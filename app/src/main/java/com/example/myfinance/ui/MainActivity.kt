package com.example.myfinance.ui

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myfinance.R
import com.example.myfinance.databinding.ActivityMainBinding
import com.example.myfinance.ui.home.HomeFragment
import com.example.myfinance.ui.home.HomeViewModel
import com.example.myfinance.ui.settings.SettingsFragment
import com.example.myfinance.ui.settings.SettingsViewModel
import com.example.myfinance.ui.statistics.StatisticsFragment
import com.example.myfinance.ui.statistics.StatisticsViewModel
import com.example.myfinance.ui.type_payment.PaymentTypeFragment
import com.example.myfinance.ui.type_payment.PaymentTypeViewModel
import com.example.myfinance.utils.UiUtils
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModal
    private var toolbar: ActionBar? = null
    private var menuPosition: Int?  = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel =
            ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
                MainViewModal::class.java
            )

        toolbar = supportActionBar
        val navigation: BottomNavigationView = binding.navView
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        mainViewModel.positionMenu.observe(this, Observer {
            menuPosition = it
        })

        menuPosition = mainViewModel.positionMenu.value

        if (menuPosition == null) {

            loadFragment(HomeFragment.newInstance(HomeViewModel()))
            toolbar?.setTitle(R.string.title_home)
        } else {
            showSelectedScreen(menuPosition!!)
        }
    }

    private fun showSelectedScreen(menuId: Int): Boolean {
        val fragment: Fragment
        var  result = false
        when (menuId) {
            R.id.navigation_home -> {
                toolbar!!.title = applicationContext.getString(R.string.title_home)
                fragment = HomeFragment.newInstance(HomeViewModel())
                loadFragment(fragment)
                result = true
            }
            R.id.navigation_statistics -> {
                toolbar!!.title = applicationContext.getString(R.string.title_statistics)
                fragment = StatisticsFragment.newInstance(StatisticsViewModel())
                loadFragment(fragment)
                result = true
            }
            R.id.navigation_type_payment -> {
                toolbar!!.title = applicationContext.getString(R.string.title_type_payment)
                fragment = PaymentTypeFragment.newInstance(PaymentTypeViewModel())
                loadFragment(fragment)
                result = true
            }
            R.id.navigation_settings -> {
                toolbar!!.title = applicationContext.getString(R.string.title_settings)
                fragment = SettingsFragment.newInstance(SettingsViewModel())
                loadFragment(fragment)
                result = true
            }
        }
        return result
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            mainViewModel.setCurrentMenuId(item.itemId)
            showSelectedScreen(item.itemId)
        }

    private fun loadFragment(fragment: Fragment) {
        UiUtils.replaceFragment(supportFragmentManager, fragment)
    }
}