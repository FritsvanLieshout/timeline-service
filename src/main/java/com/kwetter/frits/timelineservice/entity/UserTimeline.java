package com.kwetter.frits.timelineservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.UUID;

@Document(collection = "user_timeline")
public class UserTimeline implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private UUID userId;
    private String username;
    private String nickName;
    private String profileImage;
    private Boolean verified;
    private String biography;

    public UserTimeline() {}

    public UserTimeline(UUID userId, String username, String nickName, String profileImage, Boolean verified, String biography) {
        this.userId = userId;
        this.username = username;
        this.nickName = nickName;
        this.profileImage = profileImage;
        this.verified = verified;
        this.biography = biography;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UUID getUserId() { return userId; }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) { this.profileImage = profileImage; }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public String getBiography() { return biography; }

    public void setBiography(String biography) { this.biography = biography; }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserTimeline)) {
            return false;
        }
        return id != null && id.equals(((UserTimeline) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UserTimeline [userId=" + userId + ", username=" + username + ", nickname=" + nickName + ", profileImage=" + profileImage + "]";
    }
}
