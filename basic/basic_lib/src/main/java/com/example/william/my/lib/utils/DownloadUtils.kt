package com.example.william.my.lib.utils

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStream

object DownloadUtils {
    fun saveFileToDownloads(context: Context, filePath: String?, fileName: String?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val values = ContentValues()
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            val uri =
                context.contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values)
            try {
                val inputStream: InputStream = FileInputStream(filePath)
                FileSDCardUtil.writeFileFromIS(context, uri, inputStream)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
    }
}