package com.madein75.soccerbuddy.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.madein75.soccerbuddy.R;
import com.madein75.soccerbuddy.model.Player;
import com.madein75.soccerbuddy.ui.PlayerListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.madein75.soccerbuddy.SoccerBuddyApplication.EXTRA_MATCH_ID;

public class ViewPlayersActivity extends AppCompatActivity {

    private static final String TAG = ViewPlayersActivity.class.getName();

    private ListView playerListView;
    private PlayerListAdapter playerListAdapter;
    private List<Player> playerList = new ArrayList<>();
    private FirebaseFirestore db;
    private DocumentReference matchRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_players);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle(R.string.view_players);

        playerListView = findViewById(R.id.playersListView);
        playerListAdapter =
                    new PlayerListAdapter(this, R.layout.player_list_row, playerList);
        playerListView.setAdapter(playerListAdapter);

        // retrieve match id from passed intent data
        String matchId = getIntent().getExtras().getString(EXTRA_MATCH_ID);
        if (matchId == null) {
            throw new IllegalArgumentException("Must pass extra " + EXTRA_MATCH_ID);
        }

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Get reference to match
        matchRef = db.collection("Matches").document(matchId);

        // retrieve player items from Firestore
        matchRef.collection("Players").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Player player = documentSnapshot.toObject(Player.class);
                        playerList.add(player);
                    }

                    playerListAdapter.notifyDataSetChanged();
                } else {
                    Log.d(TAG, "Error loading players " + task.getException());
                }
            }
        });

    }
}
