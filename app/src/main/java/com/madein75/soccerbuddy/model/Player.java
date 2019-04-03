package com.madein75.soccerbuddy.model;

public class Player {

    private String name;

    public Player() {} // required by Firebase

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
