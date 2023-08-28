package com.example.william.my.module.opensource.activity

import androidx.core.content.ContextCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.R
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity
import com.example.william.my.basic.basic_module.router.path.ARouterPath
import com.example.william.my.library.utils.Utils
import q.rorbin.badgeview.QBadgeView

/**
 * https://github.com/qstumn/BadgeView
 */
@Route(path = ARouterPath.Opensource.BadgeView)
class BadgeViewActivity : BasicResponseActivity() {
    override fun initView() {
        super.initView()

        initQBadgeView()
    }

    private fun initQBadgeView() {
        QBadgeView(this)
            .bindTarget(mBinding.basicsResponse)
            .setBadgeNumber(5)
            .setBadgeBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
            .setGravityOffset(48f, 48f, true)
            .setOnDragStateChangedListener { _, _, _ ->
                Utils.show("已读")
            }
    }
}