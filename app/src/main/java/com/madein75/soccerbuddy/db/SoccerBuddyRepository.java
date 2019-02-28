package com.madein75.soccerbuddy.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;

import com.madein75.soccerbuddy.model.Match;
import com.madein75.soccerbuddy.model.SkillLevel;

import java.util.Date;
import java.util.List;

import static com.madein75.soccerbuddy.db.SoccerBuddyDatabase.MIGRATION_1_2;

public class SoccerBuddyRepository {

    private String DB_NAME = "soccer_buddy";

    private SoccerBuddyDatabase soccerBuddyDatabase;

    public SoccerBuddyRepository(Context context) {
        soccerBuddyDatabase = Room.databaseBuilder(
                context,
                SoccerBuddyDatabase.class,
                DB_NAME
        ).addMigrations(MIGRATION_1_2).build();
    }

    public void insertMatch(String title,
                            String description,
                            int playersRequired,
                            Date fixtureDate,
                            Date kickOffTime,
                            SkillLevel skillLevel) {
        Match match = new Match();
        match.setTitle(title);
        match.setDescription(description);
        match.setPlayersRequired(playersRequired);
        match.setFixtureDate(fixtureDate);
        match.setKickoffTime(kickOffTime);
        match.setSkillLevel(skillLevel);
        match.setCreatedAt(new Date());
        match.setUpdatedAt(new Date());

        insertMatch(match);
    }

    public void insertMatch(final Match match) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                soccerBuddyDatabase.matchDao().insert(match);
                return null;
            }
        }.execute();
    }

    public void updateMatch(final Match match) {
        match.setUpdatedAt(new Date());

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                soccerBuddyDatabase.matchDao().update(match);
                return null;
            }
        }.execute();
    }

    public LiveData<Match> getMatch(long id) {
        return soccerBuddyDatabase.matchDao().getMatch(id);
    }

    public LiveData<List<Match>> getMatches() {
        return soccerBuddyDatabase.matchDao().selectAll();
    }

}
