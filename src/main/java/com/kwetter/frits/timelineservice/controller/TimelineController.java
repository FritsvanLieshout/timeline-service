package com.kwetter.frits.timelineservice.controller;

import com.kwetter.frits.timelineservice.entity.TweetTimeline;
import com.kwetter.frits.timelineservice.logic.TimelineLogicImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/timeline")
public class TimelineController {

    private final TimelineLogicImpl timeLineLogic;

    public TimelineController(TimelineLogicImpl timeLineLogic) { this.timeLineLogic = timeLineLogic; }

    @GetMapping("/all")
    public ResponseEntity<List<TweetTimeline>> retrieveAllTweetsByTimeLine() {
        try {
            List<TweetTimeline> _timeline = new ArrayList<>(timeLineLogic.findAllOrderByDesc());

            if (_timeline.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(_timeline, HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @MessageMapping("/sendTweet")
    @SendTo("/topic_timeline")
    public TweetTimeline broadcastGroupMessage(@Payload TweetTimeline tweetTimeline) {
        return tweetTimeline;
    }
}
