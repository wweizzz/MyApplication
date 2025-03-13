package com.example.william.my.module.network.activity2

import com.alibaba.android.arouter.facade.annotation.Route
import com.android.volley.VolleyError
import com.example.william.my.basic.basic_data.bean.LoginData
import com.example.william.my.basic.basic_module.activity.BasicRecyclerActivity
import com.example.william.my.basic.basic_module.base.Constants
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.core.volley.VolleyHelper
import com.example.william.my.core.volley.listener.VolleyListener
import org.json.JSONObject

/**
 * https://github.com/google/volley
 */
@Route(path = RouterPath.Network.VolleyHelper)
class VolleyHelperActivity : BasicRecyclerActivity() {

    override fun buildList(): ArrayList<String> {
        return arrayListOf(
            "VolleyHelper postForm",
            "VolleyHelper postJson",
        )
    }

    override fun onRecyclerClick(position: Int, string: String) {
        super.onRecyclerClick(position, string)
        when (position) {
            0 -> {
                postForm(Constants.Value_Username, Constants.Value_Password)
            }

            1 -> {
                postJson(Constants.Value_Username, Constants.Value_Password)
            }
        }
    }

    private fun postForm(username: String, password: String) {
        val params = mutableMapOf(
            Constants.Key_Username to username,
            Constants.Key_Password to password
        )

        VolleyHelper
            .builder<LoginData>()
            .url(Constants.Url_Login)
            .clazz(LoginData::class.java)
            .addParams(params)
            .post()
            .build()
            .enqueue(this, object : VolleyListener<LoginData>() {
                override fun onResponse(response: LoginData?) {
                    showResponse(response?.string())
                }

                override fun onErrorResponse(error: VolleyError?) {
                    showFailure(error?.message)
                }
            })
    }

    private fun postJson(username: String, password: String) {
        val jsonObject = JSONObject()
            .put(Constants.Key_Username, username)
            .put(Constants.Key_Password, password)

        VolleyHelper
            .builder<LoginData>()
            .url(Constants.Url_Login)
            .clazz(LoginData::class.java)
            .addJsonObject(jsonObject)
            .post()
            .build()
            .enqueue(this, object : VolleyListener<LoginData>() {
                override fun onResponse(response: LoginData?) {
                    showResponse(response?.string())
                }

                override fun onErrorResponse(error: VolleyError?) {
                    showFailure(error?.message)
                }
            })
    }
}