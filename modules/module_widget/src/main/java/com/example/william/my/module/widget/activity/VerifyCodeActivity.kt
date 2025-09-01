package com.example.william.my.module.widget.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.lib.activity.BaseVBActivity
import com.example.william.my.module.widget.databinding.DemoActivityVerifyCodeBinding

@Route(path = RouterPath.Widget.VerifyCode)
class VerifyCodeActivity : BaseVBActivity<DemoActivityVerifyCodeBinding>() {

    override fun getViewBinding(): DemoActivityVerifyCodeBinding {
        return DemoActivityVerifyCodeBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        initVerifyCode()
    }

    private fun initVerifyCode() {
        mBinding.verifyCodeView.editContent = "0731"
    }
}