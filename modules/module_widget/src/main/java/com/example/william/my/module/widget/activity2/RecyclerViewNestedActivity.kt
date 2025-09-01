package com.example.william.my.module.widget.activity2

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.lib.activity.BaseVBActivity
import com.example.william.my.module.widget.adapter.RecyclerNestedAdapter
import com.example.william.my.module.widget.databinding.DemoActivityRecyclerNestedBinding

@Route(path = RouterPath.Widget.RecyclerViewNested)
class RecyclerViewNestedActivity : BaseVBActivity<DemoActivityRecyclerNestedBinding>() {

    override fun getViewBinding(): DemoActivityRecyclerNestedBinding {
        return DemoActivityRecyclerNestedBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        initRecycleView()
    }

    private fun initRecycleView() {
        val data = arrayListOf<String>()
        for (i in 1..20) {
            data.add("POSITION $i")
        }

        mBinding.recycleView.layoutManager = LinearLayoutManager(this)
        mBinding.recycleView.adapter = RecyclerNestedAdapter(data)
    }
}