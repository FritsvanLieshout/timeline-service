package com.kwetter.frits.timelineservice.logic.consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kwetter.frits.timelineservice.configuration.KafkaProperties;
import com.kwetter.frits.timelineservice.entity.TweetTimeline;
import com.kwetter.frits.timelineservice.repository.TweetTimelineRepository;
import com.kwetter.frits.timelineservice.logic.dto.TweetTimelineDTO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class TweetConsumer {

    @Autowired
    SimpMessagingTemplate template;

    private final Logger log = LoggerFactory.getLogger(TweetConsumer.class);
    private final AtomicBoolean closed = new AtomicBoolean(false);
    private final KafkaProperties kafkaProperties;

    public static final String TOPIC = "tweet-posted";

    private KafkaConsumer<String, String> kafkaConsumer;
    private TweetTimelineRepository tweetTimelineRepository;
    private ExecutorService executorService = Executors.newCachedThreadPool();

    public TweetConsumer(KafkaProperties kafkaProperties, TweetTimelineRepository tweetTimelineRepository) {
        this.kafkaProperties = kafkaProperties;
        this.tweetTimelineRepository = tweetTimelineRepository;
    }

    @PostConstruct
    public void start() {

        log.info("Kafka consumer starting...");
        this.kafkaConsumer = new KafkaConsumer<>(kafkaProperties.getConsumerProps());
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
        kafkaConsumer.subscribe(Collections.singletonList(TOPIC));
        log.info("Kafka consumer started");

        executorService.execute(() -> {
            try {
                while (!closed.get()) {
                    ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofSeconds(3));
                    for (ConsumerRecord<String, String> record : records) {
                        log.info("Consumed message in {} : {}", TOPIC, record.value());

                        ObjectMapper objectMapper = new ObjectMapper();
                        TweetTimelineDTO tweetTimelineDTO = objectMapper.readValue(record.value(), TweetTimelineDTO.class);
                        TweetTimeline tweetTimeline = new TweetTimeline();
                        tweetTimeline.setTweetUser(tweetTimelineDTO.getTweetUser());
                        tweetTimeline.setTweetMessage(tweetTimelineDTO.getTweetMessage());
                        tweetTimeline.setTweetPosted(tweetTimelineDTO.getTweetPosted());
                        tweetTimelineRepository.save(tweetTimeline);

                        listen(tweetTimeline);
                    }
                }
                kafkaConsumer.commitSync();
            } catch (WakeupException e) {
                if (!closed.get()) throw e;
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            } finally {
                log.info("Kafka consumer close");
                kafkaConsumer.close();
            }
        });

    }

    public KafkaConsumer<String, String> getKafkaConsumer() {
        return kafkaConsumer;
    }

    public void shutdown() {
        log.info("Shutdown Kafka consumer");
        closed.set(true);
        kafkaConsumer.wakeup();
    }

    public void listen(TweetTimeline tweetTimeline) {
        System.out.println("sending via kafka listener..");
        template.convertAndSend("/topic_timeline", tweetTimeline);
    }
}
