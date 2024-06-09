package com.example.william.my.module.demo.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.lib.activity.BaseVBActivity
import com.example.william.my.module.demo.adapter.RecyclerAdapter
import com.example.william.my.module.demo.databinding.DemoActivityFlexBoxBinding
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager

@Route(path = RouterPath.Demo.FlexBox)

class FlexBoxActivity : BaseVBActivity<DemoActivityFlexBoxBinding>() {

    override fun getViewBinding(): DemoActivityFlexBoxBinding {
        return DemoActivityFlexBoxBinding.inflate(layoutInflater)
    }

    private val mData = arrayListOf(
        "FlexBox_1234",
        "FlexBox_5678",
        "FlexBox_90",
        "FlexBox_123456",
        "FlexBox_567890",
        "FlexBox_12345678",
        "FlexBox_1234567890",
        "FlexBox_123",
        "FlexBox_456",
    )

    override fun initView() {
        super.initView()

        initFlexBox()
    }

    private fun initFlexBox() {
        val manager = FlexboxLayoutManager(this)
        manager.flexDirection = FlexDirection.ROW //主轴方向
        manager.flexWrap = FlexWrap.WRAP //是否换行

        mBinding.flexboxRecycleView.layoutManager = manager
        mBinding.flexboxRecycleView.adapter = RecyclerAdapter(mData)
    }
}