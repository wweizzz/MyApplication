package com.example.william.my.module.arch.activity

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.path.ARouterPath
import com.example.william.my.library.activity.BaseFragmentActivity
import com.example.william.my.module.arch.mavericks.counter.CounterFragment

/**
 * Mavericks
 * https://airbnb.io/mavericks/
 */
@Route(path = ARouterPath.Arch.Counter)
class CounterActivity : BaseFragmentActivity() {

    override fun setFragment(): Fragment {
        return CounterFragment()
    }
}