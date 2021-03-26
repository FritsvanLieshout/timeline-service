package com.kwetter.frits.timelineservice.service;

import com.kwetter.frits.timelineservice.service.dto.TweetTimelineDTO;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class EmailService {

    private JavaMailSender javaMailSender;

    private String emailAddress;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendSimpleMessage(TweetTimelineDTO tweetTimelineDTO) throws Exception {
        emailAddress = "fritske05@gmail.com";
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailAddress);
            message.setSubject("Tweet Posted on: " + tweetTimelineDTO.getTweetPosted());
            message.setText(tweetTimelineDTO.getTweetMessage());
            message.setFrom("Kwetter");
            javaMailSender.send(message);
        } catch (Exception exception) {
            throw new Exception(exception);
        }
    }

}
