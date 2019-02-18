package com.example.soccerbuddy.ui;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import android.databinding.DataBindingUtil;

import com.example.soccerbuddy.R;
import com.example.soccerbuddy.model.Match;
import com.example.soccerbuddy.ui.presenters.MatchPresenter;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;

public class MatchItemAdapter extends RecyclerView.Adapter<DataBoundViewHolder<MatchPresenter, Match>> {

    private final LayoutInflater layoutInflater;
    private final MatchPresenter matchPresenter;
    private List<Match> items = emptyList();

    public MatchItemAdapter(
            final Context context,
            final LifecycleOwner owner,
            final LiveData<List<Match>> liveItems) {

        this.layoutInflater = LayoutInflater.from(context);
        this.matchPresenter = new MatchPresenter(context);

        liveItems.observe(owner, new Observer<List<Match>>() {
            public void onChanged(final List<Match> matchItems) {
                MatchItemAdapter.this.items = (matchItems != null)
                        ? matchItems
                        : Collections.<Match>emptyList();

                MatchItemAdapter.this.notifyDataSetChanged();
            }
        });
    }

    public DataBoundViewHolder<MatchPresenter, Match> onCreateViewHolder(
            final ViewGroup parent,
            final int viewType) {

        return new DataBoundViewHolder<>(
                DataBindingUtil.inflate(
                        layoutInflater,
                        R.layout.match_item_layout,
                        parent,
                        false
                ),
                matchPresenter
        );
    }

    public void onBindViewHolder(
            final DataBoundViewHolder<MatchPresenter, Match> holder,
            final int position) {

        holder.setItem(items.get(position));
    }

    public int getItemCount() {
        return items.size();
    }


}
