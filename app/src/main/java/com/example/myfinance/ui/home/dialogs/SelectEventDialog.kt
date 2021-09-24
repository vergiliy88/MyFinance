package com.example.myfinance.ui.home.dialogs


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.IntDef
import com.example.myfinance.databinding.DialogSelectEventTypeBinding
import com.example.myfinance.ui.base.BaseDialog
import java.util.*

open class SelectEventDialog protected constructor(): BaseDialog<DialogSelectEventTypeBinding>() {
    private var listener: SelectTypeEvent? = null
    private var selectedDate: Calendar? = null

    internal constructor(
        listener: SelectTypeEvent?
    ) : this() {
        this.listener = listener
    }

    internal constructor(dialog: SelectEventDialog) :
            this(dialog.listener)


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
        fun onSelectTypeEvent(type: Int, date: Calendar)
    }

    open class Builder {
        private var listener: SelectTypeEvent? = null
        private var date: Calendar? = null

        fun setListener(listener: SelectTypeEvent): Builder {
            this.listener = listener
            return this
        }

        fun setDate(date: Calendar): Builder {
            this.date = date
            return this
        }

        open fun build(): SelectEventDialog {
            return SelectEventDialog(listener)
        }
    }
}