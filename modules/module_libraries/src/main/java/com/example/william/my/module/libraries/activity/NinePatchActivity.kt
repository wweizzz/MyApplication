package com.example.william.my.module.libraries.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.base.Constants
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.core.ninepatch.NinePatchHelper
import com.example.william.my.lib.activity.BaseVBActivity
import com.example.william.my.module.libraries.databinding.LibsActivityNinepatchBinding

/**
 * 拉伸区域(左上)：设置拉伸区域
 * 内容区域(右下)：识别内容区域
 * Show lock：当鼠标在图片区域的时候显示不可编辑区域
 * Show patches：在编辑区域显示图片拉伸的区域
 * Show bad patches：在编辑区域显示不好的图片拉伸的区域
 * Show content：在预览区域显示图片的内容区域
 */
@Route(path = RouterPath.Libraries.NinePatch)
class NinePatchActivity : BaseVBActivity<LibsActivityNinepatchBinding>() {

    override fun getViewBinding(): LibsActivityNinepatchBinding {
        return LibsActivityNinepatchBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        show()
    }

    private fun show() {
        //NinePatchChunk
        NinePatchHelper.ninePatchChunk(this, mBinding.text1, Constants.Url_NinePatch)
        //NinePatchBuilder
        NinePatchHelper.ninePatchBuilder(this, mBinding.text2, Constants.Url_NinePatch)
    }
}