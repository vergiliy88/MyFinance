package com.example.myfinance.utils



import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.myfinance.R


public class UiUtils {
    companion object {
        fun replaceFragment(
            fm: FragmentManager?,
            fragment: Fragment?
        ) {
            replaceFragment(fm, fragment, false, null)
        }

        fun replaceFragment(
            fm: FragmentManager?,
            fragment: Fragment?,
            backStackTag: String?
        ) {
            replaceFragment(fm, fragment, true, backStackTag)
        }

        private fun replaceFragment(
            fm: FragmentManager?,
            fragment: Fragment?,
            backStack: Boolean,
            backStackTag: String?
        ) {

            if (fm == null ||
                fragment == null
            ) {
                return
            }
            val transaction: FragmentTransaction = fm.beginTransaction()
                .setCustomAnimations(
                    R.anim.enter_from_right,
                    R.anim.exit_to_left,
                    R.anim.enter_from_left,
                    R.anim.exit_to_right
                )
                .replace(R.id.nav_host_fragment_activity_main, fragment, backStackTag)
            if (backStack) transaction.addToBackStack(backStackTag)
            transaction.commit()
        }
    }
}