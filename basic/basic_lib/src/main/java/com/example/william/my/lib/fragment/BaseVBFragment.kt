package com.example.william.my.lib.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding


abstract class BaseVBFragment<VB : ViewBinding?>(layout: Int = 0) :
    BaseFragment(layout) {

    private var _binding: VB? = null
    protected val mBinding get() = _binding!!

    protected abstract fun getViewBinding(): VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding()
        return mBinding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}