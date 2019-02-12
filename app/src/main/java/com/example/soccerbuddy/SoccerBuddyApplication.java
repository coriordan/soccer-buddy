package com.example.soccerbuddy;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.soccerbuddy.db.SoccerBuddyDatabase;
import com.example.soccerbuddy.db.SoccerBuddyRepository;

public class SoccerBuddyApplication extends Application {

    private static SoccerBuddyRepository repository;

    @Override
    public void onCreate() {
        super.onCreate();
        repository = new SoccerBuddyRepository(this);
    }

    public static SoccerBuddyRepository getSoccerBuddyRepository() {
        return repository;
    }
}
