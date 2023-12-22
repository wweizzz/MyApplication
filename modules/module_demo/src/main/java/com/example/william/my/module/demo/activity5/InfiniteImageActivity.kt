package com.example.william.my.module.demo.activity5

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.path.ARouterPath
import com.example.william.my.lib.activity.BaseVBActivity
import com.example.william.my.module.demo.databinding.DemoActivityInfiniteImageBinding

@Route(path = ARouterPath.Demo.InfiniteImage)
class InfiniteImageActivity : BaseVBActivity<DemoActivityInfiniteImageBinding>() {

    override fun getViewBinding(): DemoActivityInfiniteImageBinding {
        return DemoActivityInfiniteImageBinding.inflate(layoutInflater)
    }
}