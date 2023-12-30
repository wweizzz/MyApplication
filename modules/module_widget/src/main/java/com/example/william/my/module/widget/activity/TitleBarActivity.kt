package com.example.william.my.module.widget.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity
import com.example.william.my.basic.basic_module.router.path.ARouterPath
import com.example.william.my.core.widget.titlebar.TitleBar

@Route(path = ARouterPath.Widget.TitleBar)
class TitleBarActivity : BasicResponseActivity() {

    private var mTitleBar: TitleBar? = null

    override fun initView() {
        super.initView()

        initTitleBar()
    }

    private fun initTitleBar() {
        mTitleBar = TitleBar.build(mBinding.root)

        mTitleBar?.setTitle("标题")
        mTitleBar?.setBackPressed("返回")
        mTitleBar?.setBtnRight("菜单") {

        }
        mBinding.basicsResponse.text = "TitleBar"
    }
}