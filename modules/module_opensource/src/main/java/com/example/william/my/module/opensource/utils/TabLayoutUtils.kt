package com.example.william.my.module.opensource.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.william.my.module.opensource.entity.TabEntity
import com.flyco.tablayout.CommonTabLayout
import com.flyco.tablayout.SegmentTabLayout
import com.flyco.tablayout.SlidingTabLayout
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener

object TabLayoutUtils {

    fun initSlidingTab(
        tabLayout: SlidingTabLayout, viewPager: ViewPager,
        titles: ArrayList<String>, activity: FragmentActivity, fragments: ArrayList<Fragment>
    ) {
        tabLayout.setViewPager(
            viewPager, titles.toTypedArray(), activity, fragments
        )
        tabLayout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {

            }

            override fun onTabReselect(position: Int) {

            }
        })
    }

    fun initCommonTabLayout(
        tabLayout: CommonTabLayout, viewPager: ViewPager, titles: ArrayList<String>,
    ) {
        val tabs: ArrayList<CustomTabEntity> = arrayListOf()
        for (i in titles.indices) {
            tabs.add(TabEntity(titles[i]))
        }
        tabLayout.setTabData(tabs)
        tabLayout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                viewPager.currentItem = position
            }

            override fun onTabReselect(position: Int) {

            }
        })
    }

    fun initSegmentTabLayout(
        tabLayout: SegmentTabLayout, viewPager: ViewPager, titles: ArrayList<String>,
    ) {
        tabLayout.setTabData(titles.toTypedArray())
        tabLayout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                viewPager.currentItem = position
            }

            override fun onTabReselect(position: Int) {

            }
        })
    }

    fun initCommonTabLayout(
        tabLayout: CommonTabLayout, viewPager: ViewPager2, titles: ArrayList<String>,
    ) {
        val tabs: ArrayList<CustomTabEntity> = arrayListOf()
        for (i in titles.indices) {
            tabs.add(TabEntity(titles[i]))
        }
        tabLayout.setTabData(tabs)
        tabLayout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                viewPager.currentItem = position
            }

            override fun onTabReselect(position: Int) {

            }
        })
    }

    fun initSegmentTabLayout(
        tabLayout: SegmentTabLayout, viewPager: ViewPager2, titles: ArrayList<String>,
    ) {
        tabLayout.setTabData(titles.toTypedArray())
        tabLayout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                viewPager.currentItem = position
            }

            override fun onTabReselect(position: Int) {

            }
        })
    }
}