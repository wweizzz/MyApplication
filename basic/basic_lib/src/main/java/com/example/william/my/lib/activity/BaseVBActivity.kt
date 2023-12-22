package com.example.william.my.lib.activity

import android.os.Bundle
import androidx.viewbinding.ViewBinding

abstract class BaseVBActivity<VB : ViewBinding?> : BaseActivity() {

    private var _binding: VB? = null
    protected val mBinding get() = _binding!!

    protected abstract fun getViewBinding(): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        _binding = getViewBinding()
        setContentView(mBinding.root)

        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}