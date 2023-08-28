package com.example.william.my.module.opensource.activity

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.fragment.PrimaryDarkFragment
import com.example.william.my.basic.basic_module.fragment.PrimaryFragment
import com.example.william.my.basic.basic_module.router.path.ARouterPath
import com.example.william.my.library.activity.BaseVBActivity
import com.example.william.my.module.opensource.databinding.OpenActivityFlycoTabLayoutBinding
import com.flyco.tablayout.listener.OnTabSelectListener

/**
 * https://github.com/H07000223/FlycoTabLayout
 */
@Route(path = ARouterPath.Opensource.FlycoTabLayout)
class FlycoTabLayoutActivity : BaseVBActivity<OpenActivityFlycoTabLayoutBinding>(),
    OnTabSelectListener {

    override fun getViewBinding(): OpenActivityFlycoTabLayoutBinding {
        return OpenActivityFlycoTabLayoutBinding.inflate(layoutInflater)
    }

    private val mTitles: Array<String> = arrayOf(
        "primary1",
        "primaryDark1",
        "primary2",
        "primaryDark2",
    )

    private val mFragments: ArrayList<Fragment> = arrayListOf(
        PrimaryFragment(),
        PrimaryDarkFragment(),
        PrimaryFragment(),
        PrimaryDarkFragment(),
    )

    override fun initView() {
        super.initView()

        initTabLayout()
    }

    private fun initTabLayout() {
        mBinding.slidingTab.setViewPager(mBinding.slidingViewPager, mTitles, this, mFragments)
        mBinding.slidingTab.setOnTabSelectListener(this)
    }

    override fun onTabSelect(position: Int) {}
    override fun onTabReselect(position: Int) {}
}