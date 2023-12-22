package com.example.william.my.module.arch.activity

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.path.ARouterPath
import com.example.william.my.lib.activity.BaseFragmentActivity
import com.example.william.my.module.arch.fragment.MviFragment

/**
 * MVI：Model-View-Intent
 */
@Route(path = ARouterPath.Arch.MVI)
class MviActivity : BaseFragmentActivity() {

    override fun setFragment(): Fragment {
        return MviFragment()
    }
}