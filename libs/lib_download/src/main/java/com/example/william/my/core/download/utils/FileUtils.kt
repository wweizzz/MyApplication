package com.example.william.my.core.download.utils

import android.text.TextUtils
import com.example.william.my.core.download.task.DownloadTask
import java.io.File

object FileUtils {

    fun isFileExists(downloadTask: DownloadTask): Boolean {
        var flag = false
        val filePath: String =
            downloadTask.downloadFilePath + File.separator + downloadTask.downloadFileName
        val file = File(filePath)
        if (file.exists() && file.isFile) {
            flag = true
        }
        return flag
    }

    fun deleteFile(downloadTask: DownloadTask) {
        if (TextUtils.isEmpty(downloadTask.downloadFilePath)) return
        val filePath: String =
            downloadTask.downloadFilePath + File.separator + downloadTask.downloadFileName
        val file = File(filePath)
        if (file.exists() && file.isFile) {
            if (file.delete()) {
                println("删除文件：" + filePath + "成功！")
            } else {
                println("删除文件：" + filePath + "失败！")
            }
        } else {
            println("删除文件失败：" + filePath + "不存在！")
        }
    }
}