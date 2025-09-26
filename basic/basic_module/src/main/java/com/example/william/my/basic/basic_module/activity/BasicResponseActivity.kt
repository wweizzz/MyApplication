package com.example.william.my.basic.basic_module.activity

import android.os.Bundle
import android.view.Gravity
import android.view.View
import com.example.william.my.basic.basic_module.R
import com.example.william.my.basic.basic_module.databinding.BasicsLayoutResponseBinding
import com.example.william.my.lib.activity.BaseActivity

abstract class BasicResponseActivity : BaseActivity(), View.OnClickListener {

    protected lateinit var mBinding: BasicsLayoutResponseBinding

    override fun initViewBinding() {
        super.initViewBinding()
        mBinding = BasicsLayoutResponseBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

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
            response?.let {
                if (!it.startsWith("onResponse: ")) {
                    mBinding.basicsResponse.text = it
                    mBinding.basicsResponse.gravity = Gravity.CENTER
                } else {
                    mBinding.basicsResponse.text = it.formatString()
                    mBinding.basicsResponse.gravity = Gravity.NO_GRAVITY
                }
            }
        }
    }

    private fun String.formatString(): String {
        this.let {
            val json = StringBuilder()
            val indentString = StringBuilder()
            for (element in it) {
                when (element) {
                    '{', '[' -> {
                        json.append("\n").append(indentString).append(element).append("\n")
                        indentString.append("\t")
                        json.append(indentString)
                    }

                    '}', ']' -> {
                        indentString.deleteCharAt(indentString.length - 1)
                        json.append("\n").append(indentString).append(element)
                    }

                    ',' -> json.append(element).append("\n").append(indentString)
                    else -> json.append(element)
                }
            }
            return json.toString()
        }
    }
}