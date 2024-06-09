package com.example.william.my.module.widget.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener
import com.example.william.my.basic.basic_module.fragment.PrimaryDarkFragment
import com.example.william.my.basic.basic_module.fragment.PrimaryFragment
import com.example.william.my.core.widget.bottomsheet.ViewPagerBottomSheetDialogFragment
import com.example.william.my.module.widget.R
import com.example.william.my.module.widget.adapter.ViewPagerFragmentAdapter
import com.example.william.my.module.widget.databinding.DemoDialogBottomSheetBinding

class MyBottomSheetDialog : ViewPagerBottomSheetDialogFragment() {

    private lateinit var mBinding: DemoDialogBottomSheetBinding

    private val mFragments: ArrayList<Fragment> = arrayListOf(
        PrimaryFragment(),
        PrimaryDarkFragment(),
        PrimaryFragment(),
        PrimaryDarkFragment()
    )

    override fun getLayout(): Int {
        return R.layout.demo_dialog_bottom_sheet
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DemoDialogBottomSheetBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewPager()
    }

    private fun initViewPager() {
        mBinding.viewPager.offscreenPageLimit = mFragments.size
        mBinding.viewPager.adapter =
            ViewPagerFragmentAdapter(childFragmentManager, mFragments, false)
        mBinding.viewPager.addOnPageChangeListener(object : SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                onPageChange(mBinding.viewPager)
            }
        })
    }
}