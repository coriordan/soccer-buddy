package com.example.soccerbuddy.ui.presenters;

import android.arch.persistence.room.util.StringUtil;
import android.content.Context;

import com.example.soccerbuddy.model.SkillLevel;

import java.text.DateFormat;
import java.util.Date;

public class MatchPresenter {

    private final Context context;
    private DateFormat dateFormat;

    public MatchPresenter(final Context context) { this.context = context; }

    public String formatDate(final Date date) {
        if (dateFormat == null) {
            dateFormat = DateFormat.getDateInstance(DateFormat.LONG);
        }

        return dateFormat.format(date);
    }

    public String formatSkillLevel(SkillLevel level) {
        if (level == null) {
            level = SkillLevel.EASY; // default to 'easy'
        }

        return level.toString().toLowerCase();
    }

}
