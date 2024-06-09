package com.example.william.my.module.opensource.activity

import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.fragment.PrimaryDarkFragment
import com.example.william.my.basic.basic_module.fragment.PrimaryFragment
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.lib.activity.BaseVBActivity
import com.example.william.my.module.opensource.databinding.OpenActivityFlycoTabLayoutBinding
import com.example.william.my.module.opensource.utils.TabLayoutUtils

/**
 * https://github.com/H07000223/FlycoTabLayout
 */
@Route(path = RouterPath.Opensource.FlycoTabLayout)
class FlycoTabLayoutActivity : BaseVBActivity<OpenActivityFlycoTabLayoutBinding>() {

    override fun getViewBinding(): OpenActivityFlycoTabLayoutBinding {
        return OpenActivityFlycoTabLayoutBinding.inflate(layoutInflater)
    }

    private var mTitles: ArrayList<String> = arrayListOf()

    private val mFragments: ArrayList<Fragment> = arrayListOf(
        PrimaryFragment(),
        PrimaryDarkFragment(),
        PrimaryFragment(),
        PrimaryDarkFragment(),
    )

    override fun initView() {
        super.initView()

        intTitles()
        initTabLayout()
    }

    private fun intTitles() {
        mTitles = arrayListOf("primary1", "primaryDark1", "primary2", "primaryDark2")
    }

    private fun initTabLayout() {
        TabLayoutUtils.initSlidingTab(
            mBinding.slidingTab, mBinding.viewPager,
            mTitles, this, mFragments
        )
        TabLayoutUtils.initCommonTabLayout(
            mBinding.commonTab, mBinding.viewPager,
            mTitles
        )

        TabLayoutUtils.initSegmentTabLayout(
            mBinding.segmentTab, mBinding.viewPager,
            mTitles
        )

        mBinding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                mBinding.commonTab.currentTab = position
                mBinding.segmentTab.currentTab = position
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }
}