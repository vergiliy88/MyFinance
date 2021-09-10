package com.example.myfinance.ui.base;

import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import java.util.Map;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by develop32 on 25.07.18.
 */

public abstract class BaseViewHolder<T>
        extends RecyclerView.ViewHolder {

    protected SparseArray<View> clickedViews;


    public BaseViewHolder (@NonNull View itemView,
                           Set<Integer> viewIds) {
        super(itemView);
        bindView ();

        clickedViews = new SparseArray<> (viewIds.size());
        for (Integer viewId : viewIds) {
            View clickedView = itemView.findViewById (viewId);
            if (clickedView != null) {
                clickedView.setClickable (true);
                clickedViews.append (
                        viewId,
                        clickedView);
            }
        }
    }

    public void bindListeners (Map<Integer, BaseAdapter.OnViewClickListener> listeners) {
        for (Map.Entry<Integer, BaseAdapter.OnViewClickListener> listenerEntry :
                listeners.entrySet()) {
            clickedViews.get (
                    listenerEntry.getKey()).setOnClickListener (
                            v -> listenerEntry.getValue()
                                    .onClick (v, getOnClickValue()));
        }
    }

    Object getOnClickValue() {
        return getAdapterPosition();
    }

    void unbindListeners () {
        for (int i = 0; i < clickedViews.size(); i++) {
            clickedViews.valueAt(i).setOnClickListener (null);
        }
    }

    public abstract void bindData (T item);
    public abstract void bindView ();
}
