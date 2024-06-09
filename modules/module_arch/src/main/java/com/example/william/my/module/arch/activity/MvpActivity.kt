package com.example.william.my.module.arch.activity

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.lib.activity.BaseFragmentActivity
import com.example.william.my.module.arch.fragment.MvpFragment

/**
 * MVP：Model-View-Presenter
 */
@Route(path = RouterPath.Arch.MVP)
class MvpActivity : BaseFragmentActivity() {

    override fun setFragment(): Fragment {
        return MvpFragment()
    }
}