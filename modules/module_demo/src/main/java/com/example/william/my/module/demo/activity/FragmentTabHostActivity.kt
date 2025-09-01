package com.example.william.my.module.demo.activity

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.fragment.PrimaryDarkFragment
import com.example.william.my.basic.basic_module.fragment.PrimaryFragment
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.lib.activity.BaseVBActivity
import com.example.william.my.lib.utils.Utils
import com.example.william.my.module.demo.R
import com.example.william.my.module.demo.databinding.DemoActivityFragmentTabHostBinding

@Suppress("deprecation")
@Route(path = RouterPath.Demo.FragmentTabHost)
class FragmentTabHostActivity : BaseVBActivity<DemoActivityFragmentTabHostBinding>() {

    override fun getViewBinding(): DemoActivityFragmentTabHostBinding {
        return DemoActivityFragmentTabHostBinding.inflate(layoutInflater)
    }

    //选修卡文字
    private val mTitles: ArrayList<String> = arrayListOf(
        "primary1",
        "primaryDark1",
        "primary2",
        "primaryDark2",
    )

    //Fragment界面数组
    private val mFragments: ArrayList<Class<*>> = arrayListOf(
        PrimaryFragment::class.java,
        PrimaryDarkFragment::class.java,
        PrimaryFragment::class.java,
        PrimaryDarkFragment::class.java
    )

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        initTabHost()
    }

    private fun initTabHost() {
        mBinding.tabhost.setup(this, supportFragmentManager, android.R.id.tabcontent)
        mBinding.tabhost.setOnTabChangedListener {
            //在这监听切换，可根据需求做一些特定的操作
            switchTabHost(it)
        }

        for (i in mFragments.indices) {
            val tabView = getTabView(mTitles, i)
            val tabSpec = mBinding.tabhost.newTabSpec(mTitles[i]).setIndicator(tabView)

            // 设置Tab按钮的Tab
            mBinding.tabhost.addTab(tabSpec, mFragments[i], null)
            // 设置Tab按钮的背景
            mBinding.tabhost.tabWidget.getChildAt(i).setBackgroundColor(Color.WHITE)
        }
    }

    private fun getTabView(textArray: ArrayList<String>, i: Int): View {
        val view = layoutInflater.inflate(R.layout.demo_item_tab_host, mBinding.tabs, false)
        val textView = view.findViewById<TextView>(R.id.item_tab_text)
        textView.text = textArray[i]
        textView.setTextColor(
            ContextCompat.getColorStateList(
                this,
                R.color.demo_selector_select_primary_dark
            )
        )
        return view
    }

    private fun switchTabHost(s: String) {
        Utils.show(s)
    }
}