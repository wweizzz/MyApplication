package com.example.william.my.module.demo.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerFragmentAdapter2(
    fm: FragmentManager,
    lifecycle: Lifecycle,
    private val mFragments: List<Fragment>?
) :
    FragmentStateAdapter(
        fm, lifecycle
    ) {

    override fun getItemCount(): Int {
        return mFragments?.size ?: 0
    }

    override fun createFragment(position: Int): Fragment {
        return mFragments!![position]
    }
}