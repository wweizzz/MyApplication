package com.example.william.my.module.demo.activity

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.fragment.PrimaryDarkFragment
import com.example.william.my.basic.basic_module.fragment.PrimaryFragment
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.lib.activity.BaseVBActivity
import com.example.william.my.module.demo.adapter.ViewPagerFragmentAdapter
import com.example.william.my.module.demo.databinding.DemoActivityAppBarBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

/**
 * layout_scrollFlags (滚动标志)
 * scroll - scroll首先滑动的是列表，列表的数据全部滚动完毕，才开始toolbar滑动。
 * enterAlways - enterAlways首先滑动的是toolbar，然后再去滑动其他的view。
 * enterAlwaysCollapsed - 向下滚动事件时，子View先向下滚动最小高度值，然后Scrolling View开始滚动，到达边界时，子View再向下滚动，直至显示完全。
 * exitUntilCollapsed - 向上滚动事件时，子View向上滚动直至最小高度，然后Scrolling View开始滚动。也就是，子View不会完全退出屏幕。
 *
 * layout_collapseMode (折叠模式)
 * pin - 设置为这个模式时，当CollapsingToolbarLayout完全收缩后，Toolbar还可以保留在屏幕上。
 * parallax - 设置为这个模式时，在内容滚动时，CollapsingToolbarLayout中的View（比如ImageView)也可以同时滚动，实现视差滚动效果，通常和layout_collapseParallaxMultiplier(设置视差因子)搭配使用。
 */
@Route(path = RouterPath.Demo.Appbar)
class AppBarActivity : BaseVBActivity<DemoActivityAppBarBinding>() {

    override fun getViewBinding(): DemoActivityAppBarBinding {
        return DemoActivityAppBarBinding.inflate(layoutInflater)
    }

    private val mTitles: ArrayList<String> = arrayListOf(
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

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        initAppBar()
    }

    private fun initAppBar() {
        setSupportActionBar(mBinding.toolbar)
        mBinding.toolbarLayout.title = title

        mBinding.viewPager.adapter =
            ViewPagerFragmentAdapter(supportFragmentManager, mFragments, mTitles, true)

        //设置TabLayout可滚动，保证Tab数量过多时也可正常显示
        mBinding.tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        //设置TabLayout选中Tab下划线颜色
        mBinding.tabLayout.setSelectedTabIndicatorColor(
            ContextCompat.getColor(
                this,
                com.example.william.my.basic.basic_module.R.color.colorPrimaryDark
            )
        )
        //两个参数分别对应Tab未选中的文字颜色和选中的文字颜色
        mBinding.tabLayout.setTabTextColors(
            ContextCompat.getColor(
                this,
                com.example.william.my.basic.basic_module.R.color.colorPrimary
            ),
            ContextCompat.getColor(
                this,
                com.example.william.my.basic.basic_module.R.color.colorPrimaryDark
            )
        )
        //绑定ViewPager
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager)

        mBinding.tabLayout.tabMode = TabLayout.MODE_FIXED
        mBinding.tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        //设置TabLayout的选择监听
        mBinding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                mBinding.viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            /*
             * 重复点击Tab时回调
             */
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }
}