package com.example.william.my.module.demo.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Lifecycle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.fragment.PrimaryDarkFragment
import com.example.william.my.basic.basic_module.fragment.PrimaryFragment
import com.example.william.my.basic.basic_module.router.path.ARouterPath
import com.example.william.my.library.activity.BaseVBActivity
import com.example.william.my.module.demo.R
import com.example.william.my.module.demo.databinding.DemoActivityFragment1Binding

@Route(path = ARouterPath.Demo.Fragment1)
class FragmentActivity1 : BaseVBActivity<DemoActivityFragment1Binding>(),
    View.OnClickListener {

    override fun getViewBinding(): DemoActivityFragment1Binding {
        return DemoActivityFragment1Binding.inflate(layoutInflater)
    }

    private var mTransaction: FragmentTransaction? = null

    private val mTabs: ArrayList<TextView> = arrayListOf()

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

    private val isNewWay = false // 是否使用新的适配方式：setMaxLifecycle

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        initFragment(savedInstanceState)
        initFragmentTab()
        switchFragment(0)
        switchFragmentTab(0)
    }

    private fun initFragment(savedInstanceState: Bundle?) {
        mTransaction = supportFragmentManager.beginTransaction()
        if (savedInstanceState != null) {
            for (i in mFragments.indices) {
                mFragments[i] = supportFragmentManager.findFragmentByTag(mTitles[i])!!
            }
        } else {
            removeAllFragments()
            for (i in mFragments.indices) {
                mTransaction?.add(R.id.frameLayout, mFragments[i], mTitles[i])
                if (isNewWay) {
                    if (i == 0) {
                        mTransaction?.setMaxLifecycle(mFragments[i], Lifecycle.State.RESUMED)
                    } else {
                        mTransaction?.setMaxLifecycle(mFragments[i], Lifecycle.State.STARTED)
                    }
                }
            }
            mTransaction?.commit()
        }
    }

    private fun removeAllFragments() {
        for (fragment in supportFragmentManager.fragments) {
            mTransaction?.remove(fragment)?.commit()
        }
    }

    private fun initFragmentTab() {
        for (i in 0 until mBinding.navigate.childCount) {
            val textView: TextView = mBinding.navigate.getChildAt(i) as TextView
            textView.text = mTitles[i]
            textView.setOnClickListener(this)
            textView.setTextColor(
                ContextCompat.getColorStateList(
                    this,
                    R.color.demo_selector_select_primary_dark
                )
            )
            mTabs.add(textView)
        }
    }


    override fun onClick(v: View) {
        for (i in 0 until mBinding.navigate.childCount) {
            if (mBinding.navigate.getChildAt(i).id == v.id) {
                switchFragment(i)
                switchFragmentTab(i)
            }
        }
    }

    private fun switchFragment(position: Int) {
        mTransaction = supportFragmentManager.beginTransaction()
        mTransaction?.show(mFragments[position])

        if (isNewWay) {
            mTransaction?.setMaxLifecycle(mFragments[position], Lifecycle.State.RESUMED)
        }

        for (fragment in mFragments) {
            if (fragment !== mFragments[position]) {
                mTransaction?.hide(fragment)
                if (isNewWay) {
                    mTransaction?.setMaxLifecycle(fragment, Lifecycle.State.STARTED)
                }
            }
        }
        mTransaction?.commitAllowingStateLoss()
    }

    private fun switchFragmentTab(position: Int) {
        for (i in 0 until mTabs.size) {
            mTabs[i].isSelected = i == position
        }
    }
}