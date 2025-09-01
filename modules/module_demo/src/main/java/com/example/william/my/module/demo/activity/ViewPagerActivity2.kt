package com.example.william.my.module.demo.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.william.my.basic.basic_module.fragment.PrimaryDarkFragment
import com.example.william.my.basic.basic_module.fragment.PrimaryFragment
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.lib.activity.BaseVBActivity
import com.example.william.my.module.demo.adapter.ViewPagerAdapter2
import com.example.william.my.module.demo.adapter.ViewPagerFragmentAdapter2
import com.example.william.my.module.demo.databinding.DemoActivityViewPager2Binding

/**
 * Pages must fill the whole ViewPager2 (use match_parent)
 * ViewPager2内的view布局必须是math_parent，否则会报错
 */
@Route(path = RouterPath.Demo.ViewPager2)
class ViewPagerActivity2 : BaseVBActivity<DemoActivityViewPager2Binding>() {

    override fun getViewBinding(): DemoActivityViewPager2Binding {
        return DemoActivityViewPager2Binding.inflate(layoutInflater)
    }

    private val mTitles: ArrayList<String> = arrayListOf(
        "primary1",
        "primary2",
        "primaryDark1",
        "primaryDark2"
    )

    private val mFragments: ArrayList<Fragment> = arrayListOf(
        PrimaryFragment(),
        PrimaryDarkFragment(),
        PrimaryFragment(),
        PrimaryDarkFragment(),
    )

    private val mARouterFragments: ArrayList<Fragment> = arrayListOf(
        ARouter.getInstance().build(
            RouterPath.Fragment.FragmentPrimary
        ).navigation() as Fragment,
        ARouter.getInstance().build(RouterPath.Fragment.FragmentPrimaryDark)
            .navigation() as Fragment,
        ARouter.getInstance().build(RouterPath.Fragment.FragmentPrimaryDark)
            .navigation() as Fragment
    )

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        initViewPager2()
    }

    private fun initViewPager2() {
        mBinding.viewpager2View.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        mBinding.viewpager2View.adapter =
            ViewPagerAdapter2(mTitles)

        mBinding.viewpager2Fragment.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        mBinding.viewpager2Fragment.adapter =
            ViewPagerFragmentAdapter2(
                supportFragmentManager, lifecycle, mFragments
            )
    }
}