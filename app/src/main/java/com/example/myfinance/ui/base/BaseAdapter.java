package com.example.myfinance.ui.base;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by develop32 on 25.07.18.
 */

public abstract class BaseAdapter<T, VH extends BaseViewHolder<T>>
        extends RecyclerView.Adapter<VH> {

    private int resId;
    protected List<T> items;


    private Map<Integer, OnViewClickListener> listeners;

    public BaseAdapter (int resId) {
        this (resId, new LinkedHashMap<>());
    }

    public BaseAdapter (int resId,
                        Map<Integer, OnViewClickListener> listeners) {
        items = new LinkedList<>();
        this.listeners = listeners;

        this.resId = resId;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder (@NonNull ViewGroup parent,
                                  int i) {

        View view = LayoutInflater.from (parent.getContext())
                .inflate (
                        resId,
                        parent,
                        false);

        return newVhInstance (view, listeners.keySet());
    }

    @Override
    public void onBindViewHolder (@NonNull VH vh,
                                  int pos) {
        vh.bindData (items.get(pos));
        bindListeners(vh);
    }

    protected void bindListeners(@NonNull VH vh) {
        vh.bindListeners (listeners);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull VH holder) {
        holder.unbindListeners();
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void populate (List<? extends T> items) {
        this.items.addAll (items);
        notifyDataSetChanged();
    }

    public void clear () {
        items.clear();
    }


    public abstract VH newVhInstance (View view,
                                      Set<Integer> viewIds);


    @Override
    public void onViewAttachedToWindow(@NonNull VH holder) {
        super.onViewAttachedToWindow(holder);
        holder.bindListeners (listeners);
    }

    public interface OnViewClickListener {
        void onClick (View view,
                      Object value);
    }

    public List<T> getData() {
        return items;
    }
}