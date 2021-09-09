package com.example.myfinance.ui.type_payment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer

import com.example.myfinance.databinding.FragmentPaymentTypeBinding
import com.example.myfinance.ui.base.BaseFragment

class PaymentTypeFragment(viewModal: PaymentTypeViewModel): BaseFragment<FragmentPaymentTypeBinding, String>(viewModal) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = FragmentPaymentTypeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications

        _viewModal.data.observe(viewLifecycleOwner, Observer {
            it?.let {
                textView.text = it
            }
        })
        _viewModal.setData("Test String Payment Type")

        return root
    }
}