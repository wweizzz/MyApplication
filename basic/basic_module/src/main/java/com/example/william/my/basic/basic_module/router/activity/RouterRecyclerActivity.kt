package com.example.william.my.basic.basic_module.router.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.william.my.basic.basic_module.router.fragment.RouterRecyclerFragment
import com.example.william.my.basic.basic_module.router.item.RouterItem
import com.example.william.my.lib.activity.BaseFragmentActivity

abstract class RouterRecyclerActivity : BaseFragmentActivity() {

    override fun setFragment(): Fragment {
        val bundle = Bundle()
        bundle.putParcelableArrayList("router", buildRouter())
        val fragment = RouterRecyclerFragment()
        fragment.arguments = bundle
        return fragment
    }

    protected open fun buildRouter(): ArrayList<RouterItem> {
        return arrayListOf()
    }
}