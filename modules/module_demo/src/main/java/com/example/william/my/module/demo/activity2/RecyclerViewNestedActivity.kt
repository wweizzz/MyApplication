package com.example.william.my.module.demo.activity2

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.path.ARouterPath
import com.example.william.my.lib.activity.BaseVBActivity
import com.example.william.my.module.demo.adapter.RecyclerNestedAdapter
import com.example.william.my.module.demo.databinding.DemoActivityRecyclerViewBinding

@Route(path = ARouterPath.Demo.RecyclerViewNested)
class RecyclerViewNestedActivity : BaseVBActivity<DemoActivityRecyclerViewBinding>() {

    override fun getViewBinding(): DemoActivityRecyclerViewBinding {
        return DemoActivityRecyclerViewBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()

        initRecycleView()
    }

    private fun initRecycleView() {
        val data = arrayListOf<String>()
        for (i in 1..20) {
            data.add("POSITION $i")
        }

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = RecyclerView.VERTICAL

        mBinding.recycleView.layoutManager = layoutManager
        mBinding.recycleView.adapter = RecyclerNestedAdapter(data)
    }
}