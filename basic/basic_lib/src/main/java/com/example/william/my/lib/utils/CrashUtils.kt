package com.example.william.my.lib.utils

import android.content.pm.PackageManager
import android.os.Build
import android.text.TextUtils
import com.example.william.my.lib.app.BaseApp
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.StringTokenizer

object CrashUtils {

    private val FILE_SEP = System.getProperty("file.separator")
    private val LINE_SEP = System.getProperty("line.separator")
    private val DEFAULT_UNCAUGHT_EXCEPTION_HANDLER = Thread.getDefaultUncaughtExceptionHandler()

    /**
     * Initialization
     *
     * @param crashDir The directory of saving crash information.
     */
    fun init(crashDir: File) {
        init(crashDir.absolutePath, null)
    }

    /**
     * Initialization
     *
     * @param onCrashListener The crash listener.
     */
    fun init(onCrashListener: OnCrashListener?) {
        init("", onCrashListener)
    }

    /**
     * Initialization
     *
     * @param crashDir        The directory of saving crash information.
     * @param onCrashListener The crash listener.
     */
    fun init(crashDir: File, onCrashListener: OnCrashListener?) {
        init(crashDir.absolutePath, onCrashListener)
    }

    /**
     * Initialization
     *
     * @param crashDirPath    The directory's path of saving crash information.
     * @param onCrashListener The crash listener.
     */
    @JvmOverloads
    fun init(crashDirPath: String = "", onCrashListener: OnCrashListener? = null) {
        val dirPath: String = if (FileSDCardUtil.isSpace(crashDirPath)) {
            FileSDCardUtil.getCacheDirPath() + FILE_SEP + "crash" + FILE_SEP
        } else {
            if (crashDirPath.endsWith(FILE_SEP!!)) crashDirPath else crashDirPath + FILE_SEP
        }
        Thread.setDefaultUncaughtExceptionHandler(
            getUncaughtExceptionHandler(
                dirPath,
                onCrashListener
            )
        )
    }

    private fun getUncaughtExceptionHandler(
        dirPath: String, onCrashListener: OnCrashListener?
    ): Thread.UncaughtExceptionHandler {
        return Thread.UncaughtExceptionHandler { t, e ->
            val time = SimpleDateFormat("yyyy_MM_dd-HH_mm_ss", Locale.CHINA).format(Date())
            val crashInfo = CrashInfo(time, e)
            val crashFile = "$dirPath$time.txt"
            FileSDCardUtil.writeFileFromString(crashFile, crashInfo.toString(), true)
            onCrashListener?.onCrash(crashInfo)
            DEFAULT_UNCAUGHT_EXCEPTION_HANDLER?.uncaughtException(t, e)
        }
    }

    private fun getAppVersionName(packageName: String?): String {
        return if (FileSDCardUtil.isSpace(packageName)) "" else try {
            val pm = BaseApp.app.packageManager
            val pi = pm.getPackageInfo(packageName!!, 0)
            if (pi == null) "" else pi.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            ""
        }
    }

    private fun getAppVersionCode(packageName: String?): Int {
        return if (FileSDCardUtil.isSpace(packageName)) -1 else try {
            val pm = BaseApp.app.packageManager
            val pi = pm.getPackageInfo(packageName!!, 0)
            pi?.versionCode ?: -1
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            -1
        }
    }

    interface OnCrashListener {
        fun onCrash(crashInfo: CrashInfo)
    }

    data class FileHead(private val mName: String) {

        private val mFirst = LinkedHashMap<String, String>()
        private val mLast = LinkedHashMap<String, String>()
        fun addFirst(key: String, value: String) {
            append2Host(mFirst, key, value)
        }

        fun append(extra: Map<String, String>?) {
            append2Host(mLast, extra)
        }

        fun append(key: String, value: String) {
            append2Host(mLast, key, value)
        }

        private fun append2Host(host: MutableMap<String, String>, extra: Map<String, String>?) {
            if (extra == null || extra.isEmpty()) {
                return
            }
            for ((key, value) in extra) {
                append2Host(host, key, value)
            }
        }

        private fun append2Host(host: MutableMap<String, String>, key: String, value: String) {
            var key = key
            if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
                return
            }
            val delta = 19 - key.length // 19 is length of "Device Manufacturer"
            if (delta > 0) {
                key += "                   ".substring(0, delta)
            }
            host[key] = value
        }

        private val appended: String
            get() {
                val sb = StringBuilder()
                for ((key, value) in mLast) {
                    sb.append(key).append(": ").append(value).append("\n")
                }
                return sb.toString()
            }

        override fun toString(): String {
            val sb = StringBuilder()
            val border = "************* $mName Head ****************\n"
            sb.append(border)
            for ((key, value) in mFirst) {
                sb.append(key).append(": ").append(value).append("\n")
            }
            sb.append("Device Manufacturer: ").append(Build.MANUFACTURER).append("\n")
            sb.append("Device Model       : ").append(Build.MODEL).append("\n")
            sb.append("Android Version    : ").append(Build.VERSION.RELEASE).append("\n")
            sb.append("Android SDK        : ").append(Build.VERSION.SDK_INT).append("\n")
            sb.append("App VersionName    : ")
                .append(getAppVersionName(BaseApp.app.packageName))
                .append("\n")
            sb.append("App VersionCode    : ")
                .append(getAppVersionCode(BaseApp.app.packageName))
                .append("\n")
            sb.append(appended)
            return sb.append(border).append("\n").toString()
        }
    }

    fun getFullStackTrace(throwable: Throwable?): String {
        var throwable = throwable
        val throwableList: MutableList<Throwable> = ArrayList()
        while (throwable != null && !throwableList.contains(throwable)) {
            throwableList.add(throwable)
            throwable = throwable.cause
        }
        val size = throwableList.size
        val frames: MutableList<String> = ArrayList()
        var nextTrace = getStackFrameList(throwableList[size - 1])
        var i = size
        while (--i >= 0) {
            val trace = nextTrace
            if (i != 0) {
                nextTrace = getStackFrameList(throwableList[i - 1])
                removeCommonFrames(trace, nextTrace)
            }
            if (i == size - 1) {
                frames.add(throwableList[i].toString())
            } else {
                frames.add(" Caused by: " + throwableList[i].toString())
            }
            frames.addAll(trace)
        }
        val sb = StringBuilder()
        for (element in frames) {
            sb.append(element).append(LINE_SEP)
        }
        return sb.toString()
    }

    private fun getStackFrameList(throwable: Throwable): MutableList<String> {
        val sw = StringWriter()
        val pw = PrintWriter(sw, true)
        throwable.printStackTrace(pw)
        val stackTrace = sw.toString()
        val frames = StringTokenizer(stackTrace, LINE_SEP)
        val list: MutableList<String> = ArrayList()
        var traceStarted = false
        while (frames.hasMoreTokens()) {
            val token = frames.nextToken()
            // Determine if the line starts with <whitespace>at
            val at = token.indexOf("at")
            if (at != -1 && token.substring(0, at).trim { it <= ' ' }.isEmpty()) {
                traceStarted = true
                list.add(token)
            } else if (traceStarted) {
                break
            }
        }
        return list
    }

    private fun removeCommonFrames(causeFrames: MutableList<String>, wrapperFrames: List<String>) {
        var causeFrameIndex = causeFrames.size - 1
        var wrapperFrameIndex = wrapperFrames.size - 1
        while (causeFrameIndex >= 0 && wrapperFrameIndex >= 0) {
            // Remove the frame from the cause trace if it is the same
            // as in the wrapper trace
            val causeFrame = causeFrames[causeFrameIndex]
            val wrapperFrame = wrapperFrames[wrapperFrameIndex]
            if (causeFrame == wrapperFrame) {
                causeFrames.removeAt(causeFrameIndex)
            }
            causeFrameIndex--
            wrapperFrameIndex--
        }
    }

    data class CrashInfo(val time: String, val throwable: Throwable) {

        private val mFileHeadProvider: FileHead = FileHead("Crash")

        init {
            mFileHeadProvider.addFirst("Time Of Crash", time)
        }

        fun addExtraHead(extraHead: Map<String, String>) {
            mFileHeadProvider.append(extraHead)
        }

        fun addExtraHead(key: String, value: String) {
            mFileHeadProvider.append(key, value)
        }

        override fun toString(): String {
            return mFileHeadProvider.toString() + getFullStackTrace(throwable)
        }
    }
}