package com.example.myfinance.ui.type_payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myfinance.databinding.FragmentPaymentTypeBinding

class PaymentTypeFragment : Fragment() {

    private lateinit var paymentTypeViewModel: PaymentTypeViewModel
    private var _binding: FragmentPaymentTypeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        paymentTypeViewModel =
            ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
                PaymentTypeViewModel::class.java
            )

        _binding = FragmentPaymentTypeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        paymentTypeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}