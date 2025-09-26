package com.example.william.my.core.retrofit.utils

import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

object FileIOUtils {

    private const val sBufferSize = 1024 * 512

    /**
     * 将输入流写入文件
     */
    fun writeFileFromIS(
        file: File,
        inputStream: InputStream?,
        append: Boolean,
        listener: OnProgressUpdateListener?
    ): Boolean {
        if (inputStream == null || !createOrExistsFile(file)) {
            return false
        }
        var os: OutputStream? = null
        return try {
            os = BufferedOutputStream(FileOutputStream(file, append), sBufferSize)
            if (listener == null) {
                val data = ByteArray(sBufferSize)
                var len: Int
                while (inputStream.read(data).also { len = it } != -1) {
                    os.write(data, 0, len)
                }
            } else {
                val totalSize = inputStream.available().toDouble()
                var curSize = 0
                listener.onProgressUpdate(0.0)
                val data = ByteArray(sBufferSize)
                var len: Int
                while (inputStream.read(data).also { len = it } != -1) {
                    os.write(data, 0, len)
                    curSize += len
                    listener.onProgressUpdate(curSize / totalSize)
                }
            }
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
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

    /**
     * 判断文件是否存在，不存在则判断是否创建成功
     */
    private fun createOrExistsFile(file: File?): Boolean {
        if (file == null) return false
        if (file.exists()) return file.isFile
        return if (!createOrExistsDir(file.parentFile)) false else try {
            file.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    private fun createOrExistsDir(file: File?): Boolean {
        return file != null && if (file.exists()) file.isDirectory else file.mkdirs()
    }

    interface OnProgressUpdateListener {
        fun onProgressUpdate(progress: Double)
    }
}