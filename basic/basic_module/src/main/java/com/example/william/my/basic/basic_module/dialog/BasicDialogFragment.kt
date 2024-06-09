package com.example.william.my.basic.basic_module.dialog

import com.example.william.my.basic.basic_module.databinding.BasicsLayoutDiallogBinding
import com.example.william.my.lib.R
import com.example.william.my.lib.dialog.BaseVBDialogFragment

class BasicDialogFragment :
    BaseVBDialogFragment<BasicsLayoutDiallogBinding>(R.style.Basics_Dialog_Translate_Slide_Alpha) {

    override fun getViewBinding(): BasicsLayoutDiallogBinding {
        return BasicsLayoutDiallogBinding.inflate(layoutInflater)
    }

    fun showMessage(message: String?) {
        mBinding.dialog.basicsResponse.text = message
    }
}