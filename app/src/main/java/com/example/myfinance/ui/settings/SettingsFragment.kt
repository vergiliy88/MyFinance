package com.example.myfinance.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myfinance.databinding.FragmentPaymentTypeBinding

import com.example.myfinance.databinding.FragmentSettingsBinding
import com.example.myfinance.ui.base.BaseFragment
import com.example.myfinance.ui.type_payment.PaymentTypeViewModel

class SettingsFragment(viewModal: SettingsViewModel): BaseFragment<FragmentSettingsBinding, String>(viewModal) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textSettings

        _viewModal.data.observe(viewLifecycleOwner, Observer {
            it?.let {
                textView.text = it
            }
        })
        _viewModal.setData("Test String Payment Type")

        return root
    }
}