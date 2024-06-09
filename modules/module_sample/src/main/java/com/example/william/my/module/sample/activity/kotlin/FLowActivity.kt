package com.example.william.my.module.sample.activity.kotlin

import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity
import com.example.william.my.basic.basic_module.base.Constants
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.module.sample.data.NetworkResult
import com.example.william.my.module.sample.viewmodel.FlowVMFactory
import com.example.william.my.module.sample.viewmodel.FlowViewModel
import kotlinx.coroutines.launch

/**
 * Android 上的 Kotlin 数据流
 * https://developer.android.google.cn/kotlin/flow
 */
@Route(path = RouterPath.Sample.Flow)
class FLowActivity : BasicResponseActivity() {

    private val mViewModel: FlowViewModel by viewModels {
        FlowVMFactory
    }

    override fun observeViewModel() {
        super.observeViewModel()

        // 在生命周期范围内启动协同程序
        // Start a coroutine in the lifecycle scope
        lifecycleScope.launch {
            // 每次生命周期处于已启动状态（或更高）时，在新的协同路由中启动该块，并在其 STOPPED 时取消该块。
            // repeatOnLifecycle launches the block in a new coroutine every time the
            // lifecycle is in the STARTED state (or above) and cancels it when it's STOPPED.
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // 触发流并开始监听值。
                // Note that this happens when lifecycle is STARTED and stops collecting when the lifecycle is STOPPED
                // Trigger the flow and start listening for values.
                // Note that this happens when lifecycle is STARTED and stops collecting when the lifecycle is STOPPED
                mViewModel.uiState.collect { uiState ->
                    // New value received
                    when (uiState) {
                        is NetworkResult.Loading -> {
                            showResponse(uiState.string())
                        }

                        is NetworkResult.Success -> {
                            showResponse(uiState.string())
                        }

                        is NetworkResult.Error -> {
                            showResponse(uiState.string())
                        }
                    }
                }
            }
        }
    }

    override fun onResponseClick(view: View) {
        super.onResponseClick(view)

        mViewModel.login(Constants.Value_Username, Constants.Value_Password)
    }
}