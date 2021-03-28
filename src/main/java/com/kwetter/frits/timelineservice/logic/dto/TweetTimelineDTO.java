package com.kwetter.frits.timelineservice.logic.dto;

public class TweetTimelineDTO {

    private int tweetUserId;
    private String tweetMessage;
    private String tweetPosted;

    public int getTweetUserId() {
        return tweetUserId;
    }

    public void setTweetUserId(int tweetUserId) {
        this.tweetUserId = tweetUserId;
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
