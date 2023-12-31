package com.example.william.my.module.widget.activity2

import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.fragment.PrimaryDarkFragment
import com.example.william.my.basic.basic_module.fragment.PrimaryFragment
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.lib.activity.BaseVBActivity
import com.example.william.my.module.widget.R
import com.example.william.my.module.widget.adapter.ViewPagerFragmentAdapter

import com.example.william.my.module.widget.databinding.DemoActivityFragmentViewPagerBinding

@Route(path = RouterPath.Widget.FragmentViewPager)
class FragmentViewPagerActivity : BaseVBActivity<DemoActivityFragmentViewPagerBinding>(),
    RadioGroup.OnCheckedChangeListener {

    override fun getViewBinding(): DemoActivityFragmentViewPagerBinding {
        return DemoActivityFragmentViewPagerBinding.inflate(layoutInflater)
    }

    private val mTitle: ArrayList<String> = arrayListOf(
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

    override fun initView() {
        super.initView()

        initFragment()
        initFragmentTab()
        switchFragmentTab(0)
    }

    private fun initFragment() {
        mBinding.viewPager.offscreenPageLimit = 4
        mBinding.viewPager.adapter =
            ViewPagerFragmentAdapter(supportFragmentManager, mFragments, true)
    }

    private fun initFragmentTab() {
        mBinding.navigate.setOnCheckedChangeListener(this)

        for (i in 0 until mBinding.navigate.childCount) {
            val radioButton: RadioButton = mBinding.navigate.getChildAt(i) as RadioButton
            radioButton.text = mTitle[i]
            radioButton.setTextColor(
                ContextCompat.getColorStateList(
                    this,
                    R.color.demo_selector_check_primary_dark
                )
            )
            mTabs.add(radioButton)
        }
    }

    override fun onCheckedChanged(group: RadioGroup, checkedId: Int) {
        for (i in 0 until mBinding.navigate.childCount) {
            if (mBinding.navigate.getChildAt(i).id == checkedId) {
                switchFragment(i)
                switchFragmentTab(i)
            }
        }
    }

    private fun switchFragment(position: Int) {
        mBinding.viewPager.currentItem = position
    }

    private fun switchFragmentTab(position: Int) {
        //for (i in 0 until mTabs.size) {
        //    mTabs[i].isSelected = i == position
        //}
        mBinding.navigate.check(mTabs[position].id)
    }
}