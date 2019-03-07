package com.madein75.soccerbuddy;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.madein75.soccerbuddy.model.Match;
import com.madein75.soccerbuddy.ui.presenters.MatchPresenter;

import org.w3c.dom.Text;

public class ViewMatchActivity extends AppCompatActivity {

    public static final String EXTRA_MATCH_PATH =
            "com.madein75.soccerbuddy.extras.MATCH_PATH";

    private static final String TAG = ViewMatchActivity.class.getName();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference matchRef;
    private TextView textViewTitle;
    private TextView textViewDescription;
    private TextView textViewPlayersRequired;
    private TextView textViewFixtureDate;
    private TextView textViewKickoffTime;
    private TextView textViewSkillLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_match);

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

        textViewTitle = findViewById(R.id.title);
        textViewDescription = findViewById(R.id.description);
        textViewPlayersRequired = findViewById(R.id.players_required);
        textViewFixtureDate = findViewById(R.id.fixture_date);
        textViewKickoffTime = findViewById(R.id.kickoff_time);
        textViewSkillLevel = findViewById(R.id.skilllevel);
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
