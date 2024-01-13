package com.example.william.my.module.demo.utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle

object FragmentUtils {

    fun initFragment(
        savedInstanceState: Bundle?,
        fragmentManager: FragmentManager,
        layoutId: Int, fragments: ArrayList<Fragment>, titles: ArrayList<String>,
    ) {
        val transaction = fragmentManager.beginTransaction()

        if (savedInstanceState == null) {
            for (fragment in fragmentManager.fragments) {
                transaction.remove(fragment).commit()
            }
            for (i in fragments.indices) {
                transaction.add(layoutId, fragments[i], titles[i])
                if (i == 0) {
                    transaction.setMaxLifecycle(fragments[i], Lifecycle.State.RESUMED)
                } else {
                    transaction.setMaxLifecycle(fragments[i], Lifecycle.State.STARTED)
                }
            }
            transaction.commit()
        } else {
            for (i in fragments.indices) {
                fragments[i] = fragmentManager.findFragmentByTag(titles[i])!!
            }
        }
    }

    fun switchFragment(
        fragmentManager: FragmentManager, fragments: ArrayList<Fragment>, position: Int
    ) {
        val transaction = fragmentManager.beginTransaction()

        transaction.show(fragments[position])
        transaction.setMaxLifecycle(fragments[position], Lifecycle.State.RESUMED)

        for (fragment in fragments) {
            if (fragment !== fragments[position]) {
                transaction.hide(fragment)
                transaction.setMaxLifecycle(fragment, Lifecycle.State.STARTED)
            }
        }
        transaction.commitAllowingStateLoss()
    }
}