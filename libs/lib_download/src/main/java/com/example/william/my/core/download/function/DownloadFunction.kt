package com.example.william.my.core.download.function

import com.example.william.my.core.download.state.DownloadState
import com.example.william.my.core.download.task.DownloadTask
import com.example.william.my.core.retrofit.utils.FileIOUtils
import io.reactivex.rxjava3.functions.Function
import okhttp3.ResponseBody
import java.io.File

class DownloadFunction(private val downloadTask: DownloadTask) :
    Function<ResponseBody, DownloadTask> {

    override fun apply(body: ResponseBody): DownloadTask {
        downloadTask.state = DownloadState.LOADING.value
        downloadTask.totalSize = body.contentLength()

        FileIOUtils.writeFileFromIS(
            File(downloadTask.downloadFilePath + File.separator + downloadTask.downloadFileName),
            body.byteStream(),
            true,
            null
        )
        return downloadTask
    }
}