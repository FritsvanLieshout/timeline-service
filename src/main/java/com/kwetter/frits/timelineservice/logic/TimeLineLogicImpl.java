package com.kwetter.frits.timelineservice.logic;

import com.kwetter.frits.timelineservice.entity.TweetTimeline;
import com.kwetter.frits.timelineservice.interfaces.TimeLineLogic;
import com.kwetter.frits.timelineservice.repository.TweetTimeLineRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeLineLogicImpl implements TimeLineLogic {

    private final TweetTimeLineRepository tweetTimeLineRepository;

    public TimeLineLogicImpl(TweetTimeLineRepository tweetTimeLineRepository) { this.tweetTimeLineRepository = tweetTimeLineRepository; }

    @Override
    public List<TweetTimeline> findAllOrderByDesc() { return tweetTimeLineRepository.findAllByOrderByTweetPostedDesc(); }
}
