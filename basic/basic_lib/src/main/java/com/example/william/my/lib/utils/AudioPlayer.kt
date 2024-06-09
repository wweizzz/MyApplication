package com.example.william.my.lib.utils

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log

/**
 * 复制腾讯im文件
 */
object AudioPlayer {

    private val TAG = this.javaClass.simpleName

    private const val DEFAULT_AUDIO_RECORD_MAX_TIME = 60
    private const val MAGIC_NUMBER = 500
    private const val MIN_RECORD_DURATION = 1000

    var mAudioRecordPath: String? = null
        private set

    private var mRecorder: MediaRecorder? = null

    private var mPlayer: MediaPlayer? = null

    val isPlaying: Boolean
        get() = mPlayer != null && mPlayer?.isPlaying == true

    private val mHandler: Handler = Handler(Looper.getMainLooper())

    fun startRecord(context: Context, callback: Callback) {
        mRecordCallback = callback
        try {
            mAudioRecordPath =
                context.applicationContext.externalCacheDir.toString() + "auto_" + System.currentTimeMillis() + ".m4a"
            mRecorder = MediaRecorder()
            mRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
            // 使用mp4容器并且后缀改为.m4a，来兼容小程序的播放
            mRecorder?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            mRecorder?.setOutputFile(mAudioRecordPath)
            mRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            mRecorder?.prepare()
            mRecorder?.start()
            // 最大录制时间之后需要停止录制
            mHandler.removeCallbacksAndMessages(null)
            mHandler.postDelayed({
                show("已达到最大语音长度")
                stopRecord(true)
            }, (DEFAULT_AUDIO_RECORD_MAX_TIME * 1000).toLong())
        } catch (e: Exception) {
            logcat("startRecord failed")
            stopRecord(false)
        }
    }

    fun stopRecord(completed: Boolean = true) {
        stopInternalRecord()
        onRecordCompleted(completed)
        mRecordCallback = null
    }

    private fun stopInternalRecord() {
        mHandler.removeCallbacksAndMessages(null)
        mRecorder?.release()
        mRecorder = null
    }

    fun startPlay(filePath: String?, callback: Callback) {
        mAudioRecordPath = filePath
        mPlayCallback = callback
        try {
            mPlayer = MediaPlayer()
            mPlayer?.setDataSource(mAudioRecordPath)
            mPlayer?.setOnCompletionListener {
                stopPlay(true)
            }
            mPlayer?.setOnPreparedListener {
                mPlayCallback?.onStart()
                mPlayer?.start()
            }
            mPlayer?.prepareAsync()
        } catch (e: Exception) {
            logcat("startPlay failed")
            show("语音文件已损坏或不存在")
            stopPlay(false)
        }
    }

    fun stopPlay(completed: Boolean = false) {
        stopInternalPlay()
        onPlayCompleted(completed)
        mPlayCallback = null
    }

    private fun stopInternalPlay() {
        mPlayer?.release()
        mPlayer = null
    }

    private fun onPlayCompleted(success: Boolean) {
        mPlayCallback?.onCompletion(success)
        mPlayer = null
    }

    private fun onRecordCompleted(success: Boolean) {
        mRecordCallback?.onCompletion(success)
        mRecorder = null
    }

    // 语音长度如果是59s多，因为外部会/1000取整，会一直显示59'，所以这里对长度进行处理，达到四舍五入的效果

    fun getDuration(): Int {
        if (TextUtils.isEmpty(mAudioRecordPath)) {
            return 0
        }
        var duration = 0
        // 通过初始化播放器的方式来获取真实的音频长度
        try {
            val mp = MediaPlayer()
            mp.setDataSource(mAudioRecordPath)
            mp.prepare()
            duration = mp.duration
            // 语音长度如果是59s多，因为外部会/1000取整，会一直显示59'，所以这里对长度进行处理，达到四舍五入的效果
            duration = if (duration < MIN_RECORD_DURATION) {
                0
            } else {
                duration + MAGIC_NUMBER
            }
        } catch (e: Exception) {
            Log.e(TAG, "getDuration failed", e)
        }
        if (duration < 0) {
            duration = 0
        }
        return duration
    }

    private var mRecordCallback: Callback? = null
    private var mPlayCallback: Callback? = null

    interface Callback {
        fun onStart()
        fun onCompletion(success: Boolean?)
    }

    private fun show(msg: String) {
        Utils.show(msg)
    }

    private fun logcat(msg: String) {
        Utils.e(TAG, msg)
    }
}