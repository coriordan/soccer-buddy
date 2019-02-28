package com.madein75.soccerbuddy;

import android.databinding.DataBindingUtil;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.madein75.soccerbuddy.model.Match;
import com.madein75.soccerbuddy.databinding.ActivityViewMatchBinding;
import com.madein75.soccerbuddy.ui.presenters.MatchPresenter;

public class ViewMatchItemActivity extends AppCompatActivity {

    public static final String EXTRA_MATCH_ITEM =
            "com.example.soccerbuddy.extras.MATCH_ITEM";

    private Match matchItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityViewMatchBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_view_match);

        ActionBar toolbar = getSupportActionBar();
        toolbar.setTitle(R.string.play_match);

        if (getIntent().hasExtra(EXTRA_MATCH_ITEM)) {
            matchItem = getIntent().getParcelableExtra(EXTRA_MATCH_ITEM);
        } else {
            matchItem = new Match();
        }

        binding.setMatch(matchItem);
        binding.setPresenter(new MatchPresenter(this));
    }
}
