package com.vergiliy.myfinance.ui.home.view_edit_payment

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
import com.vergiliy.myfinance.R
import com.vergiliy.myfinance.domain.models.PaymentJoinPaymentType
import com.vergiliy.myfinance.ui.base.BaseAdapter
import com.vergiliy.myfinance.ui.base.BaseViewHolder
import com.vergiliy.myfinance.utils.SettingsState
import java.util.*

class ViewEditPaymentAdapter : BaseAdapter<PaymentJoinPaymentType, ViewEditPaymentAdapter.Vh>(
    R.layout.item_payment_template,
) {
    lateinit var setCommentAndSumValue: SetCommentAndSumValue
    var isEditable = false

    fun setCheckBoxListener(listenerEditText: SetCommentAndSumValue) {
        setCommentAndSumValue = listenerEditText
    }

    fun setIsEditable(isEditable: Boolean) {
        this.isEditable = isEditable
    }


    class Vh constructor(
        itemView: View,
        viewIds: Set<Int?>?,
        private val setCommentAndSumValue: SetCommentAndSumValue,
        private val isEditable: Boolean
    ) :
        BaseViewHolder<PaymentJoinPaymentType?>(itemView, viewIds) {

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
        override fun bindData(item: PaymentJoinPaymentType?) {
            with(item) {
                paymentParam?.text =  "${item?.paymentTypeName} - ${item?.sum} " +
                        itemView.context.resources.getString(R.string.currency)
                color?.background?.setTint(Color.parseColor(item?.paymentColor))
                comment?.setText(item?.comment ?: "")
                val realSumValue = item?.realSum ?: ""
                realSum?.setText(realSumValue.toString())
                isSelectedType?.visibility = GONE

                if(!SettingsState.enabledComments) {
                    comment?.visibility = GONE
                }

                if(!SettingsState.paymentReceived) {
                    realSum?.visibility = GONE
                }

                if (!isEditable) {
                    comment?.isEnabled = false
                    realSum?.isEnabled = false
                }

                if (isEditable) {
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
    }

    override fun newVhInstance(view: View?, viewIds: MutableSet<Int>?): Vh {
        return Vh(view!!, viewIds, setCommentAndSumValue, isEditable)
    }

    interface SetCommentAndSumValue {
        fun onSetComment(position: Int, value: String)
        fun onSetSum(position: Int, double: Double)
    }
}