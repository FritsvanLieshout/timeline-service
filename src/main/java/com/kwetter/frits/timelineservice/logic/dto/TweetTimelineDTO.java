package com.kwetter.frits.timelineservice.logic.dto;

import com.kwetter.frits.timelineservice.entity.TweetUser;

public class TweetTimelineDTO {

    private TweetUser tweetUser;
    private String tweetMessage;
    private String tweetPosted;

    public TweetUser getTweetUser() {
        return tweetUser;
    }

    public void setTweetUser(TweetUser tweetUser) {
        this.tweetUser = tweetUser;
    }

    public String getTweetMessage() {
        return tweetMessage;
    }

    public void setTweetMessage(String tweetMessage) {
        this.tweetMessage = tweetMessage;
    }

    public String getTweetPosted() {
        return tweetPosted;
    }

    public void setTweetPosted(String tweetPosted) {
        this.tweetPosted = tweetPosted;
    }
}
