package me.codetalk.demo.entity;

public class User {

    private String userId;
    private String name;
    private String email;
    private Long twitCount;

    public User(String userId, String name, String email, Long twitCount) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.twitCount = twitCount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getTwitCount() {
        return twitCount;
    }

    public void setTwitCount(Long twitCount) {
        this.twitCount = twitCount;
    }

    @Override
    public String toString() {
        return String.format("User: id=%s, name=%s, email=%s, twit count=%d",
                userId, name, email, twitCount == null ? 0L : twitCount);
    }
}
