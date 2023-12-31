package com.example.william.my.module.widget.activity

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.core.widget.spinner.Spinner

@Route(path = RouterPath.Widget.Spinner)
class SpinnerActivity : BasicResponseActivity() {

    private var mSpinner: Spinner? = null
    private val mData = arrayOf("第一条数据", "第二条数据", "第三条数据", "第四条数据")

    public override fun onResponseClick(view: View) {
        super.onResponseClick(view)

        showSpinner()
    }

    private fun showSpinner() {
        mSpinner = Spinner(this@SpinnerActivity, listOf(*mData))
        mSpinner?.width = mBinding.basicsResponse.width
        mSpinner?.showAsDropDown(mBinding.basicsResponse)
        mSpinner?.setItemListener { position ->
            mBinding.basicsResponse.text = mData[position]
        }
    }
}