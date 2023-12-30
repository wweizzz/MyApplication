package com.example.william.my.module.network.activity

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.android.volley.VolleyError
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity
import com.example.william.my.basic.basic_module.base.Constants
import com.example.william.my.basic.basic_module.router.path.ARouterPath
import com.example.william.my.basic.basic_repo.bean.LoginBean
import com.example.william.my.core.volley.VolleyHelper
import com.example.william.my.core.volley.VolleyListener

/**
 * https://github.com/google/volley
 */
@Route(path = ARouterPath.Network.VolleyHelper)
class VolleyHelperActivity : BasicResponseActivity() {

    override fun onResponseClick(view: View) {
        super.onResponseClick(view)

        request()
    }

    private fun request() {
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
                    showResponse(netSuccess)
                }

                override fun onErrorResponse(error: VolleyError?) {
                    val netError = "onFailure: " + error?.message
                    showResponse(netError)
                }
            })
    }
}