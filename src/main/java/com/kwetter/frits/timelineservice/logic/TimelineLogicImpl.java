package com.kwetter.frits.timelineservice.logic;

import com.kwetter.frits.timelineservice.entity.TweetTimeline;
import com.kwetter.frits.timelineservice.interfaces.TimeLineLogic;
import com.kwetter.frits.timelineservice.repository.TweetTimelineRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimelineLogicImpl implements TimeLineLogic {

    private final TweetTimelineRepository tweetTimeLineRepository;

    public TimelineLogicImpl(TweetTimelineRepository tweetTimeLineRepository) { this.tweetTimeLineRepository = tweetTimeLineRepository; }

    @Override
    public List<TweetTimeline> findAllOrderByDesc() { return tweetTimeLineRepository.findAllByOrderByTweetPostedDesc(); }
}
