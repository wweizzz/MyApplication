package com.example.william.my.library.fragment

import com.example.william.my.library.R
import com.example.william.my.library.databinding.BasicsFragmentWebViewBinding

class BaseWebViewFragment :
    BaseVBFragment<BasicsFragmentWebViewBinding>(R.layout.basics_fragment_web_view) {
    override fun getViewBinding(): BasicsFragmentWebViewBinding {
        return BasicsFragmentWebViewBinding.inflate(layoutInflater)
    }
}