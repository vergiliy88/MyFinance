package com.example.myfinance.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.myfinance.databinding.FragmentSettingsBinding
import com.example.myfinance.ui.base.BaseFragment


class SettingsFragment: BaseFragment<FragmentSettingsBinding>() {

    companion object {
        @JvmStatic
        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }

    private lateinit var _viewModal: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewModal =
            ViewModelProvider(requireActivity()).get(SettingsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val hourlyPayment = binding.isHourlyPayment
        val enabledComments = binding.enabledComments
        val isReplayPayments = binding.isReplayPayments
        val paymentReceived = binding.paymentReceived
        val isReplayPaymentsLabel = binding.labelIsReplayPayments
        val labelIsHourlyPayment = binding.labelIsHourlyPayment

        //TODO TEMP
        isReplayPayments.visibility = GONE
        labelIsHourlyPayment.visibility = GONE
        isReplayPaymentsLabel.visibility = GONE
        hourlyPayment.visibility = GONE


        _viewModal.settings.observe(viewLifecycleOwner, {settings ->
            settings.hourlyPayment?.let{
                hourlyPayment.isChecked = it
            }

            settings.enabledComments?.let{
                enabledComments.isChecked = it
            }

            settings.isReplayPayments?.let{
                isReplayPayments.isChecked = it
            }

            settings.paymentReceived?.let{
                paymentReceived.isChecked = true
            }
        })

        hourlyPayment.setOnCheckedChangeListener { _, isChecked ->
            val settings = _viewModal.settings.value!!
            settings.hourlyPayment = isChecked
            _viewModal.setSettings(settings)
        }

        enabledComments.setOnCheckedChangeListener { _, isChecked ->
            val settings = _viewModal.settings.value!!
            settings.enabledComments = isChecked
            _viewModal.setSettings(settings)
        }

        isReplayPayments.setOnCheckedChangeListener { _, isChecked ->
            val settings = _viewModal.settings.value!!
            settings.isReplayPayments = isChecked
            _viewModal.setSettings(settings)
        }

        paymentReceived.setOnCheckedChangeListener { _, isChecked ->
            val settings = _viewModal.settings.value!!
            settings.paymentReceived = isChecked
            _viewModal.setSettings(settings)
        }

        return root
    }

}