package com.example.william.my.core.download.callback

import com.example.william.my.core.download.task.DownloadTask
import com.example.william.my.core.retrofit.exception.ApiException

interface DownloadCallback {
    /**
     * 下载进度
     *
     * @param downloadTask
     */
    fun onProgress(downloadTask: DownloadTask?)

    /**
     * 下载成功
     *
     * @param downloadTask
     */
    fun onSuccess(downloadTask: DownloadTask?)

    /**
     * 下载出错
     *
     * @param api
     */
    fun onFailure(api: ApiException?)
}