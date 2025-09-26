package com.example.william.my.module.opensource.activity5

import android.os.Bundle
import android.util.TypedValue
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.lib.activity.BaseVBActivity
import com.example.william.my.module.opensource.R
import com.example.william.my.module.opensource.databinding.OpenActivityBlurViewBinding

/**
 * IOS UIVisualEffectView.
 * https://github.com/mmin18/RealtimeBlurView
 */
@Route(path = RouterPath.Opensource.BlurView)
class BlurViewActivity : BaseVBActivity<OpenActivityBlurViewBinding>() {

    override fun getViewBinding(): OpenActivityBlurViewBinding {
        return OpenActivityBlurViewBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.open_activity_blur_view)

        mBinding.blurView.setBlurRadius(
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                10f,
                getResources().displayMetrics
            )
        )
    }
}
