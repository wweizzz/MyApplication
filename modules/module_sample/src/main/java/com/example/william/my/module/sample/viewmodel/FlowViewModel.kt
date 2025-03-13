package com.example.william.my.module.sample.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.william.my.basic.basic_data.bean.LoginData
import com.example.william.my.core.retrofit.response.RetrofitResponse
import com.example.william.my.module.sample.data.NetworkResult
import com.example.william.my.module.sample.repo.FlowRepository
import com.example.william.my.module.sample.utils.ThreadUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

/**
 * Android 上的 Kotlin 数据流
 * https://developer.android.google.cn/kotlin/flow
 * <p>
 * flow -> StateFlow
 */
class FlowViewModel(private val repository: FlowRepository) : ViewModel() {

    // Backing property to avoid state updates from other classes
    private val _uiState: MutableStateFlow<NetworkResult<RetrofitResponse<LoginData?>>> =
        MutableStateFlow(NetworkResult.Loading)

    // The UI collects from this StateFlow to get its state updates
    val uiState: StateFlow<NetworkResult<RetrofitResponse<LoginData?>>> =
        _uiState

    /**
     * 3. 从数据流中进行收集
     * 4. 数据流捕获异常
     */
    fun login(username: String, password: String) {
        // 在UI线程上创建一个新的协同程序
        // Create a new coroutine on the UI thread
        viewModelScope.launch {
            //打印线程
            ThreadUtils.isMainThread("FlowViewModel login")

            val flow: Flow<RetrofitResponse<LoginData?>> =
                repository.login(username, password)

            // 使用 collect 触发流并消耗其元素
            // Trigger the flow and consume its elements using collect
            flow
                .onStart {
                    // 在调用 flow 请求数据之前，做一些准备工作，例如显示正在加载数据的进度条
                    _uiState.value = NetworkResult.Loading
                }
                .catch { exception ->
                    // 捕获上游出现的异常
                    _uiState.value = NetworkResult.Error(Exception(exception))
                }
                .onCompletion {
                    // 请求完成
                }
                .collect { response ->
                    // 更新视图
                    // Update View with the latest favorite news
                    _uiState.value = NetworkResult.Success(response)
                }
        }
    }
}

object FlowVMFactory : ViewModelProvider.Factory {

    private val repository = FlowRepository(Dispatchers.IO)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return FlowViewModel(repository) as T
    }
}
