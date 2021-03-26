package com.kwetter.frits.timelineservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;

@Document(collection = "tweet_timeline")
public class TweetTimeline implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @NotNull
    @Field("tweet_userId")
    private int tweetUserId;

    @NotNull
    @Field("tweet_message")
    private String tweetMessage;

    @NotNull
    @Field("tweet_posted")
    private String tweetPosted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTweetUserId() {
        return tweetUserId;
    }

    public void setTweetUserId(int tweetUserId) {
        this.tweetUserId = tweetUserId;
    }

    public TweetTimeline tweetUserId(int tweetUserId) {
        this.tweetUserId = tweetUserId;
        return this;
    }

    public String getTweetMessage() {
        return tweetMessage;
    }

    public void setTweetMessage(String tweetMessage) {
        this.tweetMessage = tweetMessage;
    }

    public TweetTimeline tweetMessage(String tweetMessage) {
        this.tweetMessage = tweetMessage;
        return this;
    }

    public String getTweetPosted() {
        return tweetPosted;
    }

    public void setTweetPosted(String tweetPosted) {
        this.tweetPosted = tweetPosted;
    }

    public TweetTimeline tweetPosted(String tweetPosted) {
        this.tweetPosted = tweetPosted;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TweetTimeline)) {
            return false;
        }
        return id != null && id.equals(((TweetTimeline) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TweetTimeline [id=" + id + ", tweetUserId=" + tweetUserId + ", tweetMessage=" + tweetMessage + ", tweetPosted=" + tweetPosted + "]";
    }
}
