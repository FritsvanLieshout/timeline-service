package com.kwetter.frits.timelineservice.logic.dto;

public class FollowTimelineDTO {

    private String username;
    private String followingUsername;

    public FollowTimelineDTO() {}

    public FollowTimelineDTO(String username, String followingUsername) {
        this.username = username;
        this.followingUsername = followingUsername;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFollowingUsername() {
        return followingUsername;
    }

    public void setFollowingUsername(String followingUsername) {
        this.followingUsername = followingUsername;
    }
}
