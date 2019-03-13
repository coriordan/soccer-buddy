package com.madein75.soccerbuddy.fragment;


import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.madein75.soccerbuddy.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private static final int RC_SIGN_IN = 1138;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    @BindView(R.id.buttonSignIn)
    Button btnSignIn;

    @BindView(R.id.buttonSignOut)
    Button btnSignOut;

    @BindView(R.id.textViewUser)
    TextView textViewUser;

    @BindView(R.id.textViewEmail)
    TextView textViewEmail;

    @BindView(R.id.imageViewProfile)
    ImageView imageProfile;

    private Unbinder unbinder;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);

        auth = FirebaseAuth.getInstance();
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                updateUi();
            }
        };

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

    private void updateUi() {
        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            btnSignIn.setVisibility(View.VISIBLE);
            btnSignOut.setVisibility(View.GONE);
            textViewEmail.setVisibility(View.GONE);
            textViewUser.setVisibility(View.GONE);
            imageProfile.setImageBitmap(null);
        } else {
            btnSignIn.setVisibility(View.GONE);
            btnSignOut.setVisibility(View.VISIBLE);
            textViewEmail.setVisibility(View.VISIBLE);
            textViewUser.setVisibility(View.VISIBLE);

            textViewEmail.setText(user.getDisplayName());
            textViewEmail.setText(user.getEmail());
            Glide.with(getActivity())
                    .load(user.getPhotoUrl())
                    .into(imageProfile);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == Activity.RESULT_OK) {
                Log.d(this.getClass().getName(), "This user signed in with " + response.getProviderType());
                updateUi();
            } else {
                updateUi();
            }
        }
    }

    @OnClick(R.id.buttonSignIn)
    public void signIn(View view) {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .setAvailableProviders(getAuthenticationProviders())
                        .build(),
                RC_SIGN_IN);
    }

    @OnClick(R.id.buttonSignOut)
    public void signOut(View view) {
        AuthUI.getInstance()
                .signOut(getActivity())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getActivity(), "Signed out succesfully ... ", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private List<AuthUI.IdpConfig> getAuthenticationProviders() {
        List<AuthUI.IdpConfig> authenticationProviders = new ArrayList<>();

        authenticationProviders.add(new AuthUI.IdpConfig.GoogleBuilder().build());
        authenticationProviders.add(new AuthUI.IdpConfig.EmailBuilder().build());

        return authenticationProviders;
    }
}
