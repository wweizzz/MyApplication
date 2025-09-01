package com.example.william.my.lib.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.william.my.lib.R

abstract class BaseFragmentActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.base_activity_fragment)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        initFragment()
    }

    private fun initFragment() {
        //创建FragmentManager对象
        val manager = supportFragmentManager
        //创建FragmentTransaction事务对象
        val fragmentTransaction = manager.beginTransaction()
        //使用replace（将要替换位置的i的，替换的页面）方法实现页面的替换
        val fragment = setFragment()
        fragmentTransaction.replace(R.id.fragment, fragment)
        //提交事务
        fragmentTransaction.commitAllowingStateLoss()
    }

    protected abstract fun setFragment(): Fragment
}