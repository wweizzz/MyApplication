package com.example.william.my.module.arch.activity

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.path.ARouterPath
import com.example.william.my.lib.activity.BaseFragmentActivity
import com.example.william.my.module.arch.fragment.MvvmFragment

/**
 * MVVM：Model-View-ViewModel
 */
@Route(path = ARouterPath.Arch.MVVM)
class MvvmActivity : BaseFragmentActivity() {

    override fun setFragment(): Fragment {
        return MvvmFragment()
    }
}