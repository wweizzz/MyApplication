package com.example.william.my.module.demo.activity3

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.module.demo.hook.HookManager

@Route(path = RouterPath.Demo.Hook)
class HookActivity : BasicResponseActivity() {

    override fun initView() {
        super.initView()

        setTag()

        setHook()
    }

    private fun setTag() {
        HookManager.setViewTag(mBinding.basicsResponse, "name", "hook")
    }

    private fun setHook() {
        HookManager.hookOnClick(this, mBinding.basicsResponse)
    }

}