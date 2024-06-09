package com.example.william.my.module.arch.activity.mavericks

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.lib.activity.BaseFragmentActivity
import com.example.william.my.module.arch.mavericks.counter.CounterFragment

/**
 * Mavericks
 * https://airbnb.io/mavericks/
 */
@Route(path = RouterPath.Arch.Counter)
class CounterActivity : BaseFragmentActivity() {

    override fun setFragment(): Fragment {
        return CounterFragment()
    }
}