package com.madein75.soccerbuddy.ui;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.madein75.soccerbuddy.R;
import com.madein75.soccerbuddy.model.Match;
import com.madein75.soccerbuddy.ui.presenters.MatchPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HostedMatchAdapter extends FirestoreRecyclerAdapter<Match, HostedMatchAdapter.HostedMatchHolder> {

    public HostedMatchAdapter(@NonNull FirestoreRecyclerOptions<Match> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull HostedMatchHolder holder, int position, @NonNull Match model) {
        holder.bind(model);
    }

    @NonNull
    @Override
    public HostedMatchHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater
                        .from(viewGroup.getContext())
                        .inflate(R.layout.hosted_match_layout, viewGroup,false);

        return new HostedMatchHolder(v);
    }

    class HostedMatchHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView textViewTitle;

        @BindView(R.id.fixture_date)
        TextView textViewFixtureDate;

        @BindView(R.id.playing)
        TextView textViewPlaying;

        public HostedMatchHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final Match match) {
            textViewTitle.setText(match.getTitle());
            textViewFixtureDate.setText(MatchPresenter.formatDate(match.getFixtureDate()));
        }
    }
}
