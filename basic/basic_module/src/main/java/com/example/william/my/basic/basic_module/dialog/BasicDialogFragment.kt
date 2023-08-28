package com.example.william.my.basic.basic_module.dialog

import android.os.Bundle
import android.view.View
import com.example.william.my.basic.basic_module.databinding.BasicsLayoutDiallogBinding
import com.example.william.my.library.R
import com.example.william.my.library.dialog.BaseVBDialogFragment

class BasicDialogFragment :
    BaseVBDialogFragment<BasicsLayoutDiallogBinding>(R.style.Basics_Dialog_Translate_Slide_Alpha) {

    override fun getViewBinding(): BasicsLayoutDiallogBinding {
        return BasicsLayoutDiallogBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun showMessage(message: String?) {
        mBinding.dialog.basicsResponse.text = message
    }
}