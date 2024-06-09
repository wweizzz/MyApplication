package com.example.william.my.module.arch.activity.mavericks

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.lib.activity.BaseFragmentActivity
import com.example.william.my.module.arch.mavericks.article.ArticleMavericksFragment

/**
 * Mavericks
 * https://airbnb.io/mavericks/
 */
@Route(path = RouterPath.Arch.Mavericks)
class MavericksActivity : BaseFragmentActivity() {

    override fun setFragment(): Fragment {
        return ArticleMavericksFragment()
    }
}