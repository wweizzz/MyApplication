package com.example.william.my.module.network.nano

import android.app.Application
import android.content.res.AssetManager
import com.example.william.my.lib.utils.Utils
import fi.iki.elonen.NanoHTTPD
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class NanoServer(val context: Application) : NanoHTTPD(DEFAULT_SERVER_PORT) {

    override fun serve(session: IHTTPSession): Response {
        return parseRequest(session)
        //return newFixedLengthResponse("<html><body><h1>Hello server</h1></body></html>\n");
    }

    /**
     * 解析数据
     */
    private fun parseRequest(session: IHTTPSession): Response {
        val response: Response =
            when (session.method) {
                Method.GET -> {
                    parseGetRequest(session)
                }

                Method.POST -> {
                    parsePostRequest(session)
                }

                else -> {
                    responseForNotFound()
                }
            }
        return response
    }

    private fun parseGetRequest(session: IHTTPSession): Response {
        val uri = session.uri
        var filename = uri.substring(1)
        var isAscii = true
        if (uri == "/") {
            filename = "index.html"
        }
        if (uri == "/login") {
            return newFixedLengthResponse("登录成功")
        }
        val mimeType: String
        if (filename.contains(".html") || filename.contains(".htm")) {
            mimeType = "text/html"
        } else if (filename.contains(".js")) {
            mimeType = "text/javascript"
        } else if (filename.contains(".css")) {
            mimeType = "text/css"
        } else if (filename.contains(".gif")) {
            mimeType = "text/gif"
            isAscii = false
        } else if (filename.contains(".jpeg") || filename.contains(".jpg")) {
            mimeType = "text/jpeg"
            isAscii = false
        } else if (filename.contains(".png")) {
            mimeType = "image/png"
            isAscii = false
        } else if (filename.contains(".svg")) {
            mimeType = "image/svg+xml"
            isAscii = false
        } else {
            filename = "index.html"
            mimeType = "text/html"
        }
        return if (isAscii) {
            loadHtml(filename, mimeType)
        } else {
            loadOrder(filename, mimeType)
        }
    }

    private fun parsePostRequest(session: IHTTPSession): Response {
        val response: Response =
            session.parameters?.let { params ->
                when (session.uri) {
                    "/login" -> {
                        newFixedLengthResponse("登录成功")
                    }

                    "/upload" -> {
                        val body: Map<String, String> = HashMap()
                        try {
                            session.parseBody(body)
                        } catch (e: IOException) {
                            e.printStackTrace()
                        } catch (e: ResponseException) {
                            e.printStackTrace()
                        }
                        val head = session.headers
                        for ((key, value) in head) {
                            Utils.d(TAG, "$key $value")
                        }
                        for ((key, value) in params) {
                            Utils.d(TAG, "$key $value")
                        }
                        newFixedLengthResponse(
                            Response.Status.OK,
                            MIME_HTML,
                            "{\"message\":\"上传成功\",\"status\":0}"
                        )
                    }

                    "/download" -> newFixedLengthResponse(
                        Response.Status.OK,
                        MIME_HTML,
                        "{\"message\":\"开始下载\",\"status\":0}"
                    )

                    else -> responseForNotFound()
                }
            } ?: responseParamsNotFound()
        return response
    }

    /**
     * 加载HTML文件
     */
    private fun loadHtml(filename: String, mimeType: String): Response {
        val assetManager: AssetManager = context.assets
        val response = StringBuilder()
        val reader: BufferedReader
        val isr: InputStream
        var line: String?
        try {
            isr = assetManager.open(filename, AssetManager.ACCESS_BUFFER)
            reader = BufferedReader(InputStreamReader(isr))
            while (reader.readLine().also { line = it } != null) {
                response.append(line)
            }
            reader.close()
        } catch (e: IOException) {
            e.printStackTrace()
            return newFixedLengthResponse(Response.Status.OK, mimeType, "")
        }
        return newFixedLengthResponse(Response.Status.NOT_FOUND, mimeType, response.toString())
    }

    /**
     * 加载文本响应
     */
    private fun loadOrder(filename: String, mimeType: String): Response {
        val assetManager: AssetManager = context.assets
        val isr: InputStream
        return try {
            isr = assetManager.open(filename)
            newFixedLengthResponse(Response.Status.OK, mimeType, isr, isr.available().toLong())
        } catch (e: IOException) {
            e.printStackTrace()
            newFixedLengthResponse(Response.Status.NOT_FOUND, mimeType, "")
        }
    }

    /**
     * 页面不存在
     */
    private fun responseForNotFound(): Response {
        val status = Response.Status.NOT_FOUND
        return newFixedLengthResponse(status, MIME_HTML, "NOT_FOUND")
    }

    /**
     * 请求参数错误
     */
    private fun responseParamsNotFound(): Response {
        val status = Response.Status.BAD_REQUEST
        return newFixedLengthResponse(status, MIME_HTML, "请求参数错误")
    }

    companion object {

        private val TAG = NanoServer::class.java.simpleName

        const val DEFAULT_SERVER_PORT = 5567
    }
}