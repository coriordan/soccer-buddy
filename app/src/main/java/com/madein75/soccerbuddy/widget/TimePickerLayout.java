package com.madein75.soccerbuddy.widget;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.madein75.soccerbuddy.R;
import com.madein75.soccerbuddy.ui.TimePickerWrapper;

import java.util.Date;

public class TimePickerLayout extends LinearLayout {

    private TextView label;
    private TimePickerWrapper wrapper;

    private void initialize(final Context context) {
        setOrientation(VERTICAL);
        LayoutInflater.from(context).inflate(
                R.layout.widget_time_picker, this, true);

        label = (TextView) getChildAt(0);
        wrapper = new TimePickerWrapper((TextView) getChildAt(1));
    }

    public TimePickerLayout(Context context) {
        super(context);
        initialize(context);
    }

    public TimePickerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public TimePickerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    public void setTime(final Date date) {
        wrapper.setTime(date);
    }

    public Date getTime() {
        return wrapper.getTime();
    }

    public void setLabel(final CharSequence text) {
        label.setText(text);
    }

    public void setLabel(final int resid) {
        label.setText(resid);
    }

    public CharSequence getLabel() {
        return label.getText();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        return new SavedState(
                super.onSaveInstanceState(),
                getTime().getTime(), getLabel());
    }

    @Override
    protected void onRestoreInstanceState(final Parcelable state) {
        final SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        setTime(new Date(savedState.timestamp));
        setLabel(savedState.label);
    }

    private static class SavedState extends BaseSavedState {

        final long timestamp;
        final CharSequence label;

        public SavedState(final Parcelable superState,
                          final long timestamp,
                          final CharSequence label) {

            super(superState);
            this.timestamp = timestamp;
            this.label = label;
        }

        SavedState(final Parcel in) {
            super(in);
            this.timestamp = in.readLong();
            this.label = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
        }

        @Override
        public void writeToParcel(final Parcel out, final int flags) {
            super.writeToParcel(out, flags);
            out.writeLong(timestamp);
            TextUtils.writeToParcel(label, out, flags);
        }

        public static final Parcelable.Creator<SavedState> CREATOR =
                new Parcelable.Creator<SavedState>() {

                    @Override
                    public SavedState createFromParcel(final Parcel source) {
                        return new SavedState(source);
                    }

                    @Override
                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };
    }

}
