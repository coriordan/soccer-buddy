package com.madein75.soccerbuddy.ui;

import android.app.TimePickerDialog;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimePickerWrapper implements View.OnClickListener, View.OnFocusChangeListener, TimePickerDialog.OnTimeSetListener {
    private final TextView display;
    private final DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT);

    private TimePickerDialog dialog = null;
    private Date currentTime = null;

    public TimePickerWrapper(final TextView display) {
        this.display = display;
        this.display.setFocusable(true);
        this.display.setClickable(true);
        this.display.setOnClickListener(this);
        this.display.setOnFocusChangeListener(this);
        this.setTime(new Date());
    }

    public void setTime(final Date date) {
        if (date == null) {
            throw new IllegalArgumentException("date may not be null");
        }

        this.currentTime = (Date) date.clone();
        this.display.setText(timeFormat.format(currentTime));

        if (this.dialog != null) {
            final GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(getTime());

            this.dialog.updateTime(
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE)
            );
        }
    }

    public Date getTime() {
        return currentTime;
    }

    void openTimePickerDialog() {
        if (dialog == null) {
            final GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(getTime());

            dialog = new TimePickerDialog(
                    display.getContext(),
                    this,
                    calendar.get(Calendar.HOUR),
                    calendar.get(Calendar.MINUTE),
                    false
            );
        }

        dialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = GregorianCalendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        setTime(c.getTime());
    }

    @Override
    public void onClick(View v) {
        openTimePickerDialog();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            openTimePickerDialog();
        }
    }
}