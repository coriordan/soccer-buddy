package com.madein75.soccerbuddy.model;

import java.util.concurrent.ThreadLocalRandom;

public class Photo {

    static String[] photos = new String[] {
            "https://firebasestorage.googleapis.com/v0/b/soccerbuddy-2dfd7.appspot.com/o/1.jpg?alt=media",
            "https://firebasestorage.googleapis.com/v0/b/soccerbuddy-2dfd7.appspot.com/o/2.jpg?alt=media",
            "https://firebasestorage.googleapis.com/v0/b/soccerbuddy-2dfd7.appspot.com/o/3.jpg?alt=media",
            "https://firebasestorage.googleapis.com/v0/b/soccerbuddy-2dfd7.appspot.com/o/4.jpg?alt=media"
    };

    public static String getPhotoUrl() {
        int index = ThreadLocalRandom.current().nextInt(0, 3);
        return photos[index];
    }

}
