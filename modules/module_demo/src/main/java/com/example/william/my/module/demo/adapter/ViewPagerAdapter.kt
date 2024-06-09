package com.example.william.my.module.demo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.example.william.my.module.demo.databinding.DemoPageViewPagerBinding

class ViewPagerAdapter(private val mData: List<String>?) : PagerAdapter() {

    override fun getCount(): Int {
        return mData?.size ?: 0
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding =
            DemoPageViewPagerBinding.inflate(
                LayoutInflater.from(container.context),
                container,
                true
            )
        mData?.let { data ->
            binding.response.text = data[position]
        }
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}