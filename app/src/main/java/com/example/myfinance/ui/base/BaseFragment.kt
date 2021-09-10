package com.example.myfinance.ui.base


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider


open class BaseFragment<VB, D>: Fragment() {

    var currentViewModal: BaseViewModal<D> = BaseViewModal()
    var _viewModal: BaseViewModal<D> = currentViewModal
    var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _viewModal =
            ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
                currentViewModal::class.java
            )
        return View(this.context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}