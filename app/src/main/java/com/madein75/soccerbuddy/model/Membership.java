package com.madein75.soccerbuddy.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Membership {

    private String playerId;
    private String matchId;
    private Date createdAt;
    private String matchTitle;
    private Date matchFixtureDate;
    private String matchPhotoUrl;

    public Membership() {} // required by Firebase

    public Membership(String playerId,
                      String matchId,
                      String matchTitle,
                      Date matchFixtureDate,
                      String matchPhotoUrl,
                      Date createdAt) {

        this.playerId = playerId;
        this.matchId = matchId;
        this.createdAt = createdAt;
        this.matchTitle = matchTitle;
        this.matchFixtureDate = matchFixtureDate;
        this.matchPhotoUrl = matchPhotoUrl;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getMatchId() {
        return matchId;
    }

    public String getMatchTitle() { return matchTitle; }

    public Date getMatchFixtureDate() { return matchFixtureDate; }

    public String getMatchPhotoUrl() { return matchPhotoUrl; }

    @ServerTimestamp
    public Date getCreatedAt() {
        return createdAt;
    }
}
