package com.madein75.soccerbuddy.ui;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.madein75.soccerbuddy.BR;

public class DataBoundViewHolder<P, I> extends RecyclerView.ViewHolder {

    private final ViewDataBinding binding;

    private I item;

    public DataBoundViewHolder(final ViewDataBinding binding,
                               final P presenter,
                               View.OnClickListener onClickListener) {
        super(binding.getRoot());
        this.binding = binding;
        this.binding.setVariable(BR.presenter, presenter);
        itemView.setTag(this);
        itemView.setOnClickListener(onClickListener);
    }

    public void setItem(final I item) {
        this.item = item;
        binding.setVariable(BR.item, item);
    }

    public I getItem() {
        return item;
    }

    public void setPresenter(final P presenter) {
        binding.setVariable(BR.presenter, presenter);
    }

}