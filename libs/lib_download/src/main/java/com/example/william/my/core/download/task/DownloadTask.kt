package com.example.william.my.core.download.task

import com.example.william.my.core.download.callback.DownloadCallback
import com.example.william.my.core.download.state.DownloadState
import java.io.Serializable

class DownloadTask : Serializable {

    /**
     * 当前大小
     */
    var currentSize: Long = 0

    /**
     * 文件大小
     */
    var totalSize: Long = 0

    /**
     * 当前状态
     */
    var state = DownloadState.NONE.value

    var downloadUrl: String
    var downloadFilePath: String
    var downloadFileName: String
    var downloadCallback: DownloadCallback? = null

    constructor(url: String, path: String, name: String) {
        downloadUrl = url
        downloadFilePath = path
        downloadFileName = name
    }

    constructor(url: String, path: String, name: String, callback: DownloadCallback?) {
        downloadUrl = url
        downloadFilePath = path
        downloadFileName = name
        downloadCallback = callback
    }
}