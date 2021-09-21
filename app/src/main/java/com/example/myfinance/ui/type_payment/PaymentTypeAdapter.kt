package com.example.myfinance.ui.type_payment

import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.example.myfinance.R
import com.example.myfinance.domain.models.PaymentType
import com.example.myfinance.ui.base.BaseAdapter
import com.example.myfinance.ui.base.BaseViewHolder

class PaymentTypeAdapter(
    listeners: Map<Int, OnViewClickListener>
): BaseAdapter<PaymentType, PaymentTypeAdapter.Vh>(
    R.layout.item_payment_type,
    listeners
) {
    class Vh constructor(
        itemView: View,
        viewIds: Set<Int?>?
    ) :
        BaseViewHolder<PaymentType?>(itemView, viewIds) {
        private var name: TextView? = null
        private var color: View? = null

        override fun bindView() {
            with(itemView) {
                name = findViewById(R.id.label_payment_name)
                color = findViewById(R.id.label_payment_color)
            }
        }

        override fun bindData(item: PaymentType?) {
            with(item) {
                name?.text =  "${item?.name} - ${item?.sum} " +
                        "${itemView.context.resources.getString(R.string.currency)}"
                color?.background?.setTint(Color.parseColor(item?.color))
            }
        }
    }

    override fun newVhInstance(view: View?, viewIds: MutableSet<Int>?): Vh {
        return Vh(view!!, viewIds)
    }
}