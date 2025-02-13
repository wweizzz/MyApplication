package com.example.william.my.module.network.activity

import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicRecyclerActivity
import com.example.william.my.basic.basic_module.base.Constants
import com.example.william.my.basic.basic_module.router.path.RouterPath
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.Parameters
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.http.parameters
import io.ktor.serialization.gson.gson
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.launch
import java.io.File

/**
 * https://ktor.io/
 */
@Route(path = RouterPath.Network.Ktor)
class KtorActivity : BasicRecyclerActivity() {

    override fun buildList(): ArrayList<String> {
        return arrayListOf(
            "ktor Posting a parameter",
            "ktor Posting a multipart request",
            "ktor Posting a string",
            "ktor Posting a json"
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

            2 -> {
                ktorPostString()
            }

            3 -> {
                ktorPostJson()
            }
        }
    }

    private val ktorClient = HttpClient(Android) {
        install(ContentNegotiation) {
            gson()
            //json()
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 15000
        }

        install(HttpRequestRetry) {
            maxRetries = 5
            retryIf { request, response ->
                !response.status.isSuccess()
            }
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
    }

    private fun ktorPostParam() {
        lifecycleScope.launch {
            val response: HttpResponse = ktorClient.post(Constants.Url_Login) {
                url(Constants.Url_Login)
                //headers {
                //    append("Authorization", "BearerToken")
                //    append("Custom-Header", "value")
                //}
                formData {
                    append(Constants.Key_Username, Constants.Value_Username)
                    append(Constants.Key_Password, Constants.Value_Password)
                }
            }
            showResponse(response.bodyAsText())
        }
    }

    private fun ktorPostMultipart() {
        lifecycleScope.launch {
            val response: HttpResponse = ktorClient.post(Constants.Url_Login) {
                url(Constants.Url_Login)
                //headers {
                //    append("Authorization", "BearerToken")
                //    append("Custom-Header", "value")
                //}
                setBody(MultiPartFormDataContent(
                    formData {
                        append(Constants.Key_Username, Constants.Value_Username)
                        append(Constants.Key_Password, Constants.Value_Password)
                    }
                ))

            }
            showResponse(response.bodyAsText())
        }
    }

    private fun ktorPostString() {
        lifecycleScope.launch {
            val response: HttpResponse = ktorClient.post(Constants.Url_Login) {
                url(Constants.Url_Login)
                //headers {
                //    append("Authorization", "BearerToken")
                //    append("Custom-Header", "value")
                //}
                contentType(ContentType.Application.FormUrlEncoded)
                setBody(Constants.LoginString)
            }
            showResponse(response.bodyAsText())
        }
    }

    private fun ktorPostJson() {
        lifecycleScope.launch {
            val response: HttpResponse = ktorClient.post(Constants.Url_Login) {
                url(Constants.Url_Login)
                //headers {
                //    append("Authorization", "BearerToken")
                //    append("Custom-Header", "value")
                //}
                contentType(ContentType.Application.Json)
                setBody(Constants.LoginJsonString)
            }
            showResponse(response.bodyAsText())
        }
    }

    private fun ktorPostFile() {
        lifecycleScope.launch {
            val response: HttpResponse = ktorClient.post(Constants.Url_Login) {
                url(Constants.Url_Login)

                setBody(MultiPartFormDataContent(formData {
                    append("file", File("").readBytes(), Headers.build {
                        append(HttpHeaders.ContentType, "image/*")
                    })
                }))
            }
            showResponse(response.body())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ktorClient.close()
    }
}