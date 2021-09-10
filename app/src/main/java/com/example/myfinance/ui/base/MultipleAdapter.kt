package com.example.myfinance.ui.base;

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MultipleAdapter(var delegates: List<VhDelegate<*, *>>):
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected lateinit var items: MutableList<Any>

    init {
        items = listOf<Any>().toMutableList()
    }

    override fun getItemViewType(position: Int): Int {
        val item = items[position]
        for (i in delegates.indices)
            if (delegates.get(i).isForType (item))
                return i;
        throw Exception ("There is no suitable delegate")
    }

    override fun onCreateViewHolder (parent: ViewGroup,
                                     viewType: Int)
            : RecyclerView.ViewHolder {

        return delegates.get (viewType)
                .onCreateViewHolder (parent)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder (holder: RecyclerView.ViewHolder,
                                   position: Int) {

        val pos = holder.adapterPosition
        if (isCorrectPos(pos))
            getDelegate (pos)
                    .onBindViewHolder (holder, items.get(pos))
    }

    private fun isCorrectPos(pos: Int) = 0 <= pos && pos < itemCount

    override fun onBindViewHolder (holder: RecyclerView.ViewHolder,
                                   position: Int,
                                   payloads: MutableList<Any>) {
        if (payloads.isEmpty())
            super.onBindViewHolder (holder, position, payloads)
        else {
            val pos = holder.adapterPosition
            if (isCorrectPos (pos)) {
                getDelegate (pos)
                        .onBindViewHolder (holder, position, payloads)
            }
        }
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        val pos = holder.adapterPosition
        if (isCorrectPos(pos))
            getDelegate(pos)
                .onViewDetachedFromWindow (holder)
        super.onViewDetachedFromWindow(holder)
    }

    private fun getDelegate(pos: Int): VhDelegate<*, *> {
        val viewType = getItemViewType(pos)
        return delegates.get(viewType)
    }

    fun populate(items: List<Any>) {
        this.items.addAll (items)
        notifyDataSetChanged()
    }

    fun clear() {
        items.clear()
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        val pos = holder.adapterPosition
        if (isCorrectPos(pos))
            getDelegate(pos)
                    .onViewAttachedToWindow (holder)
    }

    class Builder () {
        private val delegates = LinkedList<VhDelegate<*, *>>()
        fun addDelegate (delegate: VhDelegate<*,*>):Builder {
            delegates.add (delegate)
            return this
        }

        fun build ():MultipleAdapter {
            if (delegates.isEmpty())
                throw java.lang.Exception ("There are no delegates")
            return MultipleAdapter (delegates)
        }
    }
}