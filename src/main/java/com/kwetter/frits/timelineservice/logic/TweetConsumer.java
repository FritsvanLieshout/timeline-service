package com.kwetter.frits.timelineservice.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kwetter.frits.timelineservice.configuration.KafkaProperties;
import com.kwetter.frits.timelineservice.entity.TweetTimeline;
import com.kwetter.frits.timelineservice.repository.TweetTimeLineRepository;
import com.kwetter.frits.timelineservice.logic.dto.TweetTimelineDTO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class TweetConsumer {

    private final Logger log = LoggerFactory.getLogger(TweetConsumer.class);
    private final AtomicBoolean closed = new AtomicBoolean(false);
    private final KafkaProperties kafkaProperties;

    public static final String TOPIC = "topic_timeline";

    private KafkaConsumer<String, String> kafkaConsumer;
    private TweetTimeLineRepository tweetTimeLineRepository;
    //private EmailService emailService;
    private ExecutorService executorService = Executors.newCachedThreadPool();

    public TweetConsumer(KafkaProperties kafkaProperties, TweetTimeLineRepository tweetTimeLineRepository) {
        this.kafkaProperties = kafkaProperties;
        this.tweetTimeLineRepository = tweetTimeLineRepository;
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
                        tweetTimeline.setTweetUserId(tweetTimelineDTO.getTweetUserId());
                        tweetTimeline.setTweetMessage(tweetTimelineDTO.getTweetMessage());
                        tweetTimeline.setTweetPosted(tweetTimelineDTO.getTweetPosted());
                        tweetTimeLineRepository.save(tweetTimeline);

                        //emailService.sendSimpleMessage(tweetTimelineDTO);
                    }
                }
                kafkaConsumer.commitSync();
            } catch (WakeupException e) {
                // Ignore exception if closing
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

}
