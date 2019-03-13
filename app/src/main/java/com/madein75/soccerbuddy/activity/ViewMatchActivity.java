package com.madein75.soccerbuddy.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.madein75.soccerbuddy.R;
import com.madein75.soccerbuddy.model.Match;
import com.madein75.soccerbuddy.ui.presenters.MatchPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewMatchActivity extends AppCompatActivity {

    public static final String EXTRA_MATCH_PATH =
            "com.madein75.soccerbuddy.extras.MATCH_PATH";

    private static final String TAG = ViewMatchActivity.class.getName();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference matchRef;

    @BindView(R.id.title)
    TextView textViewTitle;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_match);

        ButterKnife.bind(this);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle(R.string.play_match);

        // retrieve document reference from passed intent data
        if (getIntent().hasExtra(EXTRA_MATCH_PATH)) {
            String matchPath = getIntent().getStringExtra(EXTRA_MATCH_PATH);
            matchRef = db.document(matchPath);
        } else {
            Toast.makeText(ViewMatchActivity.this, "Must pass a match", Toast.LENGTH_SHORT).show();
            finish(); // must pass a match path to this activity
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        matchRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            Match match = documentSnapshot.toObject(Match.class);

                            textViewTitle.setText(match.getTitle());
                            textViewDescription.setText(match.getDescription());
                            textViewPlayersRequired.setText(MatchPresenter.formatPlayersRequired(match.getPlayersRequired()));
                            textViewFixtureDate.setText(MatchPresenter.formatDate(match.getFixtureDate()));
                            textViewKickoffTime.setText(MatchPresenter.formatTime(match.getKickoffTime()));
                            textViewSkillLevel.setText(MatchPresenter.formatSkillLevel(match.getSkillLevelVal()));
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ViewMatchActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }
}
