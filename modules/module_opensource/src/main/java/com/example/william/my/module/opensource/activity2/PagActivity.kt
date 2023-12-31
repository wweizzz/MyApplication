package com.example.william.my.module.opensource.activity2

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.path.ARouterPath
import com.example.william.my.lib.activity.BaseVBActivity
import com.example.william.my.module.opensource.databinding.OpenActivityPagBinding

/**
 * https://github.com/Tencent/libpag
 */
@Route(path = ARouterPath.Opensource.Pag)
class PagActivity : BaseVBActivity<OpenActivityPagBinding>() {

    override fun getViewBinding(): OpenActivityPagBinding {
        return OpenActivityPagBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()

        initPagAnim()
    }

    private fun initPagAnim() {

    }
}