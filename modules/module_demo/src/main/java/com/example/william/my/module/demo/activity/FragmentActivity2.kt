package com.example.william.my.module.demo.activity

import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.fragment.PrimaryDarkFragment
import com.example.william.my.basic.basic_module.fragment.PrimaryFragment
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.lib.activity.BaseVBActivity
import com.example.william.my.module.demo.R
import com.example.william.my.module.demo.databinding.DemoActivityFragment2Binding
import com.example.william.my.module.demo.utils.FragmentUtils

@Route(path = RouterPath.Demo.Fragment2)
class FragmentActivity2 : BaseVBActivity<DemoActivityFragment2Binding>(),
    RadioGroup.OnCheckedChangeListener {

    override fun getViewBinding(): DemoActivityFragment2Binding {
        return DemoActivityFragment2Binding.inflate(layoutInflater)
    }


    private val mTitles: ArrayList<String> = arrayListOf(
        "primary1",
        "primaryDark1",
        "primary2",
        "primaryDark2",
    )

    private val mTabs: ArrayList<RadioButton> = arrayListOf()

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
        mBinding.navigate.setOnCheckedChangeListener(this)
        for (i in 0 until mBinding.navigate.childCount) {
            val radioButton: RadioButton = mBinding.navigate.getChildAt(i) as RadioButton
            radioButton.text = mTitles[i]
            radioButton.setTextColor(
                ContextCompat.getColorStateList(
                    this,
                    R.color.demo_selector_check_primary_dark
                )
            )
            mTabs.add(radioButton)
        }
    }

    private fun initFragment(savedInstanceState: Bundle?) {
        FragmentUtils.initFragment(
            savedInstanceState, supportFragmentManager,
            R.id.frameLayout, mFragments, mTitles
        )
    }


    override fun onCheckedChanged(group: RadioGroup, checkedId: Int) {
        for (i in 0 until mBinding.navigate.childCount) {
            if (mBinding.navigate.getChildAt(i).id == checkedId) {
                switchTab(i)
                switchFragment(i)
            }
        }
    }

    private fun switchTab(position: Int) {
        //for (i in 0 until mTabs.size) {
        //    mTabs[i].isSelected = i == position
        //}
        mBinding.navigate.check(mTabs[position].id)
    }

    private fun switchFragment(position: Int) {
        FragmentUtils.switchFragment(supportFragmentManager, mFragments, position)
    }
}