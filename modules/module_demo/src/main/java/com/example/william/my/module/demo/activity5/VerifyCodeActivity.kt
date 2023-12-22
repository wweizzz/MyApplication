package com.example.william.my.module.demo.activity5

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.path.ARouterPath
import com.example.william.my.lib.activity.BaseVBActivity
import com.example.william.my.module.demo.databinding.DemoActivityVerifyCodeBinding

@Route(path = ARouterPath.Demo.VerifyCode)
class VerifyCodeActivity : BaseVBActivity<DemoActivityVerifyCodeBinding>() {

    override fun getViewBinding(): DemoActivityVerifyCodeBinding {
        return DemoActivityVerifyCodeBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()

        initVerifyCode()
    }

    private fun initVerifyCode() {
        mBinding.verifyCodeView.editContent = "0731"
    }
}