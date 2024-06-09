package com.example.william.my.lib.utils

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import java.io.BufferedOutputStream
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

/**
 * 内部存储：
 * 通过Context.getFilesDir()方法可以获取到 /data/user/0/你的应用包名/files
 * 通过Context.getCacheDir()方法可以获取到 /data/user/0/你的应用包名/cache
 * 外部存储：
 * 通过Context.getExternalFilesDir()方法可以获取到 SDCard/Android/data/你的应用包名/files/目录，一般放一些长时间保存的数据
 * 通过Context.getExternalCacheDir()方法可以获取到 SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
 * 两个目录分别对应 设置->应用->应用详情里面的”清除数据“与”清除缓存“选项
 */
object FileSDCardUtil {

    fun isSDCardEnableByEnvironment(): Boolean {
        return Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()
    }

    /**
     * @return /storage/emulated/0/Android/data/包名/cache
     */
    fun getCacheDir(context: Context): File? {
        return if (isSDCardEnableByEnvironment()) {
            //外部存储可用
            context.applicationContext.externalCacheDir
        } else {
            //外部存储不可用
            context.applicationContext.cacheDir
        }
    }

    fun getCacheDirPath(context: Context): String {
        return if (isSDCardEnableByEnvironment()) {
            //外部存储可用
            context.applicationContext.externalCacheDir?.path ?: ""
        } else {
            //外部存储不可用
            context.applicationContext.cacheDir?.path ?: ""
        }
    }

    /**
     * @return /storage/emulated/0/Android/data/包名/files
     */
    fun getFileDir(context: Context, type: String): File? {
        return if (isSDCardEnableByEnvironment()) {
            //外部存储可用
            context.applicationContext.getExternalFilesDir(type)
        } else {
            //外部存储不可用
            context.applicationContext.filesDir
        }
    }

    fun getFileDirPath(context: Context, type: String): String {
        return if (isSDCardEnableByEnvironment()) {
            //外部存储可用
            context.applicationContext.getExternalFilesDir(type)?.path ?: ""
        } else {
            //外部存储不可用
            context.applicationContext.filesDir?.path ?: ""
        }
    }

    fun isSpace(s: String?): Boolean {
        if (s == null) return true
        var i = 0
        val len = s.length
        while (i < len) {
            if (!Character.isWhitespace(s[i])) {
                return false
            }
            ++i
        }
        return true
    }

    fun getFileByPath(filePath: String): File? {
        return if (isSpace(filePath)) null else File(filePath)
    }

    fun createOrExistsDir(file: File?): Boolean {
        return file != null && if (file.exists()) file.isDirectory else file.mkdirs()
    }

    fun createOrExistsFile(file: File?): Boolean {
        if (file == null) return false
        if (file.exists()) return file.isFile
        return if (!createOrExistsDir(file.parentFile)) false else try {
            file.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    fun writeFileFromString(filePath: String, content: String?, append: Boolean): Boolean {
        return writeFileFromString(getFileByPath(filePath), content, append)
    }

    fun writeFileFromString(file: File?, content: String?, append: Boolean): Boolean {
        if (file == null || content == null) return false
        if (!createOrExistsFile(file)) {
            Log.e("FileIOUtils", "create file <$file> failed.")
            return false
        }
        var bw: BufferedWriter? = null
        return try {
            bw = BufferedWriter(FileWriter(file, append))
            bw.write(content)
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        } finally {
            try {
                bw?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun writeFileFromIS(file: File?, inputStream: InputStream) {
        var os: OutputStream? = null
        try {
            os = BufferedOutputStream(FileOutputStream(file), 1024 * 4)
            val data = ByteArray(1024 * 4)
            var len: Int
            while (inputStream.read(data).also { len = it } != -1) {
                os.write(data, 0, len)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                inputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            try {
                os?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun writeFileFromIS(context: Context, uri: Uri?, inputStream: InputStream) {
        var os: OutputStream? = null
        try {
            os = context.contentResolver.openOutputStream(uri!!)
            val data = ByteArray(1024 * 4)
            var len: Int
            while (inputStream.read(data).also { len = it } != -1) {
                os?.write(data, 0, len)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                inputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            try {
                os?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}