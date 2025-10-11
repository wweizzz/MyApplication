package com.example.william.my.lib.utils

import android.net.Uri
import com.example.william.my.lib.app.BaseApp
import java.io.BufferedOutputStream
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

object FileWriteUtils {

    fun writeFileFromString(filePath: String, content: String?, append: Boolean): Boolean {
        val file: File? = FileSDCardUtil.getFileByPath(filePath)
        return writeFileFromString(file, content, append)
    }

    fun writeFileFromString(file: File?, content: String?, append: Boolean): Boolean {
        if (file == null || content == null) return false
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

    fun writeFileFromIS(filePath: String, inputStream: InputStream): Boolean {
        val file: File? = FileSDCardUtil.getFileByPath(filePath)
        return writeFileFromIS(file, inputStream)
    }

    fun writeFileFromIS(file: File?, inputStream: InputStream): Boolean {
        var os: OutputStream? = null
        return try {
            os = BufferedOutputStream(FileOutputStream(file), 1024 * 4)
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

    fun writeFileFromIS(uri: Uri?, inputStream: InputStream): Boolean {
        var os: OutputStream? = null
        return try {
            os = BaseApp.app.contentResolver.openOutputStream(uri!!)
            val data = ByteArray(1024 * 4)
            var len: Int
            while (inputStream.read(data).also { len = it } != -1) {
                os?.write(data, 0, len)
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
}