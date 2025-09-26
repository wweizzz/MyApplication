package com.example.william.my.module.opensource.activity

import android.view.View
import androidx.core.content.ContextCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.R
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity
import com.example.william.my.basic.basic_module.databinding.BasicsLayoutResponseBinding
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.basic.basic_module.utils.Utils

/**
 * https://github.com/pinguo-zhouwei/CustomPopwindow
 */
@Route(path = RouterPath.Opensource.PopWindow)
class PopWindowActivity : BasicResponseActivity() {

    override fun onResponseClick(view: View) {
        super.onResponseClick(view)

        initPopWindow()
    }

    private fun initPopWindow() {

        val binding = BasicsLayoutResponseBinding.inflate(layoutInflater)

        //处理popWindow 显示内容
        handleLogic(binding)

        //创建并显示popWindow
        //CustomPopWindow.PopupWindowBuilder(this)
        //    .setView(binding.root)
        //    .size(
        //        resources.getDimensionPixelOffset(R.dimen.basics_dimen_width_320),
        //        resources.getDimensionPixelOffset(R.dimen.basics_dimen_height_200)
        //    ) //设置显示的大小，不设置就默认包裹内容
        //    .setFocusable(true) //是否获取焦点，默认为ture
        //    .setOutsideTouchable(true) //是否PopupWindow以外触摸dismiss
        //    .create() //创建PopupWindow
        //    .showAsDropDown(mBinding.basicsResponse, 0, 0) //显示PopupWindow
    }

    private fun handleLogic(binding: BasicsLayoutResponseBinding) {
        binding.basicsResponse.setBackgroundColor(
            ContextCompat.getColor(
                this,
                R.color.colorPrimary
            )
        )
        binding.basicsResponse.setOnClickListener {
            Utils.toast("您点击了按钮")
        }
    }
}