package com.example.soccerbuddy.ui.presenters;

import android.arch.persistence.room.util.StringUtil;
import android.content.Context;

import com.example.soccerbuddy.model.SkillLevel;

import java.text.DateFormat;
import java.text.Format;
import java.util.Date;

public class MatchPresenter {

    private final Context context;
    private DateFormat dateFormat;
    private DateFormat timeFormat;


    public MatchPresenter(final Context context) { this.context = context; }

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
