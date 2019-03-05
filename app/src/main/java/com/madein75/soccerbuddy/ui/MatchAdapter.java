package com.madein75.soccerbuddy.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.madein75.soccerbuddy.R;
import com.madein75.soccerbuddy.model.Match;
import com.madein75.soccerbuddy.ui.presenters.MatchPresenter;

public class MatchAdapter extends FirestoreRecyclerAdapter<Match, MatchAdapter.MatchHolder> {

    private OnItemClickListener onClickListener;

    public MatchAdapter(@NonNull FirestoreRecyclerOptions<Match> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MatchHolder holder, int position, @NonNull Match model) {
        MatchPresenter presenter = new MatchPresenter();

        holder.textViewTitle.setText(model.getTitle());
        holder.textViewDescription.setText(model.getDescription());
        holder.textViewFixtureDate.setText(presenter.formatDate(model.getFixtureDate()));
    }

    @NonNull
    @Override
    public MatchHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.match_item_layout, viewGroup,false);
        return new MatchHolder(v);
    }

    class MatchHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewDescription;
        TextView textViewFixtureDate;

        public MatchHolder(@NonNull View itemView ) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.item_title);
            textViewDescription = itemView.findViewById(R.id.item_description);
            textViewFixtureDate = itemView.findViewById(R.id.item_fixture_date);

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

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onClickListener = listener;
    }
}
