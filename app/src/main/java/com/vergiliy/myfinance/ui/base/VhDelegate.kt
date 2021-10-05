package com.vergiliy.myfinance.ui.base;


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


abstract class VhDelegate<T, V: BaseViewHolder<T>>
    (var listeners: Map<Int, BaseAdapter.OnViewClickListener>) {


    abstract fun isForType (obj: Any): Boolean
    abstract fun getLayoutRes(): Int
    abstract fun newInstance (view: View): V

    open fun onCreateViewHolder(parent: ViewGroup): BaseViewHolder<T> = newInstance (
                LayoutInflater.from (parent.context)
                        .inflate (
                                getLayoutRes(),
                                parent,
                                false)
        )

    open fun onBindViewHolder (vh: RecyclerView.ViewHolder,
                          item: Any) {

        with (vh as V) {
            bindData (item as T)
            bindListeners (listeners)
        }
    }

    open fun onBindViewHolder (holder: RecyclerView.ViewHolder,
                          position: Int,
                          payloads: MutableList<Any>) {}

    open fun onViewDetachedFromWindow (vh: RecyclerView.ViewHolder) {
        (vh as V).unbindListeners()
    }

    open fun onViewAttachedToWindow (vh: RecyclerView.ViewHolder) {
        (vh as V).bindListeners(listeners)
    }
}