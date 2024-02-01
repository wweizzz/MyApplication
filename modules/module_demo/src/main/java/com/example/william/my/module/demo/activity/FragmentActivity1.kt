package com.example.william.my.module.demo.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.fragment.PrimaryDarkFragment
import com.example.william.my.basic.basic_module.fragment.PrimaryFragment
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.lib.activity.BaseVBActivity
import com.example.william.my.module.demo.R
import com.example.william.my.module.demo.databinding.DemoActivityFragment1Binding
import com.example.william.my.module.demo.utils.FragmentUtils

@Route(path = RouterPath.Demo.Fragment1)
class FragmentActivity1 : BaseVBActivity<DemoActivityFragment1Binding>(),
    View.OnClickListener {

    override fun getViewBinding(): DemoActivityFragment1Binding {
        return DemoActivityFragment1Binding.inflate(layoutInflater)
    }

    private val mTitles: ArrayList<String> = arrayListOf(
        "primary1",
        "primaryDark1",
        "primary2",
        "primaryDark2",
    )

    private val mTabs: ArrayList<TextView> = arrayListOf()

    private val mFragments: ArrayList<Fragment> = arrayListOf(
        PrimaryFragment(),
        PrimaryDarkFragment(),
        PrimaryFragment(),
        PrimaryDarkFragment(),
    )

    private val isNewWay = false // 是否使用新的适配方式：setMaxLifecycle

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        initTab()
        initFragment(savedInstanceState)
        switchTab(0)
        switchFragment(0)
    }

    private fun initTab() {
        for (i in 0 until mBinding.navigate.childCount) {
            val textView: TextView = mBinding.navigate.getChildAt(i) as TextView
            textView.text = mTitles[i]
            textView.setOnClickListener(this)
            textView.setTextColor(
                ContextCompat.getColorStateList(
                    this,
                    R.color.demo_selector_select_primary_dark
                )
            )
            mTabs.add(textView)
        }
    }

    private fun initFragment(savedInstanceState: Bundle?) {
        FragmentUtils.initFragment(
            savedInstanceState, supportFragmentManager,
            R.id.frameLayout, mFragments, mTitles
        )
    }

    override fun onClick(v: View) {
        for (i in 0 until mBinding.navigate.childCount) {
            if (mBinding.navigate.getChildAt(i).id == v.id) {
                switchTab(i)
                switchFragment(i)
            }
        }
    }

    private fun switchTab(position: Int) {
        for (i in 0 until mTabs.size) {
            mTabs[i].isSelected = i == position
        }
    }

    private fun switchFragment(position: Int) {
        FragmentUtils.switchFragment(supportFragmentManager, mFragments, position)
    }
}