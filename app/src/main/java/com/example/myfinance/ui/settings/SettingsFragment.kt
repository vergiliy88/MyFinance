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

        val switchHourlyPayment = binding.isHourlyPayment
        val switchEnabledComments = binding.enabledComments
        val switchIsReplayPayments = binding.isReplayPayments
        val switchPaymentReceived = binding.paymentReceived
        val labelIsReplayPaymentsLabel = binding.labelIsReplayPayments
        val labelIsHourlyPayment = binding.labelIsHourlyPayment

        //TODO TEMP
        switchIsReplayPayments.visibility = GONE
        labelIsHourlyPayment.visibility = GONE
        labelIsReplayPaymentsLabel.visibility = GONE
        switchHourlyPayment.visibility = GONE


        _viewModal.settings.observe(viewLifecycleOwner, {settings ->
            settings.hourlyPayment?.let{
                switchHourlyPayment.isChecked = it
            }

            settings.enabledComments?.let{
                switchEnabledComments.isChecked = it
            }

            settings.isReplayPayments?.let{
                switchIsReplayPayments.isChecked = it
            }

            settings.paymentReceived?.let{
                switchPaymentReceived.isChecked = true
            }
        })

        switchHourlyPayment.setOnCheckedChangeListener { _, isChecked ->
            val settings = _viewModal.settings.value!!
            settings.hourlyPayment = isChecked
            _viewModal.setSettings(settings)
        }

        switchEnabledComments.setOnCheckedChangeListener { _, isChecked ->
            val settings = _viewModal.settings.value!!
            settings.enabledComments = isChecked
            _viewModal.setSettings(settings)
        }

        switchIsReplayPayments.setOnCheckedChangeListener { _, isChecked ->
            val settings = _viewModal.settings.value!!
            settings.isReplayPayments = isChecked
            _viewModal.setSettings(settings)
        }

        switchPaymentReceived.setOnCheckedChangeListener { _, isChecked ->
            val settings = _viewModal.settings.value!!
            settings.paymentReceived = isChecked
            _viewModal.setSettings(settings)
        }

        return root
    }

}