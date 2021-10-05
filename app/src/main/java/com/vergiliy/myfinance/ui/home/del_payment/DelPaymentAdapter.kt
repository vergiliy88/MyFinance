package com.vergiliy.myfinance.ui.home.del_payment

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import com.vergiliy.myfinance.R
import com.vergiliy.myfinance.domain.models.PaymentJoinPaymentType
import com.vergiliy.myfinance.ui.base.BaseAdapter
import com.vergiliy.myfinance.ui.base.BaseViewHolder
import com.vergiliy.myfinance.utils.SettingsState

class DelPaymentAdapter  : BaseAdapter<PaymentJoinPaymentType, DelPaymentAdapter.Vh>(
    R.layout.item_payment_template,
) {
    lateinit var onSelectPaymentForDel: SelectPayment

    fun setCheckBoxListener(listenerEditText: SelectPayment) {
        onSelectPaymentForDel = listenerEditText
    }

    class Vh constructor(
        itemView: View,
        viewIds: Set<Int?>?,
        private val onSelectPaymentForDel: SelectPayment
    ) :
        BaseViewHolder<PaymentJoinPaymentType?>(itemView, viewIds) {

        private var paymentParam: TextView? = null
        private var color: View? = null
        private var isSelectedType: CheckBox? = null
        private var comment: EditText? = null
        private var realSum: EditText? = null

        override fun bindView() {
            with(itemView) {
                paymentParam = findViewById(R.id.label_payment_param)
                color = findViewById(R.id.label_payment_template_color)
                isSelectedType = findViewById(R.id.check_box_selected)
                comment = findViewById(R.id.edit_text_comment)
                realSum = findViewById(R.id.edit_text_real_sum)
            }
        }

        @SuppressLint("SetTextI18n")
        override fun bindData(item: PaymentJoinPaymentType?) {
            with(item) {
                paymentParam?.text =  "${item?.paymentTypeName} - ${item?.sum} " +
                        itemView.context.resources.getString(R.string.currency)
                color?.background?.setTint(Color.parseColor(item?.paymentColor))
                comment?.setText(item?.comment ?: "")
                val realSumValue = item?.realSum ?: ""
                realSum?.setText(realSumValue.toString())
                comment?.isEnabled = false
                realSum?.isEnabled = false

                if(!SettingsState.enabledComments) {
                    comment?.visibility = View.GONE
                }

                if(!SettingsState.paymentReceived) {
                    realSum?.visibility = View.GONE
                }

                isSelectedType?.setOnCheckedChangeListener{ view, isChecked  ->
                    onSelectPaymentForDel.onSelectPayment(adapterPosition, isChecked)
                }

            }
        }
    }

    override fun newVhInstance(view: View?, viewIds: MutableSet<Int>?): Vh {
        return Vh(view!!, viewIds, onSelectPaymentForDel)
    }

    interface SelectPayment {
        fun onSelectPayment(position: Int, isSelected: Boolean)
    }
}