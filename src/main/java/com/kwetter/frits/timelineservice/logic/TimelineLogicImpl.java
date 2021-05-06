package com.kwetter.frits.timelineservice.logic;

import com.kwetter.frits.timelineservice.entity.FollowTimeline;
import com.kwetter.frits.timelineservice.entity.TweetTimeline;
import com.kwetter.frits.timelineservice.interfaces.TimeLineLogic;
import com.kwetter.frits.timelineservice.repository.FollowTimelineRepository;
import com.kwetter.frits.timelineservice.repository.TweetTimelineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TimelineLogicImpl implements TimeLineLogic {

    private final TweetTimelineRepository tweetTimeLineRepository;
    private final FollowTimelineRepository followTimelineRepository;
    private final Logger log = LoggerFactory.getLogger(TimelineLogicImpl.class);

    public TimelineLogicImpl(TweetTimelineRepository tweetTimeLineRepository, FollowTimelineRepository followTimelineRepository) {
        this.tweetTimeLineRepository = tweetTimeLineRepository;
        this.followTimelineRepository = followTimelineRepository;
    }

    @Override
    public List<TweetTimeline> findAllOrderByDesc() { return tweetTimeLineRepository.findAllByOrderByTweetPostedDesc(); }

    @Override
    public List<TweetTimeline> findTweetsByFollowing(String username) {
        return getFollowingTweets(username);
    }

    @Override
    public List<TweetTimeline> findOwnTweets(String username) {
        return null;
    }

    private List<TweetTimeline> getFollowingTweets(String username) {
        log.info("----------- START -----------");
        List<TweetTimeline> timeline = new ArrayList<>();
        List<FollowTimeline> following = followTimelineRepository.findAllByUsername(username);

        log.info(String.valueOf(following.size()));

        for (var user : following) {
            log.info(user.getFollowingUsername() + ", user: " + user.getUsername());
            timeline.addAll(tweetTimeLineRepository.findAllByTweetUser_Username(user.getFollowingUsername()));
        }

        log.info("----------- END -----------");
        return timeline;
    }
}
