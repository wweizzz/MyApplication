package com.example.william.my.module.opensource.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.path.ARouterPath
import com.example.william.my.library.activity.BaseVBActivity
import com.example.william.my.module.opensource.databinding.OpenActivityShadowLayoutBinding

/**
 * https://github.com/lihangleo2/ShadowLayout
 */
@Route(path = ARouterPath.Opensource.ShadowLayout)
class ShadowLayoutActivity : BaseVBActivity<OpenActivityShadowLayoutBinding>() {

    override fun getViewBinding(): OpenActivityShadowLayoutBinding {
        return OpenActivityShadowLayoutBinding.inflate(layoutInflater)
    }
}