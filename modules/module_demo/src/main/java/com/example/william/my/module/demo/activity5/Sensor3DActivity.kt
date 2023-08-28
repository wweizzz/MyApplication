package com.example.william.my.module.demo.activity5

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.path.ARouterPath
import com.example.william.my.library.activity.BaseVBActivity
import com.example.william.my.module.demo.databinding.DemoActivitySensor3dBinding

@Route(path = ARouterPath.Demo.Sensor3DView)
class Sensor3DActivity : BaseVBActivity<DemoActivitySensor3dBinding>() {

    override fun getViewBinding(): DemoActivitySensor3dBinding {
        return DemoActivitySensor3dBinding.inflate(layoutInflater)
    }
}