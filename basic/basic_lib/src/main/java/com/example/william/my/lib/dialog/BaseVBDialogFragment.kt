package com.example.william.my.lib.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

abstract class BaseVBDialogFragment<VB : ViewBinding?>(
    windowAnimationsRes: Int = 0
) : BaseDialogFragment(
    layout = 0,
    windowAnimationsRes = windowAnimationsRes
) {

    private var _binding: VB? = null
    protected val mBinding get() = _binding!!

    protected abstract fun getViewBinding(): VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = getViewBinding()
        return mBinding.root
    }
}