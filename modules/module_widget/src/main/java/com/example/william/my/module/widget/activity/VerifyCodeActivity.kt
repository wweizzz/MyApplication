package com.example.william.my.module.widget.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.path.ARouterPath
import com.example.william.my.lib.activity.BaseVBActivity
import com.example.william.my.module.widget.databinding.DemoActivityVerifyCodeBinding

@Route(path = ARouterPath.Widget.VerifyCode)
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