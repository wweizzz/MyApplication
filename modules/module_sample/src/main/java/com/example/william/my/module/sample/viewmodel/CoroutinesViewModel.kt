package com.example.william.my.module.sample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.william.my.basic.basic_data.bean.Login
import com.example.william.my.core.retrofit.response.RetrofitResponse
import com.example.william.my.module.sample.data.NetworkResult
import com.example.william.my.module.sample.repo.CoroutinesRepository
import com.example.william.my.module.sample.utils.ThreadUtils
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Android 上的 Kotlin 协程
 * https://developer.android.google.cn/kotlin/coroutines
 * <p>
 * result -> LiveData
 */
class CoroutinesViewModel(private val repository: CoroutinesRepository) : ViewModel() {

    private val _login = MutableLiveData<String>()

    val login: LiveData<String>
        get() = _login

    /**
     * 3. 处理异常
     */
    fun login(username: String, password: String) {

        // 在UI线程上创建一个新的协同程序
        // Create a new coroutine on the UI thread
        viewModelScope.launch {
            //打印线程
            ThreadUtils.isMainThread("CoroutinesViewModel login")

            // 执行网络请求 并 挂起，直至请求完成
            // Make the network call and suspend execution until it finishes
            val result: NetworkResult<RetrofitResponse<Login?>> =
                try {
                    repository.login(username, password)
                } catch (e: Exception) {
                    NetworkResult.Error(Exception("Network request failed"))
                }

            // 向用户展示网络请求结果
            // Display result of the network request to the user
            when (result) {
                is NetworkResult.Success<RetrofitResponse<Login?>> -> {
                    _login.postValue("onResponse: " + Gson().toJson(result.data))
                }

                is NetworkResult.Error -> {
                    _login.postValue("onFailure: " + result.exception.message)
                }

                is NetworkResult.Loading -> {
                    _login.postValue("onLoading: " + "加载中……")
                }
            }
        }
    }
}

object CoroutinesVMFactory : ViewModelProvider.Factory {

    private val repository = CoroutinesRepository(Dispatchers.IO)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return CoroutinesViewModel(repository) as T
    }
}