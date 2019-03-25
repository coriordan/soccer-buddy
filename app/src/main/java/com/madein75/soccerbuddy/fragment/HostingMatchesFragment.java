package com.madein75.soccerbuddy.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.madein75.soccerbuddy.R;
import com.madein75.soccerbuddy.model.Match;
import com.madein75.soccerbuddy.ui.HostedMatchAdapter;

import javax.annotation.Nullable;

/**
 * A simple {@link Fragment} subclass.
 */
public class HostingMatchesFragment extends Fragment {

    private static final String TAG = HostingMatchesFragment.class.getName();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference matchesRef = db.collection("matches");

    private HostedMatchAdapter adapter;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hosting_matches, container, false);

        recyclerView = view.findViewById(R.id.hosting_items);
        setUpRecyclerView();

        return view;
    }

    private void setUpRecyclerView() {
        Query query = matchesRef.orderBy("fixtureDate", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Match> options = new FirestoreRecyclerOptions.Builder<Match>()
                .setQuery(query, Match.class)
                .build();

        adapter = new HostedMatchAdapter(options);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
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
