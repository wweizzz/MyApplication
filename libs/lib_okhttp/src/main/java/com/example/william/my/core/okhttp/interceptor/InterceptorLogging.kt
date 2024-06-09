package com.example.william.my.core.okhttp.interceptor

import com.example.william.my.core.okhttp.format.FormatPrinterImpl
import com.example.william.my.core.okhttp.format.ParseUtils
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit

class InterceptorLogging : Interceptor {

    private val mPrinter = FormatPrinterImpl

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()

        val requestBody = request.body
        if (requestBody != null && ParseUtils.isParseAble(requestBody.contentType())) {
            mPrinter.printJsonRequest(request, ParseUtils.parseRequest(request))
        } else {
            mPrinter.printFileRequest(request)
        }

        val startNs = System.nanoTime()
        val response: Response
        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
            throw e
        }
        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)

        val responseBody = response.body!!
        if (ParseUtils.isParseAble(responseBody.contentType())) {
            mPrinter.printJsonResponse(
                tookMs,
                response,
                responseBody.contentType(),
                ParseUtils.parseResponse(response)
            )
        } else {
            mPrinter.printFileResponse(tookMs, response)
        }

        return response
    }
}