package com.vergiliy.myfinance.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.vergiliy.myfinance.databinding.ActivitySplashBinding
import com.vergiliy.myfinance.ui.type_payment.SplashViewModal
import com.vergiliy.myfinance.utils.SettingsState
import android.content.Intent
import android.os.Handler
import android.os.Looper


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var _viewModal: SplashViewModal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        _viewModal = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            SplashViewModal::class.java
        )

        _viewModal.settings.observe(this, {
            it?.let {
                SettingsState.enabledComments = it.enabledComments ?: false
                SettingsState.hourlyPayment = it.hourlyPayment ?: false
                SettingsState.isReplayPayments = it.isReplayPayments ?: false
                SettingsState.paymentReceived = it.paymentReceived ?: false
                Handler(Looper.getMainLooper()).postDelayed({
                    val i = Intent(this, MainActivity::class.java)
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(i)
                    finish()
                }, 1000)
            }
        })
    }
}