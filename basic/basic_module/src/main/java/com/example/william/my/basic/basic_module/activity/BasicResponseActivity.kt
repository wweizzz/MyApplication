package com.example.william.my.basic.basic_module.activity

import android.os.Bundle
import android.view.View
import com.example.william.my.basic.basic_module.R
import com.example.william.my.basic.basic_module.databinding.BasicsLayoutResponseBinding
import com.example.william.my.library.activity.BaseActivity

abstract class BasicResponseActivity : BaseActivity(), View.OnClickListener {

    protected lateinit var mBinding: BasicsLayoutResponseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        mBinding = BasicsLayoutResponseBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        super.onCreate(savedInstanceState)
    }


    override fun initView() {
        super.initView()

        mBinding.basicsResponse.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.basics_response) {
            onResponseClick(v)
        }
    }

    protected open fun onResponseClick(view: View) {

    }

    protected fun showResponse(response: String?) {
        runOnUiThread {
            mBinding.basicsResponse.text = response
        }
    }
}