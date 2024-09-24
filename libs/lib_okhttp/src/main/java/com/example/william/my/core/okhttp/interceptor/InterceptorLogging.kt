package com.example.william.my.core.okhttp.interceptor

import com.example.william.my.core.okhttp.format.FormatPrinterImpl
import com.example.william.my.core.okhttp.format.ParseUtils
import com.example.william.my.core.okhttp.format.ParseUtils.isParseAble
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit

class InterceptorLogging(filters: List<String>) : Interceptor {

    private val mPrinter = FormatPrinterImpl

    init {
        mPrinter.setFilters(filters)
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()

        if (request.body?.contentType().isParseAble()) {
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

        if (response.body?.contentType().isParseAble()) {
            mPrinter.printJsonResponse(
                tookMs,
                response,
                response.body?.contentType(),
                ParseUtils.parseResponse(response)
            )
        } else {
            mPrinter.printFileResponse(tookMs, response)
        }

        return response
    }
}