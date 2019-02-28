package com.madein75.soccerbuddy.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.Date;

@Entity(tableName = "matches")
public class Match implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public long id;
    String title;
    String description;
    int playersRequired;
    @ColumnInfo(name = "created_at")
    Date createdAt;
    @ColumnInfo(name = "updated_at")
    Date updatedAt;
    Date fixtureDate;
    Date kickoffTime;
    SkillLevel skillLevel;

    public Match() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPlayersRequired() {
        return playersRequired;
    }

    public void setPlayersRequired(int playersRequired) {
        this.playersRequired = playersRequired;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getFixtureDate() {
        return fixtureDate;
    }

    public void setFixtureDate(Date fixtureDate) {
        this.fixtureDate = fixtureDate;
    }

    public Date getKickoffTime() { return kickoffTime; }

    public void setKickoffTime(Date kickoffTime) { this.kickoffTime = kickoffTime; }

    public SkillLevel getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(SkillLevel skillLevel) {
        this.skillLevel = skillLevel;
    }

    protected Match(final Parcel in) {
        id = in.readLong();
        title = in.readString();
        description = in.readString();
        playersRequired = in.readInt();

        final long created = in.readLong();
        createdAt = created != -1 ? new Date(created) : null;

        final long updated = in.readLong();
        updatedAt = updated != -1 ? new Date(updated) : null;

        final long time = in.readLong();
        fixtureDate = time != -1 ? new Date(time) : null;

        final long kickOff = in.readLong();
        kickoffTime = kickOff != -1 ? new Date(kickOff) : null;

        final int skillLevelOrd = in.readInt();
        skillLevel = skillLevelOrd != -1
                ? SkillLevel.values()[skillLevelOrd]
                : null;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeInt(playersRequired);
        dest.writeLong(createdAt != null ? createdAt.getTime() : -1);
        dest.writeLong(updatedAt != null ? updatedAt.getTime() : -1);
        dest.writeLong(fixtureDate != null ? fixtureDate.getTime() : -1);
        dest.writeLong(kickoffTime != null ? kickoffTime.getTime() : -1);
        dest.writeInt(skillLevel != null ? skillLevel.ordinal() : -1);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Match> CREATOR = new
            Creator<Match>() {
                @Override
                public Match createFromParcel(Parcel in) {
                    return new Match(in);
                }
                @Override
                public Match[] newArray(int size) {
                    return new Match[size];
                }
            };

    public boolean isValid() {
        return !TextUtils.isEmpty(title)
                && !TextUtils.isEmpty(description)
                && playersRequired > 0
                && fixtureDate != null
                && kickoffTime != null
                && skillLevel != null;
    }
}