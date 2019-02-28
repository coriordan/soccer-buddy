package com.madein75.soccerbuddy;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.madein75.soccerbuddy.model.Match;
import com.madein75.soccerbuddy.ui.DataBoundViewHolder;
import com.madein75.soccerbuddy.ui.MatchItemAdapter;

import static com.madein75.soccerbuddy.ViewMatchItemActivity.EXTRA_MATCH_ITEM;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExploreMatchesFragment extends Fragment {

    MatchItemAdapter mAdapter;

    public ExploreMatchesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore_matches, container, false);

        SoccerBuddyApplication app = (SoccerBuddyApplication) getActivity().getApplication();

        final RecyclerView matchItems = view.findViewById(R.id.match_items);
        mAdapter = new MatchItemAdapter(getActivity(),
                this,
                app.getSoccerBuddyRepository().getMatches());

        mAdapter.setOnClickListener(new ViewMatchItemClickListener());
        matchItems.setAdapter(mAdapter);

        SearchView searchView = view.findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mAdapter.getFilter().filter(s);
                return false;
            }
        });

        return view;
    }

    class ViewMatchItemClickListener
            implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            DataBoundViewHolder<?, Match> viewHolder =
                    (DataBoundViewHolder<?, Match>) v.getTag();

            Intent viewMatchItemActivity = new Intent(
                    getContext(),
                    ViewMatchItemActivity.class);
            viewMatchItemActivity.putExtra(EXTRA_MATCH_ITEM, viewHolder.getItem());
            startActivity(viewMatchItemActivity);
        }
    }


}
