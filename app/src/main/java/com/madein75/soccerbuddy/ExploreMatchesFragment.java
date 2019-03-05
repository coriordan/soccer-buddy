package com.madein75.soccerbuddy;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.madein75.soccerbuddy.model.Match;
import com.madein75.soccerbuddy.ui.MatchAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExploreMatchesFragment extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference matchesRef = db.collection("matches");

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
                Match match = documentSnapshot.toObject(Match.class);
                String id = documentSnapshot.getId();
                String path = documentSnapshot.getReference().getPath();
                Toast.makeText(getActivity(), "Position: " + position + " ID: " + id, Toast.LENGTH_SHORT).show();
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

    class ViewMatchItemClickListener
            implements View.OnClickListener {

        @Override
        public void onClick(View v) {
//            MatchHolder viewHolder = (MatchHolder) v.getTag();
//
//            Intent viewMatchItemActivity = new Intent(
//                    getContext(),
//                    ViewMatchItemActivity.class);
//            viewMatchItemActivity.putExtra(EXTRA_MATCH_ITEM, viewHolder.getItem());
//            startActivity(viewMatchItemActivity);
        }

    }
}
