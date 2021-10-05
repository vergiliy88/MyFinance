package com.vergiliy.myfinance.ui.type_payment.dialogs


import android.widget.ArrayAdapter
import android.content.Context
import android.graphics.Color
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.vergiliy.myfinance.databinding.SpinnerItemBinding


class ColorArrayAdapter(context: Context, resource: Int, var items: Array<String>)
    : ArrayAdapter<String>(context, resource, items) {

    val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getDropDownView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: SpinnerItemBinding = SpinnerItemBinding.inflate(inflater, parent, false)
        val root: View = binding.root
        val colorItem : View = binding.itemColor
        colorItem.setBackgroundColor(Color.parseColor(items[position]))
        return root
    }
}