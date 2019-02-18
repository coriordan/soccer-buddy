package com.example.soccerbuddy.ui.presenters;

import android.content.Context;

import java.text.DateFormat;
import java.util.Date;

public class MatchPresenter {

    private final Context context;
    private DateFormat dateFormat;

    public MatchPresenter(final Context context) { this.context = context; }

    public String formatDate(final Date date) {
        if (dateFormat == null) {
            dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT);
        }

        return dateFormat.format(date);
    }

}
