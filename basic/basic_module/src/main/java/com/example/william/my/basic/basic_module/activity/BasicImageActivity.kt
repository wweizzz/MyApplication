package com.example.william.my.basic.basic_module.activity

import android.os.Bundle
import android.view.View
import com.example.william.my.basic.basic_module.R
import com.example.william.my.basic.basic_module.databinding.BasicsLayoutImageBinding
import com.example.william.my.lib.activity.BaseActivity

abstract class BasicImageActivity : BaseActivity(), View.OnClickListener {

    protected lateinit var mBinding: BasicsLayoutImageBinding

    override fun initViewBinding() {
        super.initViewBinding()
        mBinding = BasicsLayoutImageBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        mBinding.basicsImage.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.basics_image) {
            onImageClick(v)
        }
    }

    protected open fun onImageClick(view: View) {

    }
}