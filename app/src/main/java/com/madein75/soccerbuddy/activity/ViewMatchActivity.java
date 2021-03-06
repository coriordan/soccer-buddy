package com.madein75.soccerbuddy.activity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.madein75.soccerbuddy.R;
import com.madein75.soccerbuddy.fragment.MapFragment;
import com.madein75.soccerbuddy.model.Match;
import com.madein75.soccerbuddy.model.Membership;
import com.madein75.soccerbuddy.model.Player;
import com.madein75.soccerbuddy.ui.presenters.MatchPresenter;

import java.util.Collection;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.madein75.soccerbuddy.SoccerBuddyApplication.EXTRA_MATCH_ID;

public class ViewMatchActivity extends AppCompatActivity {

    private static final String TAG = ViewMatchActivity.class.getName();

    private FirebaseFirestore db;
    private DocumentReference matchRef;

    @BindView(R.id.title)
    TextView textViewTitle;

    @BindView(R.id.photo)
    ImageView imageViewPhoto;

    @BindView(R.id.description)
    TextView textViewDescription;

    @BindView(R.id.players_required)
    TextView textViewPlayersRequired;

    @BindView(R.id.fixture_date)
    TextView textViewFixtureDate;

    @BindView(R.id.kickoff_time)
    TextView textViewKickoffTime;

    @BindView(R.id.skilllevel)
    TextView textViewSkillLevel;

    @BindView(R.id.button_join_match)
    Button buttonJoinMatch;

    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_match);

        ButterKnife.bind(this);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle(R.string.play_match);

        // retrieve match id from passed intent data
        String matchId = getIntent().getExtras().getString(EXTRA_MATCH_ID);
        if (matchId == null) {
            throw new IllegalArgumentException("Must pass extra " + EXTRA_MATCH_ID);
        }

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Get reference to match
        matchRef = db.collection("Matches").document(matchId);

        mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.setReadOnly();
    }

    @Override
    protected void onStart() {
        super.onStart();

        matchRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(ViewMatchActivity.this, "Error while loading!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, e.toString());
                    return;
                }

                if (documentSnapshot.exists()) {
                    Match match = documentSnapshot.toObject(Match.class);
                    updateUI(match);
                }
            }
        });
    }

    @OnClick(R.id.button_join_match)
    public void onJoinMatch(View view) {
        addPlayer().addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                buttonJoinMatch.setEnabled(false);
            }
        })
        .addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Add membership failed", e);
                Toast.makeText(ViewMatchActivity.this, "Error while joining match!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Task<Void> addPlayer() {
        return db.runTransaction(new Transaction.Function<Void>() {
            @Nullable
            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String currentUserName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                String currentThumbnailUrl = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString();


                // get players sub-collection
                CollectionReference playersCollection = matchRef.collection("Players");

                // create reference for new membership
                DocumentReference membershipRef = db
                        .collection("Memberships").document();

                // create the membership object
                DocumentSnapshot matchSnapshot = transaction.get(matchRef);
                Match match = matchSnapshot.toObject(Match.class);
                Membership membership = new Membership(currentUserId,
                        matchSnapshot.getId(),
                        match.getTitle(),
                        match.getFixtureDate(),
                        match.getPhotoUrl(),
                        new Date());

                // update no. players playing match
                transaction.update(matchRef, "players",
                                            FieldValue.arrayUnion(currentUserId));
                // set current user as player
                transaction.set(playersCollection.document(currentUserId),
                                            new Player(currentUserName, currentThumbnailUrl));
                // set membership of match
                transaction.set(membershipRef, membership);
                return null;
            }
        });
    }

    private void updateUI(Match match) {
        textViewTitle.setText(match.getTitle());
        textViewDescription.setText(match.getDescription());
        textViewPlayersRequired.setText(MatchPresenter
                .formatPlacesAvailable(match.getPlacesAvailable()));
        textViewFixtureDate.setText(MatchPresenter.formatDate(match.getFixtureDate()));
        textViewKickoffTime.setText(MatchPresenter.formatTime(match.getKickoffTime()));
        textViewSkillLevel.setText(MatchPresenter.formatSkillLevel(match.getSkillLevelVal()));
        mapFragment.setMarkedLocation(match.getLocationLatLng());

        Glide.with(this)
                .load(match.getPhotoUrl())
                .into(imageViewPhoto);

        // determine if we can enable the 'Join Match' button
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (match.getPlayers().contains(currentUserId)
                || match.getOwnerId().equals(currentUserId)
                || match.isFull()) {
            buttonJoinMatch.setEnabled(false);
        }
    }
}
