package com.design_shinbi.searchinghome.model.entity;

import java.time.LocalDateTime;

public class Score {
    private int id;
    private int userId;
    private int score;
    private LocalDateTime playedAt;

    public Score() {
    }

    public Score(int userId, int score) {
        this.userId = userId;
        this.score = score;
        this.playedAt = LocalDateTime.now();
    }

    public Score(int id, int userId, int score, LocalDateTime playedAt) {
        this.id = id;
        this.userId = userId;
        this.score = score;
        this.playedAt = playedAt;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }

    public LocalDateTime getPlayedAt() {
        return playedAt;
    }
    public void setPlayedAt(LocalDateTime playedAt) {
        this.playedAt = playedAt;
    }
    
    public String toString() {
        return "Score{" +
                "id=" + id +
                ", userId=" + userId +
                ", score=" + score +
                ", playedAt=" + playedAt +
                '}';
    }
}