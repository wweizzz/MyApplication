package com.example.william.my.core.okhttp.body

import com.example.william.my.core.okhttp.listener.ResponseProgressListener
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.Buffer
import okio.BufferedSource
import okio.ForwardingSource
import okio.Source
import okio.buffer

/**
 * 下载进度
 */
class ResponseProgressBody(
    private val mUrl: String,
    private val mResponseBody: ResponseBody,
    private val mResponseProgressListener: ResponseProgressListener
) : ResponseBody() {

    override fun contentType(): MediaType? {
        return mResponseBody.contentType()
    }

    override fun contentLength(): Long {
        return mResponseBody.contentLength()
    }

    override fun source(): BufferedSource {
        return source(mResponseBody.source()).buffer()
    }

    private fun source(source: Source): Source {
        return ProgressSource(source)
    }

    private inner class ProgressSource(delegate: Source) : ForwardingSource(delegate) {

        //当前读取字节数
        var bytesRead = 0L

        //总字节长度，避免多次调用contentLength()方法
        var totalBytesCount = 0L

        override fun read(sink: Buffer, byteCount: Long): Long {
            val count = super.read(sink, byteCount)
            //获得contentLength的值，后续不再调用
            if (totalBytesCount == 0L) {
                totalBytesCount = contentLength()
            }
            if (count == -1L) { // this source is exhausted
                bytesRead = totalBytesCount
            } else {
                bytesRead += count
            }

            mResponseProgressListener.onProgress(mUrl, bytesRead, totalBytesCount)
            return count
        }
    }
}