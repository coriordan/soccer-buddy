package com.madein75.soccerbuddy.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Membership {

    private String playerId;
    private String matchId;
    private Date createdAt;

    public Membership() {} // required by Firebase

    public Membership(String playerId,
                      String matchId,
                      Date createdAt) {

        this.playerId = playerId;
        this.matchId = matchId;
        this.createdAt = createdAt;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getMatchId() {
        return matchId;
    }

    @ServerTimestamp
    public Date getCreatedAt() {
        return createdAt;
    }
}
