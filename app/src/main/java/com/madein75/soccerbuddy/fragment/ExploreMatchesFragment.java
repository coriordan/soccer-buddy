package com.madein75.soccerbuddy.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.madein75.soccerbuddy.R;
import com.madein75.soccerbuddy.activity.ViewMatchActivity;
import com.madein75.soccerbuddy.model.Match;
import com.madein75.soccerbuddy.ui.MatchAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExploreMatchesFragment extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference matchesRef = db.collection("Matches");

    private MatchAdapter adapter;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore_matches, container, false);

        recyclerView = view.findViewById(R.id.match_items);
        setUpRecyclerView();

        return view;
    }

    private void setUpRecyclerView() {
        Query query = matchesRef.orderBy("fixtureDate", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Match> options = new FirestoreRecyclerOptions.Builder<Match>()
                .setQuery(query, Match.class)
                .build();

        adapter = new MatchAdapter(options);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MatchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                String matchId = documentSnapshot.getReference().getId();

                Intent intent = new Intent(
                  getContext(),
                  ViewMatchActivity.class
                );

                intent.putExtra(ViewMatchActivity.EXTRA_MATCH_ID, matchId);
                startActivity(intent);
            }
        });
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
