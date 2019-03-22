package com.madein75.soccerbuddy.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.madein75.soccerbuddy.R;

import java.util.ArrayList;
import java.util.List;

public class SignInActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1138;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }

    public void onSignInClick(View view) {
        startSignIn();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            handleSignInResponse(resultCode);
        }
    }

    private void startSignIn() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .setAvailableProviders(getAuthenticationProviders())
                        .build(),
                RC_SIGN_IN);
    }

    private List<AuthUI.IdpConfig> getAuthenticationProviders() {
        List<AuthUI.IdpConfig> authenticationProviders = new ArrayList<>();

        authenticationProviders.add(new AuthUI.IdpConfig.GoogleBuilder().build());
        authenticationProviders.add(new AuthUI.IdpConfig.EmailBuilder().build());

        return authenticationProviders;
    }

    private void handleSignInResponse(int resultCode) {

        if (resultCode == RESULT_OK) {
            Intent i = new Intent(SignInActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        } else {
            Toast.makeText(this, "Sign In Failed", Toast.LENGTH_SHORT).show();
        }
    }
}
