package com.example.william.my.module.opensource.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.lib.activity.BaseVBActivity
import com.example.william.my.module.opensource.adapter.SwipeRecyclerAdapter
import com.example.william.my.module.opensource.databinding.OpenActivitySwipeLayoutBinding

/**
 * https://github.com/daimajia/AndroidSwipeLayout
 */
@Route(path = RouterPath.Opensource.SwipeLayout)
class SwipeLayoutActivity : BaseVBActivity<OpenActivitySwipeLayoutBinding>() {

    override fun getViewBinding(): OpenActivitySwipeLayoutBinding {
        return OpenActivitySwipeLayoutBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()

        initRecycleView()
    }

    private fun initRecycleView() {
        val data = arrayListOf<String>()
        for (i in 0..19) {
            data.add("item : " + (i + 1))
        }
        mBinding.swipeRecycleView.adapter = SwipeRecyclerAdapter(data)
    }
}