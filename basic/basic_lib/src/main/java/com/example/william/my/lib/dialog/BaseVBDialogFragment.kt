package com.example.william.my.lib.dialog

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.launcher.ARouter

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