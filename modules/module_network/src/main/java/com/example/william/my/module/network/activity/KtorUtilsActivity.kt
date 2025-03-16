package com.example.william.my.module.network.activity

import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_data.bean.LoginData
import com.example.william.my.basic.basic_module.activity.BasicRecyclerActivity
import com.example.william.my.basic.basic_module.base.Constants
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.core.ktor.KtorUtils
import kotlinx.coroutines.launch

/**
 * https://ktor.io/
 */
@Route(path = RouterPath.Network.KtorUtils)
class KtorUtilsActivity : BasicRecyclerActivity() {

    override fun buildList(): ArrayList<String> {
        return arrayListOf(
            "ktor post",
        )
    }

    override fun onRecyclerClick(position: Int, string: String) {
        super.onRecyclerClick(position, string)
        when (position) {
            0 -> {
                ktorPost(Constants.Value_Username, Constants.Value_Password)
            }
        }
    }

    private fun ktorPost(username: String, password: String) {
        val params = mutableMapOf<String, String>()
        params[Constants.Key_Username] = username
        params[Constants.Key_Password] = password

        lifecycleScope.launch {
            KtorUtils.post<LoginData>(Constants.Url_Login, params).collect { result ->
                when {
                    result.isSuccess -> {
                        val data = result.getOrNull()
                        // 更新UI或处理数据
                        showResponse(data?.string() ?: "")
                    }

                    result.isFailure -> {
                        val error = result.exceptionOrNull()
                        // 处理错误（如提示用户）
                        showFailure(error?.message ?: "Unknown error")
                    }
                }
            }
        }
    }
}