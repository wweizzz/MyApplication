package com.example.william.my.module.opensource.activity

import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.fragment.PrimaryDarkFragment
import com.example.william.my.basic.basic_module.fragment.PrimaryFragment
import com.example.william.my.basic.basic_module.router.path.ARouterPath
import com.example.william.my.library.activity.BaseVBActivity
import com.example.william.my.module.opensource.databinding.OpenActivityFlycoTabLayoutBinding
import com.example.william.my.module.opensource.entity.TabEntity
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener

/**
 * https://github.com/H07000223/FlycoTabLayout
 */
@Route(path = ARouterPath.Opensource.FlycoTabLayout)
class FlycoTabLayoutActivity : BaseVBActivity<OpenActivityFlycoTabLayoutBinding>() {

    override fun getViewBinding(): OpenActivityFlycoTabLayoutBinding {
        return OpenActivityFlycoTabLayoutBinding.inflate(layoutInflater)
    }

    private var mTitles: ArrayList<String> = arrayListOf()

    private var mTitleTabs: ArrayList<CustomTabEntity> = arrayListOf()

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
        for (i in mTitles.indices) {
            mTitleTabs.add(TabEntity(mTitles[i]))
        }
    }

    private fun initTabLayout() {
        mBinding.commonTab.setTabData(mTitleTabs)
        mBinding.commonTab.setOnTabSelectListener(
            object : OnTabSelectListener {
                override fun onTabSelect(position: Int) {
                    mBinding.viewPager.currentItem = position
                }

                override fun onTabReselect(position: Int) {

                }
            })

        mBinding.slidingTab.setViewPager(
            mBinding.viewPager, mTitles.toTypedArray(), this, mFragments
        )
        mBinding.slidingTab.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {

            }

            override fun onTabReselect(position: Int) {

            }
        })
        mBinding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                mBinding.commonTab.currentTab = position
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }
}