package com.madein75.soccerbuddy.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.madein75.soccerbuddy.R;
import com.madein75.soccerbuddy.fragment.AddMatchFragment;
import com.madein75.soccerbuddy.fragment.ExploreMatchesFragment;
import com.madein75.soccerbuddy.fragment.HostingMatchesFragment;
import com.madein75.soccerbuddy.fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    private ActionBar toolbar;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        checkIsSignedIn();

        setContentView(R.layout.activity_main);

        toolbar = getSupportActionBar();

        toolbar.setTitle(R.string.title_explore_matches);
        loadFragment(new ExploreMatchesFragment());

        final BottomNavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
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
                    fragment = new AddMatchFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_hosting:
                    toolbar.setTitle(R.string.title_hosting);
                    fragment = new HostingMatchesFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_profile:
                    toolbar.setTitle(R.string.title_profile);
                    fragment = new ProfileFragment();
                    loadFragment(fragment);
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

    private void checkIsSignedIn() {
        if (currentUser == null) {
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        }
    }
}
