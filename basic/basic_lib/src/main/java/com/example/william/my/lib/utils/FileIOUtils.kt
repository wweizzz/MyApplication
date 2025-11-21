package com.example.william.my.lib.utils

import android.net.Uri
import android.util.Log
import com.blankj.utilcode.util.FileUtils
import java.io.BufferedOutputStream
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

object FileIOUtils {

    const val sBufferSize: Int = 1024 * 512

    private fun createOrExistsFile(file: File?): Boolean {
        if (file == null) return false
        if (file.exists()) return file.isFile()
        if (!FileUtils.createOrExistsDir(file.getParentFile())) return false
        try {
            return file.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        }
    }

    fun writeFileFromString(filePath: String, content: String?, append: Boolean): Boolean {
        val file: File? = FileSDCardUtil.getFileByPath(filePath)
        return writeFileFromString(file, content, append)
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

    fun writeFileFromString(uri: Uri?, content: String?, append: Boolean): Boolean {
        return false
    }

    fun writeFileFromIS(filePath: String, inputStream: InputStream?, append: Boolean): Boolean {
        val file: File? = FileSDCardUtil.getFileByPath(filePath)
        return writeFileFromIS(file, inputStream, append)
    }

    fun writeFileFromIS(file: File?, inputStream: InputStream?, append: Boolean): Boolean {
        if (inputStream == null || !createOrExistsFile(file)) {
            Log.e("FileIOUtils", "create file <$file> failed.")
            return false
        }
        var os: OutputStream? = null
        return try {
            os = BufferedOutputStream(FileOutputStream(file, append), sBufferSize)
            val data = ByteArray(1024 * 4)
            var len: Int
            while (inputStream.read(data).also { len = it } != -1) {
                os.write(data, 0, len)
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

    fun writeFileFromIS(uri: Uri?, inputStream: InputStream?, append: Boolean): Boolean {
        return false
    }
}