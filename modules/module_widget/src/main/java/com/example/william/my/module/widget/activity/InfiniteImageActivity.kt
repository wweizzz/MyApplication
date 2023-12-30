package com.example.william.my.module.widget.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.path.ARouterPath
import com.example.william.my.lib.activity.BaseVBActivity
import com.example.william.my.module.widget.databinding.DemoActivityInfiniteImageBinding

@Route(path = ARouterPath.Widget.InfiniteImage)
class InfiniteImageActivity : BaseVBActivity<DemoActivityInfiniteImageBinding>() {

    override fun getViewBinding(): DemoActivityInfiniteImageBinding {
        return DemoActivityInfiniteImageBinding.inflate(layoutInflater)
    }
}