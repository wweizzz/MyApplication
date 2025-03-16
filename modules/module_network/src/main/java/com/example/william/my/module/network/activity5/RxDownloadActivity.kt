package com.example.william.my.module.network.activity5

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity
import com.example.william.my.basic.basic_module.base.Constants
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.core.download.RxDownload
import com.example.william.my.core.download.task.DownloadTask

/**
 * https://square.github.io/retrofit
 * https://github.com/square/retrofit
 */
@Route(path = RouterPath.Network.RxDownload)
class RxDownloadActivity : BasicResponseActivity() {

    override fun onResponseClick(view: View) {
        super.onResponseClick(view)

        download()
    }

    private fun download() {
        val downloadTask = DownloadTask(
            Constants.Url_Download,
            getExternalFilesDir("rx_download")!!.absolutePath,
            "rx_download.apk"
        )
        RxDownload.start(downloadTask)
    }
}