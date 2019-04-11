package com.madein75.soccerbuddy.model;

public class Player {

    private String name, thumbnailUrl;

    public Player() {} // required by Firebase

    public Player(String name, String thumbnailUrl) {
        this.name = name;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getName() {
        return name;
    }
}
