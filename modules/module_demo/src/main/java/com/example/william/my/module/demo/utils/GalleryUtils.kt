package com.example.william.my.module.demo.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import java.io.File

object GalleryUtils {

    private const val TAG = "GalleryUtils"

    fun saveBitmapToGallery(
        context: Context,
        bitmap: Bitmap,
        dirName: String,
        onComplete: ((Boolean, String) -> Unit)? = null
    ) {
        //ImageUtils.save2Album(
        //    bitmap, dirName, CompressFormat.PNG
        //)
    }

    /**
     * 保存视频文件到系统相册
     * @param context 上下文
     * @param file 要保存的视频文件
     * @param onComplete 保存完成回调 (success: 是否成功, message: 结果消息)
     */
    fun saveVideoToGallery(
        context: Context,
        file: File?,
        dirName: String,
        onComplete: ((Boolean, String) -> Unit)? = null
    ) {
        val safeDirName =
            if (TextUtils.isEmpty(dirName)) context.packageName else dirName
        val fileName = System.currentTimeMillis().toString() + "." + "mp4"
        file?.let { file ->
            try {
                val resolver = context.contentResolver

                val contentValues = ContentValues().apply {
                    put(MediaStore.Video.Media.DISPLAY_NAME, fileName)
                    put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
                    put(MediaStore.Video.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
                    put(MediaStore.Video.Media.DATE_TAKEN, System.currentTimeMillis())
                    put(
                        MediaStore.Video.Media.RELATIVE_PATH,
                        Environment.DIRECTORY_DCIM + "/" + safeDirName
                    )
                    put(MediaStore.Video.Media.IS_PENDING, 1)
                }

                val contentUri: Uri
                if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                } else {
                    contentUri = MediaStore.Video.Media.INTERNAL_CONTENT_URI
                }

                val videoUri = resolver.insert(contentUri, contentValues)
                videoUri?.let { uri ->

                    try {
                        resolver.openOutputStream(uri)?.use { os ->
                            file.inputStream().use { it.copyTo(os) }
                        }

                        contentValues.clear()
                        contentValues.put(MediaStore.Video.Media.IS_PENDING, 0)
                        resolver.update(uri, contentValues, null, null)

                        onComplete?.invoke(true, "视频已保存到相册")
                    } catch (e: Exception) {
                        Log.e(TAG, "保存视频失败: ${e.message}", e)
                        onComplete?.invoke(false, "保存视频失败: ${e.localizedMessage}")
                    } finally {
                        // 删除临时文件
                        if (!file.delete()) {
                            Log.e(TAG, "无法删除临时文件: ${file.absolutePath}")
                        }
                    }
                } ?: run {
                    onComplete?.invoke(false, "保存视频失败: Failed to create media URI")
                }
            } catch (e: Exception) {
                Log.e(TAG, "保存视频异常: ${e.message}", e)
                onComplete?.invoke(false, "保存视频异常: ${e.localizedMessage ?: "未知错误"}")
            }
        }
    }
}