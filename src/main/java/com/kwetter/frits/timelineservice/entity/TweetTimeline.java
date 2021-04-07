package com.kwetter.frits.timelineservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Document(collection = "tweet_timeline")
public class TweetTimeline implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("tweet_user")
    private TweetUser tweetUser;

    @NotNull
    @Field("tweet_message")
    private String tweetMessage;

    @NotNull
    @Field("tweet_posted")
    private String tweetPosted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TweetUser getTweetUser() {
        return tweetUser;
    }

    public void setTweetUser(TweetUser tweetUser) {
        this.tweetUser = tweetUser;
    }

    public TweetTimeline tweetTimeline(TweetUser tweetUser) {
        this.tweetUser = tweetUser;
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
        return "TweetTimeline [id=" + id + ", tweetUser=" + tweetUser.toString() + ", tweetMessage=" + tweetMessage + ", tweetPosted=" + tweetPosted + "]";
    }
}
