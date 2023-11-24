package com.example.william.my.library.fragment

import com.example.william.my.library.R
import com.example.william.my.library.databinding.BaseFragmentWebViewBinding

class BaseWebViewFragment :
    BaseVBFragment<BaseFragmentWebViewBinding>(R.layout.base_fragment_web_view) {
    override fun getViewBinding(): BaseFragmentWebViewBinding {
        return BaseFragmentWebViewBinding.inflate(layoutInflater)
    }
}