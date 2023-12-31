package com.example.william.my.module.widget.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.lib.activity.BaseVBActivity
import com.example.william.my.module.widget.databinding.DemoActivitySensor3dBinding

@Route(path = RouterPath.Widget.Sensor3DView)
class Sensor3DActivity : BaseVBActivity<DemoActivitySensor3dBinding>() {

    override fun getViewBinding(): DemoActivitySensor3dBinding {
        return DemoActivitySensor3dBinding.inflate(layoutInflater)
    }
}