package com.madein75.soccerbuddy.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.madein75.soccerbuddy.R;
import com.madein75.soccerbuddy.model.Match;
import com.madein75.soccerbuddy.model.Membership;
import com.madein75.soccerbuddy.ui.presenters.MatchPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MembershipAdapter extends FirestoreRecyclerAdapter<Membership, MembershipAdapter.MembershipViewHolder> {

    public MembershipAdapter(@NonNull FirestoreRecyclerOptions<Membership> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MembershipViewHolder holder, int position, @NonNull Membership model) {
        holder.bind(model);
    }

    @NonNull
    @Override
    public MembershipViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.match_membership_layout, viewGroup,false);

        return new MembershipViewHolder(v);
    }

    class MembershipViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.thumbnail)
        ImageView imageThumbnail;

        @BindView(R.id.title)
        TextView textViewTitle;

        @BindView(R.id.fixture_date)
        TextView textViewFixtureDate;

        public MembershipViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final Membership membership) {

            Glide.with(itemView.getContext())
                    .load(membership.getMatchPhotoUrl())
                    .into(imageThumbnail);

            textViewTitle.setText(membership.getMatchTitle());
            textViewFixtureDate.setText(MatchPresenter.formatDate(membership.getMatchFixtureDate()));
        }
    }
}
