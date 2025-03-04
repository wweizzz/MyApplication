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
            "RxRetrofit Post",
            "RxRetrofit Post String",
            "RxRetrofit Post JsonString",
            "RxRetrofit Post JsonObject",
        )
    }

    override fun onRecyclerClick(position: Int, string: String) {
        super.onRecyclerClick(position, string)
        when (position) {
            0 -> {
                post()
            }

            1 -> {
                postString()
            }

            2 -> {
                postJsonString()
            }

            3 -> {
                postJsonObject()
            }
        }
    }

    private fun post() {
        RxRetrofit
            .builder<JsonElement>()
            .api(Constants.Url_Login)
            .addParam(Constants.Key_Username, Constants.Value_Username)
            .addParam(Constants.Key_Password, Constants.Value_Password)
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
                    showMessage(e.message)
                }
            })
    }

    private fun postString() {
        RxRetrofit
            .builder<JsonElement>()
            .api(Constants.Url_Login)
            .addString(Constants.LoginString)
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
                    showMessage(e.message)
                }
            })
    }

    private fun postJsonString() {
        RxRetrofit
            .builder<JsonElement>()
            .api(Constants.Url_Login)
            .addJsonString(Constants.LoginJsonString)
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
                    showMessage(e.message)
                }
            })
    }

    private fun postJsonObject() {
        val jsonObject = JSONObject()
            .put(Constants.Key_Username, Constants.Value_Username)
            .put(Constants.Key_Password, Constants.Value_Password)

        RxRetrofit
            .builder<JsonElement>()
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
                    showMessage(e.message)
                }
            })
    }
}