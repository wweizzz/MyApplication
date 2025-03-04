package com.example.william.my.module.network.activity

import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicRecyclerActivity
import com.example.william.my.basic.basic_module.base.Constants
import com.example.william.my.basic.basic_module.router.path.RouterPath
import kotlinx.coroutines.launch

/**
 * https://ktor.io/
 */
@Route(path = RouterPath.Network.KtorUtils)
class KtorUtilsActivity : BasicRecyclerActivity() {

    override fun buildList(): ArrayList<String> {
        return arrayListOf(
            "ktor Posting a param",
            "ktor Posting a multipart",
        )
    }

    override fun onRecyclerClick(position: Int, string: String) {
        super.onRecyclerClick(position, string)
        when (position) {
            0 -> {
                ktorPostParam(Constants.Value_Username, Constants.Value_Password)
            }

            1 -> {
                ktorPostMultipart(Constants.Value_Username, Constants.Value_Password)
            }
        }
    }

    private fun ktorPostParam(username: String, password: String) {
        val params = mutableMapOf<String, String>()
        params[Constants.Key_Username] = username
        params[Constants.Key_Password] = password

        lifecycleScope.launch {

        }
    }

    private fun ktorPostMultipart(username: String, password: String) {
        val params = mutableMapOf<String, String>()
        params[Constants.Key_Username] = username
        params[Constants.Key_Password] = password

        lifecycleScope.launch {

        }
    }
}