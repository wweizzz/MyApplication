package com.example.william.my.module.widget.activity

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.lib.activity.BaseVBActivity
import com.example.william.my.module.widget.R
import com.example.william.my.module.widget.databinding.DemoActivityMarqueeViewBinding

@Route(path = RouterPath.Widget.MarqueeView)
class MarqueeViewActivity : BaseVBActivity<DemoActivityMarqueeViewBinding>() {

    override fun getViewBinding(): DemoActivityMarqueeViewBinding {
        return DemoActivityMarqueeViewBinding.inflate(layoutInflater)
    }

    private val mData = arrayListOf("第一条数据", "第二条数据", "第三条数据", "第四条数据")
    private val marqueeViews = mutableListOf<View>()

    override fun initView() {
        super.initView()

        initMarqueeView()
    }

    private fun initMarqueeView() {
        var i = 0
        while (i < mData.size) {
            //设置滚动的单个布局
            val viewGroup = layoutInflater.inflate(
                R.layout.demo_item_marquee_view,
                window.decorView as ViewGroup,
                false
            ) as LinearLayout
            //初始化布局的控件
            val textView1 = viewGroup.findViewById<TextView>(R.id.item_marquee_primary)
            val textView2 = viewGroup.findViewById<TextView>(R.id.item_marquee_accent)
            //进行对控件赋值
            textView1.text = mData[i]
            if (mData.size > i + 1) {
                //因为淘宝那儿是两条数据，但是当数据是奇数时就不需要赋值第二个，所以加了一个判断，还应该把第二个布局给隐藏掉
                textView2.text = mData[i + 1]
            } else {
                textView2.visibility = View.GONE
            }
            viewGroup.gravity = Gravity.CENTER
            //添加到循环滚动数组里面去
            marqueeViews.add(viewGroup)
            i += 2
        }
        mBinding.marqueeView.setViews(marqueeViews)
    }
}