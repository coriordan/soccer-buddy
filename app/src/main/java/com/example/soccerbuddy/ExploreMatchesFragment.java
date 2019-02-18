package com.example.soccerbuddy;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.soccerbuddy.ui.MatchItemAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExploreMatchesFragment extends Fragment {


    public ExploreMatchesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore_matches, container, false);

        SoccerBuddyApplication app = (SoccerBuddyApplication) getActivity().getApplication();

        final RecyclerView matchItems = view.findViewById(R.id.match_items);
        matchItems.setAdapter(new MatchItemAdapter(
                getActivity(), this,
                app.getSoccerBuddyRepository().getMatches()
        ));

        return view;
    }

}
