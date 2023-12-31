package com.example.william.my.module.utils.activity

import android.content.res.Resources
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.AdaptScreenUtils
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.lib.activity.BaseVBActivity
import com.example.william.my.module.utils.databinding.UtilsActivityAdaptscreenBinding

@Route(path = RouterPath.Utils.AdaptScreenUtils)
class AdaptScreenUtilsActivity : BaseVBActivity<UtilsActivityAdaptscreenBinding>() {
    override fun getViewBinding(): UtilsActivityAdaptscreenBinding {
        return UtilsActivityAdaptscreenBinding.inflate(layoutInflater)
    }

    override fun getResources(): Resources {
        return AdaptScreenUtils.adaptWidth(super.getResources(), 1080)
    }
}