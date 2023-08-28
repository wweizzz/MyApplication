package com.example.william.my.core.okhttp.body

import com.example.william.my.core.okhttp.listener.RequestProgressListener
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.Buffer
import okio.BufferedSink
import okio.ForwardingSink
import okio.Sink
import okio.buffer

/**
 * 上传进度
 */
class RequestProgressBody(
    private val mRequestBody: RequestBody,
    private val mRequestProgressListener: RequestProgressListener?
) : RequestBody() {

    override fun contentType(): MediaType? {
        return mRequestBody.contentType()
    }

    override fun contentLength(): Long {
        return mRequestBody.contentLength()
    }

    override fun writeTo(sink: BufferedSink) {
        val bufferedSink = sink(sink).buffer()
        mRequestBody.writeTo(bufferedSink)
        //必须调用flush，否则最后一部分数据可能不会被写入
        bufferedSink.flush()
    }

    private fun sink(sink: Sink): Sink {

        return object : ForwardingSink(sink) {
            //当前写入字节数
            var bytesWritten = 0L

            //总字节长度，避免多次调用contentLength()方法
            var totalBytesCount = 0L

            override fun write(source: Buffer, byteCount: Long) {
                super.write(source, byteCount)
                //获得contentLength的值，后续不再调用
                if (totalBytesCount == 0L) {
                    totalBytesCount = contentLength()
                }
                //增加当前写入的字节数
                bytesWritten += byteCount

                mRequestProgressListener?.onProgress(bytesWritten, totalBytesCount)
            }
        }
    }
}