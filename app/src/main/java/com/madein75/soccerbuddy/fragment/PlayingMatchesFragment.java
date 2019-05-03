package com.madein75.soccerbuddy.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.madein75.soccerbuddy.R;
import com.madein75.soccerbuddy.activity.ViewPlayersActivity;
import com.madein75.soccerbuddy.model.Match;
import com.madein75.soccerbuddy.model.Membership;
import com.madein75.soccerbuddy.ui.MembershipAdapter;
import com.madein75.soccerbuddy.ui.OnItemClickListener;
import com.madein75.soccerbuddy.ui.SimpleMatchAdapter;

import javax.annotation.Nullable;

import static com.madein75.soccerbuddy.SoccerBuddyApplication.EXTRA_MATCH_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayingMatchesFragment extends Fragment {

    private static final String TAG = PlayingMatchesFragment.class.getName();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference membershipsRef = db.collection("Memberships");

    private MembershipAdapter adapter;
    RecyclerView recyclerView;

    public PlayingMatchesFragment() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playing_matches, container, false);

        recyclerView = view.findViewById(R.id.membership_items);
        setUpRecyclerView();

        return view;
    }

    private void setUpRecyclerView() {
        Query query = membershipsRef
                .whereEqualTo("playerId", FirebaseAuth.getInstance().getCurrentUser().getUid())
                .orderBy("matchFixtureDate", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Membership> options = new FirestoreRecyclerOptions.Builder<Membership>()
                .setQuery(query, Membership.class)
                .build();

        adapter = new MembershipAdapter(options);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}
