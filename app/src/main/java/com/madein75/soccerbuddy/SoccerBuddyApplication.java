package com.madein75.soccerbuddy;

import android.app.Application;

import com.madein75.soccerbuddy.db.SoccerBuddyRepository;

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
