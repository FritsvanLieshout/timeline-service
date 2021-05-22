package com.kwetter.frits.timelineservice.logic.consumers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kwetter.frits.timelineservice.configuration.KafkaProperties;
import com.kwetter.frits.timelineservice.logic.dto.UserTimelineDTO;
import com.kwetter.frits.timelineservice.repository.FollowTimelineRepository;
import com.kwetter.frits.timelineservice.repository.TweetTimelineRepository;
import com.kwetter.frits.timelineservice.repository.UserTimelineRepository;
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
public class PermanentDeleteUserConsumer {

    private final Logger log = LoggerFactory.getLogger(PermanentDeleteUserConsumer.class);
    private final AtomicBoolean closed = new AtomicBoolean(false);
    private final KafkaProperties kafkaProperties;

    public static final String TOPIC = "user-deleted";

    private KafkaConsumer<String, String> kafkaConsumer;
    private UserTimelineRepository userTimelineRepository;
    private FollowTimelineRepository followTimelineRepository;
    private TweetTimelineRepository tweetTimelineRepository;
    private ExecutorService executorService = Executors.newCachedThreadPool();

    public PermanentDeleteUserConsumer(KafkaProperties kafkaProperties, UserTimelineRepository userTimelineRepository, FollowTimelineRepository followTimelineRepository, TweetTimelineRepository tweetTimelineRepository) {
        this.kafkaProperties = kafkaProperties;
        this.userTimelineRepository = userTimelineRepository;
        this.followTimelineRepository = followTimelineRepository;
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

                        var objectMapper = new ObjectMapper();
                        var userTimelineDTO = objectMapper.readValue(record.value(), UserTimelineDTO.class);
                        var user = userTimelineRepository.findUserTimelineByUsernameAndUserId(userTimelineDTO.getUsername(), userTimelineDTO.getUserId());

                        if (user != null) {
                            userTimelineRepository.deleteUserTimelineByUsername(user.getUsername());
                            followTimelineRepository.deleteAllByUsername(user.getUsername());
                            followTimelineRepository.deleteAllByFollowingUsername(user.getUsername());
                            tweetTimelineRepository.deleteAllByTweetUser_Username(user.getUsername());
                        }
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
}
