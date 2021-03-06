package com.madein75.soccerbuddy.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;
import java.util.List;

public class Match {

    private String ownerId;
    private String title;
    private String description;
    private int playersRequired;
    private Date createdAt;
    private Date fixtureDate;
    private Date kickoffTime;
    private SkillLevel skillLevel;
    private GeoPoint location;
    List<String> players;
    private String photoUrl;

    public Match() {} // required by Firebase

    public Match(
                FirebaseUser owner,
                String title,
                String description,
                int playersRequired,
                Date createdAt,
                Date fixtureDate,
                Date kickoffTime,
                String skillLevel,
                GeoPoint location,
                List<String> players) {
        this.ownerId = owner.getUid();
        this.title = title;
        this.description = description;
        this.playersRequired = playersRequired;
        this.createdAt = createdAt;
        this.fixtureDate = fixtureDate;
        this.kickoffTime = kickoffTime;
        this.skillLevel = SkillLevel.valueOf(skillLevel);
        this.location = location;
        this.players = players;
        this.photoUrl = Photo.getPhotoUrl();
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

    public GeoPoint getLocation() {return location;}

    @Exclude
    public LatLng getLocationLatLng() {return new LatLng(this.location.getLatitude(),
                                                    this.location.getLongitude());}

    public List<String> getPlayers() {
        return players;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    @Exclude
    public boolean isFull() {
        return this.getPlayersRequired() == this.getPlayersJoined();
    }

    @Exclude
    public int getPlayersJoined() {
        return players.size();
    }

    @Exclude
    public SkillLevel getSkillLevelVal() {return skillLevel; }

    @Exclude
    public int getPlacesAvailable() {
        return this.getPlayersRequired() - this.getPlayersJoined();
    }

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