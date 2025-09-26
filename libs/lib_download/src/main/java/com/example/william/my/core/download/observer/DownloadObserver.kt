package com.example.william.my.core.download.observer

import android.os.Handler
import com.example.william.my.core.download.RxDownload
import com.example.william.my.core.download.callback.DownloadCallback
import com.example.william.my.core.download.state.DownloadState
import com.example.william.my.core.download.task.DownloadTask
import com.example.william.my.core.okhttp.listener.ResponseProgressListener
import com.example.william.my.core.okhttp.utils.HttpLogger
import com.example.william.my.core.retrofit.exception.ApiException
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import java.lang.ref.SoftReference

/**
 * 下载观察者
 */
class DownloadObserver(downloadTask: DownloadTask, private val mHandler: Handler) :
    DisposableSingleObserver<DownloadTask>(), ResponseProgressListener {

    private var mDownloadTask: DownloadTask
    private var mDownloadCallback: SoftReference<DownloadCallback>

    init {
        mDownloadTask = downloadTask
        mDownloadCallback = SoftReference<DownloadCallback>(downloadTask.downloadCallback)
    }

    fun setDownload(downloadTask: DownloadTask) {
        mDownloadTask = downloadTask
        mDownloadCallback = SoftReference<DownloadCallback>(downloadTask.downloadCallback)
    }

    override fun onStart() {
        super.onStart()
        /*
         * 等待状态
         */
        mDownloadTask.state = DownloadState.WAITING.value
        HttpLogger.debug("等待下载：" + mDownloadTask.downloadUrl)
        mDownloadCallback.get()?.onProgress(mDownloadTask)
    }

    override fun onSuccess(downloadTask: DownloadTask) {
        /*
         * 完成状态
         */
        mDownloadTask.state = DownloadState.FINISH.value
        HttpLogger.debug("下载完成：" + downloadTask.downloadUrl)
        mDownloadCallback.get()?.onProgress(mDownloadTask)

        /*
         * 移除下载
         */
        RxDownload.remove(mDownloadTask)
        dispose()
    }

    override fun onError(e: Throwable) {
        /*
         * 错误状态
         */
        mDownloadTask.state = DownloadState.ERROR.value
        HttpLogger.debug("下载错误：" + mDownloadTask.downloadUrl)

        if (e is ApiException) {
            mDownloadCallback.get()?.onFailure(e)
        } else {
            mDownloadCallback.get()?.onFailure(ApiException(e, ApiException.Error.UNKNOWN))
        }

        /*
         * 移除下载
         */
        RxDownload.remove(mDownloadTask)
        dispose()
    }

    override fun onProgress(url: String, currentSize: Long, totalSize: Long) {
        if (mDownloadTask.totalSize > totalSize) {
            mDownloadTask.currentSize = mDownloadTask.totalSize - totalSize + currentSize
        } else {
            mDownloadTask.totalSize = totalSize
        }
        mHandler.post {
            /*下载进度==总进度修改为完成状态*/
            if (mDownloadTask.currentSize == mDownloadTask.totalSize && mDownloadTask.totalSize != 0L) {
                mDownloadTask.state = DownloadState.FINISH.value
            }

            /*如果暂停或者停止状态延迟，不需要继续发送回调，影响显示*/
            if (mDownloadTask.state != DownloadState.STOP.value) {
                mDownloadCallback.get()?.onProgress(mDownloadTask)
            }
        }
    }
}