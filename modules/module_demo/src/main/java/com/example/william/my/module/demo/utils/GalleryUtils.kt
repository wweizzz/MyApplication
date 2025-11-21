package com.example.william.my.module.demo.utils

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.william.my.lib.app.BaseApp
import com.example.william.my.lib.utils.UriUtils.uri2File
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

object GalleryUtils {

    fun saveBitmapToGallery(dirName: String, bitmap: Bitmap?) {
        //ImageUtils.save2Album(
        //    bitmap, dirName, Bitmap.CompressFormat.PNG
        //)
    }

    fun saveFileToGallery(dirName: String, filePath: String) {
        val tempFile = File(filePath)
        if (!tempFile.exists()) {
            return
        }
        val fileName = filePath.substringAfterLast('/')
            .removeSuffix(".temp")
        save2Album(tempFile, dirName, fileName)
    }

    fun save2Album(
        file: File,
        dirName: String?,
        fileName: String?,
    ): File? {
        val safeDirName = if (TextUtils.isEmpty(dirName)) BaseApp.app.packageName else dirName
        val targetFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Android 10+ 使用 MediaStore API
            saveVideoToMediaStore(BaseApp.app.applicationContext, file, fileName, safeDirName)
        } else {
            // Android 9及以下 使用文件系统
            saveVideoToFileSystem(BaseApp.app.applicationContext, file, fileName, safeDirName)
        }
        return targetFile
    }

    private fun saveVideoToMediaStore(
        context: Context, file: File, fileName: String?, dirName: String?
    ): File? {
        val contentValues = ContentValues()
        contentValues.put(MediaStore.Video.Media.DISPLAY_NAME, fileName)
        contentValues.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
        val contentUri: Uri
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        } else {
            contentUri = MediaStore.Video.Media.INTERNAL_CONTENT_URI
        }
        contentValues.put(
            MediaStore.Video.Media.RELATIVE_PATH, Environment.DIRECTORY_DCIM + "/" + dirName
        )
        // 标记为待处理
        contentValues.put(MediaStore.MediaColumns.IS_PENDING, 1)
        val uri = context.contentResolver.insert(contentUri, contentValues)
        if (uri == null) {
            return null
        }

        var inputStream: FileInputStream? = null
        var outputStream: FileOutputStream? = null
        try {

            inputStream = FileInputStream(file)
            outputStream = context.contentResolver.openOutputStream(uri) as FileOutputStream

            inputStream.copyTo(outputStream)
            outputStream.flush()

            // 标记为已完成
            contentValues.clear()
            contentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
            context.contentResolver.update(uri, contentValues, null, null)

            return uri2File(uri)
        } catch (e: Exception) {
            context.contentResolver.delete(uri, null, null)
            e.printStackTrace()
            return null
        } finally {
            try {
                inputStream?.close()
                outputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun saveVideoToFileSystem(
        context: Context, file: File, fileName: String?, dirName: String?
    ): File? {
        if (ContextCompat.checkSelfPermission(
                context, Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.e("GalleryUtils", "save to album need storage permission")
            return null
        }

        // 创建目标目录
        val picDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
        val destFile = File(picDir, "$dirName/$fileName")

        var inputStream: FileInputStream? = null
        var outputStream: FileOutputStream? = null
        try {
            inputStream = FileInputStream(file)
            outputStream = FileOutputStream(destFile)

            inputStream.copyTo(outputStream)
            outputStream.flush()

            // 通知系统扫描新文件
            scanFile(context, destFile.absolutePath)
            return destFile
        } catch (e: IOException) {
            Log.e("GalleryUtils", "Failed to copy video file to destination", e)
            e.printStackTrace()
            return null
        } finally {
            try {
                inputStream?.close()
                outputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun scanFile(context: Context, filePath: String) {
        MediaScannerConnection.scanFile(context, arrayOf(filePath), null) { path, uri ->

        }
    }
}