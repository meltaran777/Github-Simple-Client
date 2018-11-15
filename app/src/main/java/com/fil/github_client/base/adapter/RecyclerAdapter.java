package com.fil.github_client.base.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.List;

public abstract class RecyclerAdapter<ITEM, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected List<ITEM> values;

    public RecyclerAdapter(List<ITEM> values) {
        this.values = values;
    }

    public void add(int position, ITEM item) {
        values.add(position, item);
        notifyItemInserted(position);
    }


    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }


    public void update(int position, ITEM item) {
        values.set(position, item);
        notifyItemChanged(position);
    }

    public void setValues(List<ITEM> newValues){
        values = newValues;
        notifyDataSetChanged();
    }
}
