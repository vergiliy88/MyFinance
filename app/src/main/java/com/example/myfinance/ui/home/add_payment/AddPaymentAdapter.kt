package com.example.myfinance.ui.home.add_payment

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.GONE
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myfinance.R

import com.example.myfinance.ui.base.BaseAdapter
import com.example.myfinance.ui.base.BaseViewHolder
import com.example.myfinance.ui.models.PaymentTemplate
import com.example.myfinance.utils.SettingsState
import java.util.*

class AddPaymentAdapter: BaseAdapter<PaymentTemplate, AddPaymentAdapter.Vh>(
    R.layout.item_payment_template,
) {
    lateinit var setSelectedPaymentType: SetSelectedPaymentType
    lateinit var setCommentAndSumValue: SetCommentAndSumValue

    fun setCheckBoxListener(listenerSelect: SetSelectedPaymentType, listenerEditText: SetCommentAndSumValue) {
        setSelectedPaymentType = listenerSelect
        setCommentAndSumValue = listenerEditText
    }


    class Vh constructor(
        itemView: View,
        viewIds: Set<Int?>?,
        private val setSelectedPaymentType: SetSelectedPaymentType,
        private val setCommentAndSumValue: SetCommentAndSumValue
    ) :
        BaseViewHolder<PaymentTemplate?>(itemView, viewIds) {

        private var paymentParam: TextView? = null
        private var color: View? = null
        private var isSelectedType: CheckBox? = null
        private var comment: EditText? = null
        private var realSum: EditText? = null
        private var timerComment: Timer? = null
        private var timerSum: Timer? = null

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
        override fun bindData(item: PaymentTemplate?) {
            with(item) {
                paymentParam?.text =  "${item?.paymentParam} - ${item?.sum} " +
                        itemView.context.resources.getString(R.string.currency)
                color?.background?.setTint(Color.parseColor(item?.color))
                comment?.setText(item?.comment ?: "")
                val realSumValue = item?.realSum ?: ""
                realSum?.setText(realSumValue.toString())
                isSelectedType?.setChecked(item?.isSelected ?: false)

                if(!SettingsState.enabledComments) {
                    comment?.visibility = GONE
                }

                if(!SettingsState.paymentReceived) {
                    realSum?.visibility = GONE
                }

                isSelectedType?.setOnCheckedChangeListener{ view, isChecked  ->
                    setSelectedPaymentType.onSelectPaymentType(adapterPosition)
                }
                comment?.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        timerComment?.cancel()
                    }

                    override fun afterTextChanged(editable: Editable?) {
                        timerComment = Timer()
                        timerComment?.schedule(object : TimerTask() {
                            override fun run() {
                                editable?.let { text ->
                                    if (adapterPosition != RecyclerView.NO_POSITION) {
                                        setCommentAndSumValue.onSetComment(
                                            adapterPosition,
                                            text.toString()
                                        )
                                    }
                                }
                            }
                        }, 600)
                    }
                })

                realSum?.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        timerSum?.cancel()
                    }

                    override fun afterTextChanged(editable: Editable?) {
                        timerSum = Timer()
                        timerSum?.schedule(object : TimerTask() {
                            override fun run() {
                                editable?.let { text ->
                                    if (adapterPosition != RecyclerView.NO_POSITION) {
                                        if (text.isNotEmpty()) {
                                            setCommentAndSumValue.onSetSum(
                                                adapterPosition,
                                                text.toString().toDouble()
                                            )
                                        }

                                    }
                                }
                            }
                        }, 600)
                    }
                })
            }
        }
    }

    override fun newVhInstance(view: View?, viewIds: MutableSet<Int>?): Vh {
        return Vh(view!!, viewIds, setSelectedPaymentType, setCommentAndSumValue)
    }

    interface SetSelectedPaymentType{
        fun onSelectPaymentType(position: Int)
    }

    interface SetCommentAndSumValue {
        fun onSetComment(position: Int, value: String)
        fun onSetSum(position: Int, double: Double)
    }
}
