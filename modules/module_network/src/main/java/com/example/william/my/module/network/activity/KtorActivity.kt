package com.example.william.my.module.network.activity

import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicRecyclerActivity
import com.example.william.my.basic.basic_module.base.Constants
import com.example.william.my.basic.basic_module.router.path.RouterPath
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.parameters
import kotlinx.coroutines.launch

/**
 * https://ktor.io/
 */
@Route(path = RouterPath.Network.Ktor)
class KtorActivity : BasicRecyclerActivity() {

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
                ktorPostParam()
            }

            1 -> {
                ktorPostMultipart()
            }
        }
    }

    private val ktorClient = HttpClient(OkHttp) {
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.e("Ktor Logging : ", message)
                }
            }

            level = LogLevel.ALL
        }
    }

    private fun ktorPostParam() {
        lifecycleScope.launch {
            val response: HttpResponse = ktorClient.post(Constants.Url_Login) {
                url(Constants.Url_Login)
                setBody(
                    FormDataContent(
                        parameters {
                            append(Constants.Key_Username, Constants.Value_Username)
                            append(Constants.Key_Password, Constants.Value_Password)
                        })
                )
            }
            showResponse(response.bodyAsText())
        }
    }

    private fun ktorPostMultipart() {
        lifecycleScope.launch {
            val response: HttpResponse = ktorClient.post(Constants.Url_Login) {
                url(Constants.Url_Login)
                setBody(
                    MultiPartFormDataContent(
                        formData {
                            append(Constants.Key_Username, Constants.Value_Username)
                            append(Constants.Key_Password, Constants.Value_Password)
                        })
                )
            }
            showResponse(response.bodyAsText())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ktorClient.close()
    }
}