package com.example.william.my.module.network.activity2

import com.alibaba.android.arouter.facade.annotation.Route
import com.android.volley.VolleyError
import com.example.william.my.basic.basic_module.activity.BasicRecyclerActivity
import com.example.william.my.basic.basic_module.base.Constants
import com.example.william.my.basic.basic_module.router.path.ARouterPath
import com.example.william.my.basic.basic_repo.bean.LoginBean
import com.example.william.my.core.volley.VolleyHelper
import com.example.william.my.core.volley.VolleyListener

/**
 * https://github.com/google/volley
 */
@Route(path = ARouterPath.Network.VolleyHelper)
class VolleyHelperActivity : BasicRecyclerActivity() {

    override fun buildList(): ArrayList<String> {
        return arrayListOf(
            "VolleyHelper Post",
        )
    }

    override fun onRecyclerClick(position: Int, string: String) {
        super.onRecyclerClick(position, string)
        when (position) {
            0 -> {
                post()
            }
        }
    }

    private fun post() {
        VolleyHelper
            .builder<LoginBean>()
            .url(Constants.Url_Login)
            .clazz(LoginBean::class.java)
            .addParam(Constants.Key_Username, Constants.Value_Username)
            .addParam(Constants.Key_Password, Constants.Value_Password)
            .post()
            .build()
            .enqueue(this, object : VolleyListener<LoginBean>() {
                override fun onResponse(response: LoginBean?) {
                    val netSuccess = "onResponse: ${response?.string()}"
                    showMessage(netSuccess)
                }

                override fun onErrorResponse(error: VolleyError?) {
                    val netError = "onFailure: " + error?.message
                    showMessage(netError)
                }
            })
    }
}