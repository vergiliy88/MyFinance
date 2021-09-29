package com.example.myfinance.ui.base


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myfinance.ui.MainActivity

import android.app.Activity
import com.example.myfinance.utils.UiUtils


open class BaseFragment<VB>: Fragment() {

    var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return View(this.context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    open fun setToolBarTitle(
        value: String?,
        activity: Activity?
    ) {
        if (activity is MainActivity) {
            activity.setToolbarTitle(UiUtils.setBoldValueInBKT(value))
        }
    }
}