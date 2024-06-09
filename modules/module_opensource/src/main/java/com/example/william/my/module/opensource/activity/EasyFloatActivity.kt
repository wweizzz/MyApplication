package com.example.william.my.module.opensource.activity

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.module.opensource.R
import com.lzf.easyfloat.EasyFloat

/**
 * https://github.com/princekin-f/EasyFloat
 */
@Route(path = RouterPath.Opensource.EasyFloat)
class EasyFloatActivity : BasicResponseActivity() {

    override fun onResponseClick(view: View) {
        super.onResponseClick(view)
        showEasyFloat()
    }

    private fun showEasyFloat() {
        EasyFloat.with(this)
            .setLayout(R.layout.open_layout_float)
            .show()
    }
}