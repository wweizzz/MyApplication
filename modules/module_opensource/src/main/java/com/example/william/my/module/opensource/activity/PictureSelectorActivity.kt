package com.example.william.my.module.opensource.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.lib.activity.BaseVBActivity
import com.example.william.my.module.opensource.databinding.OpenActivityPictureSelectorBinding

/**
 * https://github.com/LuckSiege/PictureSelector
 */
@Route(path = RouterPath.Opensource.PictureSelector)
class PictureSelectorActivity : BaseVBActivity<OpenActivityPictureSelectorBinding>() {

    override fun getViewBinding(): OpenActivityPictureSelectorBinding {
        return OpenActivityPictureSelectorBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
    }
}