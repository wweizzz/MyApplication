package com.example.william.my.lib.tab

import androidx.viewpager2.widget.ViewPager2
import com.example.william.my.lib.tab.RecyclerTabAdapter

object RecyclerTabLayoutUtils {

    fun RecyclerTabAdapter<Any>.setViewPager(viewPager: ViewPager2) {
        this.setOnItemClickListener { _, _, position ->
            viewPager.currentItem = position
        }
    }

    fun ViewPager2.setTabAdapter(adapter: RecyclerTabAdapter<Any>) {
        this.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                adapter.setSelectPosition(position)
            }
        })
    }
}
