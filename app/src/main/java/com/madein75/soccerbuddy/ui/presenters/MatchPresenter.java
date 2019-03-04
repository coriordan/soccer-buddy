package com.madein75.soccerbuddy.ui.presenters;

import android.content.Context;

import com.madein75.soccerbuddy.model.SkillLevel;

import java.text.DateFormat;
import java.util.Date;

public class MatchPresenter {

    private DateFormat dateFormat;
    private DateFormat timeFormat;

    public MatchPresenter() { }

    public String formatDate(final Date date) {
        if (dateFormat == null) {
            dateFormat = DateFormat.getDateInstance(DateFormat.LONG);
        }

        return dateFormat.format(date);
    }

    public String formatTime(final Date time) {
        if (timeFormat == null) {
            timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
        }

        return timeFormat.format(time);
    }

    public String formatSkillLevel(SkillLevel level) {
        if (level == null) {
            level = SkillLevel.EASY; // default to 'easy'
        }

        return level.toString().toLowerCase();
    }
}
