package com.example.soccerbuddy.ui;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.databinding.DataBindingUtil;
import android.widget.Filter;
import android.widget.Filterable;

import com.example.soccerbuddy.R;
import com.example.soccerbuddy.model.Match;
import com.example.soccerbuddy.ui.presenters.MatchPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;

public class MatchItemAdapter extends RecyclerView.Adapter<DataBoundViewHolder<MatchPresenter, Match>>
                implements Filterable {

    private final LayoutInflater layoutInflater;
    private final MatchPresenter matchPresenter;
    private List<Match> items = emptyList();
    private List<Match> itemsFiltered = emptyList();
    private View.OnClickListener onClickListener;


    public MatchItemAdapter(
            final Context context,
            final LifecycleOwner owner,
            final LiveData<List<Match>> liveItems) {

        this.layoutInflater = LayoutInflater.from(context);
        this.matchPresenter = new MatchPresenter(context);

        liveItems.observe(owner, new Observer<List<Match>>() {
            public void onChanged(final List<Match> matchItems) {
                // initialize unfiltered items and filtered items together
                MatchItemAdapter.this.items = (matchItems != null)
                        ? matchItems
                        : Collections.<Match>emptyList();

                MatchItemAdapter.this.itemsFiltered = (matchItems != null)
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
                matchPresenter,
                onClickListener
        );
    }

    public void onBindViewHolder(
            final DataBoundViewHolder<MatchPresenter, Match> holder,
            final int position) {
        holder.setItem(itemsFiltered.get(position));
    }

    public int getItemCount() {
        return itemsFiltered.size();
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    itemsFiltered = items;
                } else {
                    List<Match> filteredList = new ArrayList<>();
                    for (Match row : items) {
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    itemsFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = itemsFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                itemsFiltered = (ArrayList<Match>) results.values;
                MatchItemAdapter.this.notifyDataSetChanged();
            }
        };
    }
}
