package com.madein75.soccerbuddy.ui.presenters;

import com.madein75.soccerbuddy.model.SkillLevel;

import java.text.DateFormat;
import java.util.Date;

public class MatchPresenter {

    private static DateFormat dateFormat;
    private static DateFormat timeFormat;

    public MatchPresenter() { }

    public static String formatDate(final Date date) {
        if (dateFormat == null) {
            dateFormat = DateFormat.getDateInstance(DateFormat.LONG);
        }

        return dateFormat.format(date);
    }

    public static String formatTime(final Date time) {
        if (timeFormat == null) {
            timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
        }

        return timeFormat.format(time);
    }

    public static String formatSkillLevel(SkillLevel level) {
        if (level == null) {
            level = SkillLevel.EASY; // default to 'easy'
        }

        return level.toString().toLowerCase();
    }

    public static String formatPlacesAvailable(int places) {
        return Integer.toString(places);
    }

    public static String formatPlayingCount(int players) {
        return String.format("%d player(s)", players);
    }
}
