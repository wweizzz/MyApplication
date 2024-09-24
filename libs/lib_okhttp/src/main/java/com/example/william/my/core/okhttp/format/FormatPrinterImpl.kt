package com.example.william.my.core.okhttp.format

import android.text.TextUtils
import android.util.Log
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.Response

/**
 * 作者　: zzyu
 * 时间　: 2020/3/26
 * 描述　:
 *
 * @Modified by Jim: 增加每行最多字符数(换行)定义：LINE_CHAR_COUNT, from 110 changed to 180
 */
object FormatPrinterImpl : FormatPrinter {

    private const val TAG = "HttpLog"

    private const val LINE_CHAR_COUNT = 180

    private const val REQUEST_UP_LINE =
        "┌────── Request ────────────────────────────────────────────────────────────────────────"
    private const val END_LINE =
        "└───────────────────────────────────────────────────────────────────────────────────────"
    private const val RESPONSE_UP_LINE =
        "┌────── Response ───────────────────────────────────────────────────────────────────────"

    private const val LINE_SEPARATOR = "\n"
    private const val DOUBLE_SEPARATOR = "\n\n"

    private val OMITTED_REQUEST = arrayOf(LINE_SEPARATOR, "Omitted request body")
    private val OMITTED_RESPONSE = arrayOf(LINE_SEPARATOR, "Omitted response body")

    private const val URL_TAG = "URL:"
    private const val METHOD_TAG = "Method: "
    private const val CONTENT_TYPE_TAG = "Content-Type"
    private const val CONTENT_LENGTH_TAG = "Content-Length"
    private const val HEADERS_TAG = "Headers:"
    private const val STATUS_CODE_TAG = "Status Code: "
    private const val BODY_TAG = "Body:"
    private const val CORNER_UP = "┌ "
    private const val CORNER_BOTTOM = "└ "
    private const val CENTER_LINE = "├ "
    private const val DEFAULT_LINE = "│ "

    private val ARMS = arrayOf("-A-", "-R-", "-M-", "-S-")

    private var mFilters: List<String> = arrayListOf()

    private val last: ThreadLocal<Int> = object : ThreadLocal<Int>() {
        override fun initialValue(): Int {
            return 0
        }
    }

    private fun isEmpty(line: String): Boolean {
        return TextUtils.isEmpty(line) || "\n" == line || "\t" == line || TextUtils.isEmpty(line.trim { it <= ' ' })
    }

    /**
     * 设置过滤
     */
    fun setFilters(filters: List<String>) {
        this.mFilters = filters
    }

    /**
     * 打印网络请求信息, 当网络请求时 {[okhttp3.RequestBody]} 可以解析的情况
     *
     * @param request    Request
     * @param bodyString String
     */
    override fun printJsonRequest(request: Request, bodyString: String) {
        if (shouldPrint(getUrl(request))) {
            val tag = "$TAG-Request"
            Log.d(tag, REQUEST_UP_LINE)
            logLines(tag, getUrl(request), false)
            logLines(tag, getHeaders(request), true)
            logLines(tag, getRequestBody(bodyString), true)
            Log.d(tag, END_LINE)
        }
    }

    /**
     * 打印网络请求信息, 当网络请求时 {[okhttp3.RequestBody]} 不可解析的情况
     *
     * @param request Request
     */
    override fun printFileRequest(request: Request) {
        val tag = "$TAG-Request"
        Log.d(tag, REQUEST_UP_LINE)
        logLines(tag, getUrl(request), false)
        logLines(tag, getHeaders(request), true)
        logLines(tag, OMITTED_REQUEST, true)
        Log.d(tag, END_LINE)
    }

    /**
     * 打印网络响应信息, 当网络响应时 {[okhttp3.ResponseBody]} 可以解析的情况
     */
    override fun printJsonResponse(
        tookMs: Long,
        response: Response,
        mediaType: MediaType?,
        bodyString: String
    ) {
        if (shouldPrint(getUrl(response))) {
            val tag = "$TAG-Response"
            Log.d(tag, RESPONSE_UP_LINE)
            logLines(tag, getUrl(response), false)
            logLines(tag, getHeaders(response, tookMs), true)
            logLines(tag, getResponseBody(mediaType, bodyString), true)
            Log.d(tag, END_LINE)
        }
    }

    /**
     * 打印网络响应信息, 当网络响应时 {[okhttp3.ResponseBody]} 不可解析的情况
     */
    override fun printFileResponse(tookMs: Long, response: Response) {
        val tag = "$TAG-Response"
        Log.d(tag, RESPONSE_UP_LINE)
        logLines(tag, getUrl(response), false)
        logLines(tag, getHeaders(response, tookMs), true)
        logLines(tag, OMITTED_RESPONSE, true)
        Log.d(tag, END_LINE)
    }

    private fun shouldPrint(url: Array<String>): Boolean {
        for (filter in mFilters) {
            if (url[0].endsWith(filter)) {
                return false
            }
        }
        return true
    }

    /**
     * 对 `lines` 中的信息进行逐行打印
     *
     * @param tag          String
     * @param lines        String[]
     * @param withLineSize 为 `true` 时, 每行的信息长度不会超过110, 超过则自动换行
     */
    private fun logLines(tag: String, lines: Array<String>, withLineSize: Boolean) {
        for (line in lines) {
            val lineLength = line.length
            val maxLongSize = if (withLineSize) LINE_CHAR_COUNT else lineLength
            for (i in 0..lineLength / maxLongSize) {
                val start = i * maxLongSize
                var end = (i + 1) * maxLongSize
                end = end.coerceAtMost(line.length)
                Log.d(resolveTag(tag), DEFAULT_LINE + line.substring(start, end))
            }
        }
    }


    /**
     * 此方法是为了解决在 AndroidStudio v3.1 以上 Logcat 输出的日志无法对齐的问题
     *
     *
     * 此问题引起的原因, 据 JessYan 猜测, 可能是因为 AndroidStudio v3.1 以上将极短时间内以相同 tag 输出多次的 log 自动合并为一次输出
     * 导致本来对称的输出日志, 出现不对称的问题
     * AndroidStudio v3.1 此次对输出日志的优化, 不小心使市面上所有具有日志格式化输出功能的日志框架无法正常工作
     * 现在暂时能想到的解决方案有两个: 1. 改变每行的 tag (每行 tag 都加一个可变化的 token) 2. 延迟每行日志打印的间隔时间
     *
     *
     * [.resolveTag] 使用第一种解决方案
     *
     * @param tag
     */
    private fun resolveTag(tag: String): String {
        return computeKey() + tag
    }

    private fun computeKey(): String {
        if (last.get()!! >= ARMS.size) {
            last.set(0)
        }
        val s = ARMS[last.get()!!]
        last.set(last.get()!! + 1)
        return s
    }

    private fun getUrl(request: Request): Array<String> {
        return arrayOf(URL_TAG + request.url)
    }

    private fun getHeaders(request: Request): Array<String> {
        val log: String
        val headers = request.headers

        val headerString = StringBuilder()
        for (i in 0 until headers.size) {
            headerString.append(headers.name(i) + ": " + headers.value(i)).append("\n")
        }

        log = METHOD_TAG + request.method + DOUBLE_SEPARATOR +
                if (isEmpty(headerString.toString())) "" else HEADERS_TAG + LINE_SEPARATOR + dotHeaders(
                    headerString.toString()
                )
        return log.split(LINE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    }

    private fun getRequestBody(bodyString: String): Array<String> {
        val log = LINE_SEPARATOR + BODY_TAG + LINE_SEPARATOR + bodyString
        return log.split(LINE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    }

    private fun getUrl(response: Response): Array<String> {
        return arrayOf(URL_TAG + response.request.url)
    }

    private fun getHeaders(response: Response, tookMs: Long): Array<String> {
        val log: String
        val headers = response.headers

        val headerString = StringBuilder()
        for (i in 0 until headers.size) {
            headerString.append(headers.name(i) + ": " + headers.value(i)).append("\n")
        }

        log =
            STATUS_CODE_TAG + "${response.code}" + "${if (response.message.isEmpty()) "" else ' ' + response.message} (${tookMs}ms)" + DOUBLE_SEPARATOR +
                    if (isEmpty(headerString.toString())) "" else HEADERS_TAG + LINE_SEPARATOR + dotHeaders(
                        headerString.toString()
                    )
        return log.split(LINE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    }

    private fun getResponseBody(mediaType: MediaType?, bodyString: String): Array<String> {
        val log = LINE_SEPARATOR + BODY_TAG + LINE_SEPARATOR + (
                if (ParseUtils.isJson(mediaType)) {
                    ParseUtils.jsonFormat(bodyString)
                } else if (ParseUtils.isXml(mediaType)) {
                    ParseUtils.xmlFormat(bodyString)
                } else {
                    bodyString
                })
        return log.split(LINE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    }

    /**
     * 对 `header` 按规定的格式进行处理
     *
     * @param header String
     * @return String
     */
    private fun dotHeaders(header: String): String {
        val headers = header.split(LINE_SEPARATOR.toRegex()).dropLastWhile {
            it.isEmpty()
        }.toTypedArray()
        val builder = StringBuilder()
        var tag = "─ "
        if (headers.size > 1) {
            for (i in headers.indices) {
                tag = when (i) {
                    0 -> {
                        CORNER_UP
                    }

                    headers.size - 1 -> {
                        CORNER_BOTTOM
                    }

                    else -> {
                        CENTER_LINE
                    }
                }
                builder.append(tag).append(headers[i]).append("\n")
            }
        } else {
            for (item in headers) {
                builder.append(tag).append(item).append("\n")
            }
        }
        return builder.toString()
    }
}