package com.example.william.my.module.network.activity2

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicRecyclerActivity
import com.example.william.my.basic.basic_module.base.Constants
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.core.retrofit.RxRetrofit
import com.example.william.my.core.retrofit.callback.RetrofitResponseCallback
import com.example.william.my.core.retrofit.exception.ApiException
import com.google.gson.JsonElement
import org.json.JSONObject

/**
 * https://square.github.io/retrofit
 * https://github.com/square/retrofit
 */
@Route(path = RouterPath.Network.RxRetrofit)
class RxRetrofitActivity : BasicRecyclerActivity() {

    override fun buildList(): ArrayList<String> {
        return arrayListOf(
            "RxRetrofit Post postForm",
            "RxRetrofit Post postJson",
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

        RxRetrofit.builder<JsonElement>()
            .api(Constants.Url_Login)
            .addParams(params)
            .post()
            .setProvider(this)
            .buildSingle()
            .subscribe(object : RetrofitResponseCallback<JsonElement>() {
                override fun onResponse(response: JsonElement?) {
                    super.onResponse(response)
                    showResponse(response?.toString())
                }

                override fun onFailure(e: ApiException) {
                    super.onFailure(e)
                    showFailure(e.message)
                }
            })
    }

    private fun postJson(username: String, password: String) {
        val jsonObject = JSONObject()
            .put(Constants.Key_Username, username)
            .put(Constants.Key_Password, password)

        RxRetrofit.builder<JsonElement>()
            .api(Constants.Url_Login)
            .addJsonObject(jsonObject)
            .post()
            .setProvider(this)
            .buildSingle()
            .subscribe(object : RetrofitResponseCallback<JsonElement>() {
                override fun onResponse(response: JsonElement?) {
                    super.onResponse(response)
                    showResponse(response?.toString())
                }

                override fun onFailure(e: ApiException) {
                    super.onFailure(e)
                    showFailure(e.message)
                }
            })
    }
}