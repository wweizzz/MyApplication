package com.example.william.my.core.okhttp.format

import okhttp3.MediaType
import okhttp3.Request
import okhttp3.Response

interface FormatPrinter {
    /**
     * 打印网络请求信息, 当网络请求时 {[okhttp3.RequestBody]} 可以解析的情况
     *
     * @param request
     * @param bodyString 发送给服务器的请求体中的数据(已解析)
     */
    fun printJsonRequest(request: Request, bodyString: String)

    /**
     * 打印网络请求信息, 当网络请求时 {[okhttp3.RequestBody]} 为 `null` 或不可解析的情况
     *
     * @param request
     */
    fun printFileRequest(request: Request)

    /**
     * 打印网络响应信息, 当网络响应时 {[okhttp3.ResponseBody]} 可以解析的情况
     */
    fun printJsonResponse(
        tookMs: Long,
        response: Response,
        mediaType: MediaType?,
        bodyString: String
    )

    /**
     * 打印网络响应信息, 当网络响应时 {[okhttp3.ResponseBody]} 为 `null` 或不可解析的情况
     */
    fun printFileResponse(tookMs: Long, response: Response)
}