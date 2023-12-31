package com.example.william.my.module.demo.activity

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.william.my.basic.basic_module.fragment.PrimaryDarkFragment
import com.example.william.my.basic.basic_module.fragment.PrimaryFragment
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.lib.activity.BaseVBActivity
import com.example.william.my.module.demo.adapter.ViewPagerAdapter
import com.example.william.my.module.demo.adapter.ViewPagerFragmentAdapter
import com.example.william.my.module.demo.databinding.DemoActivityViewPagerBinding

@Route(path = RouterPath.Demo.ViewPager)
class ViewPagerActivity : BaseVBActivity<DemoActivityViewPagerBinding>() {

    override fun getViewBinding(): DemoActivityViewPagerBinding {
        return DemoActivityViewPagerBinding.inflate(layoutInflater)
    }

    private val mTitles: ArrayList<String> = arrayListOf(
        "primary1",
        "primaryDark1",
        "primary2",
        "primaryDark2"
    )

    private val mFragments: ArrayList<Fragment> = arrayListOf(
        PrimaryFragment(),
        PrimaryDarkFragment(),
        PrimaryFragment(),
        PrimaryDarkFragment()
    )

    private val mARouterFragments = arrayOf(
        ARouter.getInstance().build(RouterPath.Fragment.FragmentPrimary)
            .navigation() as Fragment,
        ARouter.getInstance().build(RouterPath.Fragment.FragmentPrimaryDark)
            .navigation() as Fragment,
        ARouter.getInstance().build(RouterPath.Fragment.FragmentPrimaryDark)
            .navigation() as Fragment
    )

    override fun initView() {
        super.initView()

        initViewPager()
    }

    private fun initViewPager() {
        mBinding.viewpagerView.adapter = ViewPagerAdapter(mTitles)

        mBinding.viewpagerFragment.adapter = ViewPagerFragmentAdapter(
            supportFragmentManager, mFragments, false
        )
    }
}