package com.kwetter.frits.timelineservice.logic;

import com.kwetter.frits.timelineservice.entity.FollowTimeline;
import com.kwetter.frits.timelineservice.entity.TweetTimeline;
import com.kwetter.frits.timelineservice.interfaces.TimeLineLogic;
import com.kwetter.frits.timelineservice.repository.FollowTimelineRepository;
import com.kwetter.frits.timelineservice.repository.TweetTimelineRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class TimelineLogicImpl implements TimeLineLogic {

    private final TweetTimelineRepository tweetTimeLineRepository;
    private final FollowTimelineRepository followTimelineRepository;

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
        List<TweetTimeline> timeline = new ArrayList<>(tweetTimeLineRepository.findAllByTweetUser_Username(username));
        timeline.sort(Comparator.comparing(TweetTimeline::getTweetPosted).reversed());
        return timeline;
    }

    private List<TweetTimeline> getFollowingTweets(String username) {
        List<TweetTimeline> timeline = new ArrayList<>();
        List<FollowTimeline> following = followTimelineRepository.findAllByUsername(username);

        for (var user : following) {
            timeline.addAll(tweetTimeLineRepository.findAllByTweetUser_Username(user.getFollowingUsername()));
        }

        timeline.sort(Comparator.comparing(TweetTimeline::getTweetPosted).reversed());
        return timeline;
    }
}
