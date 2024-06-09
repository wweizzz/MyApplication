package com.example.william.my.module.network.activity2

import com.alibaba.android.arouter.facade.annotation.Route
import com.android.volley.VolleyError
import com.example.william.my.basic.basic_data.bean.Login
import com.example.william.my.basic.basic_module.activity.BasicRecyclerActivity
import com.example.william.my.basic.basic_module.base.Constants
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.core.volley.VolleyHelper
import com.example.william.my.core.volley.VolleyListener

/**
 * https://github.com/google/volley
 */
@Route(path = RouterPath.Network.VolleyHelper)
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
            .builder<Login>()
            .url(Constants.Url_Login)
            .clazz(Login::class.java)
            .addParam(Constants.Key_Username, Constants.Value_Username)
            .addParam(Constants.Key_Password, Constants.Value_Password)
            .post()
            .build()
            .enqueue(this, object : VolleyListener<Login>() {
                override fun onResponse(response: Login?) {
                    showResponse(response?.string())
                }

                override fun onErrorResponse(error: VolleyError?) {
                    showFailure(error?.message)
                }
            })
    }
}