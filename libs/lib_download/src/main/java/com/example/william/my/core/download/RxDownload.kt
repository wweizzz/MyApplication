package com.example.william.my.core.download

import android.os.Handler
import android.os.Looper
import com.example.william.my.core.download.function.DownloadFunction
import com.example.william.my.core.download.observer.DownloadObserver
import com.example.william.my.core.download.state.DownloadState
import com.example.william.my.core.download.task.DownloadTask
import com.example.william.my.core.download.utils.FileUtils
import com.example.william.my.core.okhttp.helper.OkHttpHelper
import com.example.william.my.core.okhttp.interceptor.InterceptorProgress
import com.example.william.my.core.okhttp.utils.HttpLogger
import com.example.william.my.core.retrofit.api.Api
import com.example.william.my.core.retrofit.helper.RetrofitHelper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

object RxDownload {

    private val mHandler: Handler = Handler(Looper.getMainLooper())

    // 下载队列
    private val mDownloadTaskMap: MutableMap<String, DownloadTask> = mutableMapOf()

    // 下载队列
    private val mDownloadObserverMap: MutableMap<String, DownloadObserver> = mutableMapOf()


    fun start(downloadTask: DownloadTask?) {
        if (downloadTask == null) return

        /**
         * 检查下载任务
         */
        if (!checkTask(downloadTask)) {
            return
        }

        /**
         * 检查下载文件
         */
        if (!checkFile(downloadTask)) {
            return
        }

        /**
         * 添加到下载队列
         */
        mDownloadTaskMap[downloadTask.downloadUrl] = downloadTask

        val observer = DownloadObserver(downloadTask, mHandler)
        mDownloadObserverMap[downloadTask.downloadUrl] = observer

        val interceptor = InterceptorProgress(observer)
        val client = OkHttpHelper.setInterceptor(interceptor).client()
        val retrofit = RetrofitHelper.client(client).retrofit()
        val api: Api = retrofit.create(Api::class.java)
        api
            .downloadFile("bytes=" + downloadTask.currentSize, downloadTask.downloadUrl)

            .subscribeOn(Schedulers.io())
            .map(DownloadFunction(downloadTask))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

    /**
     * @return 是否需要下载
     */
    private fun checkTask(downloadTask: DownloadTask): Boolean {
        return mDownloadTaskMap[downloadTask.downloadUrl]?.let { task ->
            if (task.state == DownloadState.STOP.value || task.state == DownloadState.ERROR.value) {
                true
            } else {
                HttpLogger.debug("已添加任务：" + task.downloadUrl)
                false
            }
        } != false
    }

    /**
     * @return 是否需要下载
     */
    private fun checkFile(downloadTask: DownloadTask): Boolean {
        if (!FileUtils.isFileExists(downloadTask) && downloadTask.currentSize > 0) {
            downloadTask.currentSize = 0
            HttpLogger.debug("已存在文件：" + downloadTask.downloadUrl)
            return false
        }
        return true
    }

    fun stop(downloadTask: DownloadTask?) {
        if (downloadTask == null) return

        HttpLogger.debug("暂停任务：" + downloadTask.downloadUrl)

        /**
         * 移除队列
         */
        mDownloadObserverMap[downloadTask.downloadUrl]?.let { observer ->
            observer.dispose()
            mDownloadObserverMap.remove(downloadTask.downloadUrl)
        }

        /**
         * 设置数据状态
         */
        downloadTask.state = DownloadState.STOP.value
        downloadTask.downloadCallback?.onProgress(downloadTask)
        mDownloadTaskMap[downloadTask.downloadUrl] = downloadTask
    }

    fun remove(downloadTask: DownloadTask?, removeFile: Boolean = false) {
        if (downloadTask == null) return

        HttpLogger.debug("移除任务：" + downloadTask.downloadUrl)

        /**
         * 暂停任务
         */
        if (downloadTask.state != DownloadState.FINISH.value) {
            stop(downloadTask)
        }

        /**
         * 删除文件
         */
        if (removeFile) {
            FileUtils.deleteFile(downloadTask)
        }
    }
}