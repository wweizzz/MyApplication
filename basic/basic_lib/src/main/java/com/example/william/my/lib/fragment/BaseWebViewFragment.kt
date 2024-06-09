package com.example.william.my.lib.fragment

import com.example.william.my.lib.R
import com.example.william.my.lib.databinding.BaseFragmentWebViewBinding

class BaseWebViewFragment :
    BaseVBFragment<BaseFragmentWebViewBinding>(R.layout.base_fragment_web_view) {
    override fun getViewBinding(): BaseFragmentWebViewBinding {
        return BaseFragmentWebViewBinding.inflate(layoutInflater)
    }
}