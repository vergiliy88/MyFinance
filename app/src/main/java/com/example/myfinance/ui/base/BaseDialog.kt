package com.example.myfinance.ui.base


import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.DialogFragment

open class BaseDialog<VB> : DialogFragment() {

    var _binding: VB? = null
    val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return View(this.context)
    }

    override fun onResume() {
        super.onResume()
        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            val params = dialog!!.window!!.attributes
            params.width = LinearLayoutCompat.LayoutParams.MATCH_PARENT
            dialog!!.window!!.attributes = params as android.view.WindowManager.LayoutParams
        } else {
            val displayMetrics = requireContext().resources.displayMetrics
            val dpWidth = displayMetrics.widthPixels
            val params = dialog!!.window!!.attributes
            params.height = LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            dialog!!.window!!.setLayout(dpWidth ,
                params.height)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}