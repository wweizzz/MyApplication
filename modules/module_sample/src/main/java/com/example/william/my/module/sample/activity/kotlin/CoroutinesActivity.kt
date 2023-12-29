package com.example.william.my.module.sample.activity.kotlin

import android.view.View
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity
import com.example.william.my.basic.basic_module.base.Constants
import com.example.william.my.basic.basic_module.router.path.ARouterPath
import com.example.william.my.module.sample.viewmodel.CoroutinesVMFactory
import com.example.william.my.module.sample.viewmodel.CoroutinesViewModel

/**
 * Android 上的 Kotlin 协程
 * https://developer.android.google.cn/kotlin/coroutines
 */
@Route(path = ARouterPath.Sample.Coroutines)
class CoroutinesActivity : BasicResponseActivity() {

    private val mViewModel: CoroutinesViewModel by viewModels {
        CoroutinesVMFactory
    }

    override fun observeViewModel() {
        super.observeViewModel()

        mViewModel.login.observe(this) {
            showResponse(it)
        }
    }

    override fun onResponseClick(view: View) {
        super.onResponseClick(view)

        mViewModel.login(Constants.Value_Username, Constants.Value_Password)
    }
}