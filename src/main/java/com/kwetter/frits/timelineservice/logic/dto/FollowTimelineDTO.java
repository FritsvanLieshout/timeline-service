package com.kwetter.frits.timelineservice.logic.dto;

public class FollowTimelineDTO {

    private String username;
    private String followUsername;

    public FollowTimelineDTO() {}

    public FollowTimelineDTO(String username, String followUsername) {
        this.username = username;
        this.followUsername = followUsername;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFollowUsername() {
        return followUsername;
    }

    public void setFollowUsername(String followUsername) {
        this.followUsername = followUsername;
    }
}
