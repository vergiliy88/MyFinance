package com.vergiliy.myfinance.ui

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vergiliy.myfinance.R
import com.vergiliy.myfinance.databinding.ActivityMainBinding
import com.vergiliy.myfinance.ui.home.HomeFragment
import com.vergiliy.myfinance.ui.settings.SettingsFragment
import com.vergiliy.myfinance.ui.statistics.StatisticsFragment
import com.vergiliy.myfinance.ui.type_payment.PaymentTypeFragment
import com.vergiliy.myfinance.utils.UiUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.text.SpannableString





class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModal
    private var toolbar: ActionBar? = null

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

        mainViewModel.positionMenu.observe(this, Observer { menuItem ->
            if (menuItem == null) {
                loadFragment(HomeFragment.newInstance())
                toolbar?.setTitle(R.string.title_home)
                mainViewModel.setCurrentMenuId(R.id.navigation_home)
            }
        })
    }

    private fun showSelectedScreen(menuId: Int): Boolean {
        val fragment: Fragment
        var  result = false
        when (menuId) {
            R.id.navigation_home -> {
                toolbar!!.title = applicationContext.getString(R.string.title_home)
                fragment = HomeFragment.newInstance()
                loadFragment(fragment)
                result = true
            }
            R.id.navigation_statistics -> {
                toolbar!!.title = applicationContext.getString(R.string.title_statistics)
                fragment = StatisticsFragment.newInstance()
                loadFragment(fragment)
                result = true
            }
            R.id.navigation_type_payment -> {
                toolbar!!.title = applicationContext.getString(R.string.title_type_payment)
                fragment = PaymentTypeFragment.newInstance()
                loadFragment(fragment)
                result = true
            }
            R.id.navigation_settings -> {
                toolbar!!.title = applicationContext.getString(R.string.title_settings)
                fragment = SettingsFragment.newInstance()
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

    fun setToolbarTitle(title: SpannableString?) {
        toolbar?.title = title
    }
}