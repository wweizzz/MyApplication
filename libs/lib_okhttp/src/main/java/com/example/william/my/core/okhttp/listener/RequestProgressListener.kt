package com.example.william.my.core.okhttp.listener

/**
 * 上传进度接口
 */
interface RequestProgressListener {
    /**
     * 上传进度
     *
     * @param currentSize
     * @param totalSize
     */
    fun onProgress(currentSize: Long, totalSize: Long)
}