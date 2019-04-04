package com.madein75.soccerbuddy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.madein75.soccerbuddy.R;

public class ViewPlayersActivity extends AppCompatActivity {

    private static final String TAG = ViewPlayersActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_players);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle(R.string.view_players);
    }
}
