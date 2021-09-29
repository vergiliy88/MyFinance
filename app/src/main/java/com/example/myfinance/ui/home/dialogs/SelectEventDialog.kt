package com.example.myfinance.ui.home.dialogs


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.IntDef
import com.example.myfinance.databinding.DialogSelectEventTypeBinding
import com.example.myfinance.ui.base.BaseDialog
import com.example.myfinance.ui.models.StatisticDate
import com.example.myfinance.utils.SettingsState
import java.util.*

open class SelectEventDialog constructor(): BaseDialog<DialogSelectEventTypeBinding>() {
    private var listener: SelectTypeEvent? = null
    private var selectedDate: StatisticDate? = null

    internal constructor(
        listener: SelectTypeEvent?,
        date: StatisticDate?
    ) : this() {
        this.listener = listener
        this.selectedDate = date
    }

    internal constructor(dialog: SelectEventDialog, date: StatisticDate) :
            this(dialog.listener, date)


    companion object {
        const val TAG = "SelectEventDialog"
        const val TAG_SELECT_DATE = "select_date"

        @IntDef(EventType.VIEW,
            EventType.EDIT,
            EventType.DEL)

        annotation class EventType {
            companion object {
                const val VIEW = 1
                const val EDIT = 2
                const val DEL = 3
                const val ADD = 4
            }
        }

        fun newInstance(date: Long): SelectEventDialog {
            val args = Bundle()
            args.putLong(TAG_SELECT_DATE, date)
            val fragment = SelectEventDialog()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = DialogSelectEventTypeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val buttonViewPayment: Button = binding.buttonViewPayment
        val buttonEditPayment: Button = binding.buttonEditPayment
        val buttonDelPayment: Button = binding.buttonDelPayment
        val buttonAddPayment: Button = binding.buttonAddPayment

        if(SettingsState.enabledComments || SettingsState.paymentReceived) {
            buttonEditPayment.visibility = View.VISIBLE
        } else {
            buttonEditPayment.visibility = View.GONE
        }


        buttonAddPayment.setOnClickListener {
            selectedDate?.let {
                listener?.onSelectTypeEvent(EventType.ADD, it)
            }
            dismiss()
        }

        buttonViewPayment.setOnClickListener {
            selectedDate?.let{
                listener?.onSelectTypeEvent(EventType.VIEW, it)
            }
            dismiss()
        }

        buttonEditPayment.setOnClickListener {
            selectedDate?.let{
                listener?.onSelectTypeEvent(EventType.EDIT, it)
            }
            dismiss()
        }

        buttonDelPayment.setOnClickListener {
            selectedDate?.let{
                listener?.onSelectTypeEvent(EventType.DEL, it)
            }
            dismiss()
        }

        return root
    }

    interface SelectTypeEvent {
        fun onSelectTypeEvent(type: Int, date: StatisticDate)
    }

    open class Builder {
        private var listener: SelectTypeEvent? = null
        private var date: StatisticDate? = null

        fun setListener(listener: SelectTypeEvent): Builder {
            this.listener = listener
            return this
        }

        fun setDate(date: StatisticDate): Builder {
            this.date = date
            return this
        }

        open fun build(): SelectEventDialog {
            return SelectEventDialog(listener, date)
        }
    }
}