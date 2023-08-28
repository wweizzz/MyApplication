package com.example.william.my.core.okhttp.listener

/**
 * 下载进度接口
 */
interface ResponseProgressListener {
    /**
     * 下载进度
     *
     * @param url
     * @param currentSize
     * @param totalSize
     */
    fun onProgress(url: String, currentSize: Long, totalSize: Long)
}