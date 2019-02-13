package com.example.soccerbuddy;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.soccerbuddy.model.Match;

public class MainActivity extends AppCompatActivity {

    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = getSupportActionBar();

        toolbar.setTitle(R.string.title_explore_matches);
        loadFragment(new ExploreMatchesFragment());

        final BottomNavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_search:
                    toolbar.setTitle(R.string.title_explore_matches);
                    fragment = new ExploreMatchesFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_playing:
                    toolbar.setTitle(R.string.title_playing);
                    return true;
                case R.id.navigation_add_match:
                    toolbar.setTitle(R.string.title_add_match);
                    fragment = new MatchDetailsFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_hosting:
                    toolbar.setTitle(R.string.title_hosting);
                    return true;
                case R.id.navigation_profile:
                    toolbar.setTitle(R.string.title_profile);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
