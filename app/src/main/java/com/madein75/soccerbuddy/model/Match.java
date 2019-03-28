package com.madein75.soccerbuddy.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Match {

    private String ownerId;
    private String title;
    private String description;
    private int playersRequired;
    private Date createdAt;
    private Date fixtureDate;
    private Date kickoffTime;
    private SkillLevel skillLevel;
    private int playersJoined;

    public Match() {} // required by Firebase

    public Match(
                FirebaseUser owner,
                String title,
                String description,
                int playersRequired,
                Date createdAt,
                Date fixtureDate,
                Date kickoffTime,
                String skillLevel) {
        this.ownerId = owner.getUid();
        this.title = title;
        this.description = description;
        this.playersRequired = playersRequired;
        this.createdAt = createdAt;
        this.fixtureDate = fixtureDate;
        this.kickoffTime = kickoffTime;
        this.skillLevel = SkillLevel.valueOf(skillLevel);
        this.playersJoined = 0;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPlayersRequired() {
        return playersRequired;
    }

    @ServerTimestamp
    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getFixtureDate() {
        return fixtureDate;
    }

    public Date getKickoffTime() { return kickoffTime; }

    public String getSkillLevel() {
        return skillLevel.name();
    }

    public int getPlayersJoined() {
        return playersJoined;
    }

    public void setPlayersJoined(int playersJoined) {
        this.playersJoined = playersJoined;
    }

    @Exclude
    public SkillLevel getSkillLevelVal() {return skillLevel; }

    @Exclude
    public boolean isValid() {
        return !TextUtils.isEmpty(title)
                && !TextUtils.isEmpty(description)
                && playersRequired > 0
                && fixtureDate != null
                && kickoffTime != null
                && skillLevel != null;
    }
}