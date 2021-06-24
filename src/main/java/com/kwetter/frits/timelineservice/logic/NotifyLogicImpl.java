package com.kwetter.frits.timelineservice.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kwetter.frits.timelineservice.configuration.KafkaProperties;
import com.kwetter.frits.timelineservice.interfaces.NotifyLogic;
import com.kwetter.frits.timelineservice.repository.FollowTimelineRepository;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotifyLogicImpl implements NotifyLogic {

    private final Logger log = LoggerFactory.getLogger(NotifyLogicImpl.class);

    private static final String TOPIC = "notify-followers";

    private final KafkaProperties kafkaProperties;

    private final static Logger logger = LoggerFactory.getLogger(NotifyLogicImpl.class);
    private KafkaProducer<String, String> producer;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private FollowTimelineRepository followTimelineRepository;

    public NotifyLogicImpl(KafkaProperties kafkaProperties, FollowTimelineRepository followTimelineRepository) {
        this.kafkaProperties = kafkaProperties;
        this.followTimelineRepository = followTimelineRepository;
    }

    @PostConstruct
    public void initialize(){
        log.info("Kafka producer initializing...");
        this.producer = new KafkaProducer<>(kafkaProperties.getProducerProps());
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
        log.info("Kafka producer initialized");
    }

    @Override
    public void notifyFollowers(String username) {
        try {
            var followers = followTimelineRepository.findAllByUsername(username);
            if (!followers.isEmpty()) {
                List<String> usernames = new ArrayList<>();
                for (var follower : followers) {
                    usernames.add(follower.getFollowingUsername());
                }
                var message = objectMapper.writeValueAsString(usernames);
                ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, message);
                producer.send(record);
            }
        } catch (JsonProcessingException e) {
            logger.error("Could not send tweet", e);
        }
    }

    @PreDestroy
    public void shutdown() {
        log.info("Shutdown Kafka producer");
        producer.close();
    }
}
