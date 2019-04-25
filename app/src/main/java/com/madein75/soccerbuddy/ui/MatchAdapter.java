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
import com.google.firebase.firestore.DocumentSnapshot;
import com.madein75.soccerbuddy.R;
import com.madein75.soccerbuddy.model.Match;
import com.madein75.soccerbuddy.ui.presenters.MatchPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MatchAdapter extends FirestoreRecyclerAdapter<Match, MatchAdapter.MatchHolder> {

    private OnItemClickListener onClickListener;

    public MatchAdapter(@NonNull FirestoreRecyclerOptions<Match> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MatchHolder holder, int position, @NonNull Match model) {
        holder.bind(model);
    }

    @NonNull
    @Override
    public MatchHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater
                        .from(viewGroup.getContext())
                        .inflate(R.layout.match_item_layout, viewGroup,false);

        return new MatchHolder(v);
    }

    class MatchHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_image)
        ImageView imagePhoto;

        @BindView(R.id.item_title)
        TextView textViewTitle;

        @BindView(R.id.item_description)
        TextView textViewDescription;

        @BindView(R.id.item_fixture_date)
        TextView textViewFixtureDate;

        public MatchHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final Match match) {
            textViewTitle.setText(match.getTitle());
            textViewDescription.setText(match.getDescription());
            textViewFixtureDate.setText(MatchPresenter.formatDate(match.getFixtureDate()));

            Glide.with(itemView.getContext())
                    .load(match.getPhotoUrl())
                    .into(imagePhoto);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && onClickListener != null) {
                        onClickListener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onClickListener = listener;
    }
}
