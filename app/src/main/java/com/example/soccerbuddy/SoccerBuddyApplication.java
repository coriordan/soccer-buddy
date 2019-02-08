package com.example.soccerbuddy;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.soccerbuddy.db.SoccerBuddyDatabase;

public class SoccerBuddyApplication extends Application {

    private static SoccerBuddyDatabase DATABASE;

    @Override
    public void onCreate() {
        super.onCreate();
        DATABASE = Room.databaseBuilder(
                this,
                SoccerBuddyDatabase.class,
                "SoccerBuddy"
        ).build();
    }

    public static SoccerBuddyDatabase getSoccerBuddyDatabase() {
        return DATABASE;
    }
}
