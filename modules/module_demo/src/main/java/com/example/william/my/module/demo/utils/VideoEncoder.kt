package com.example.william.my.module.demo.utils

import android.media.MediaCodec
import android.media.MediaCodecInfo
import android.media.MediaFormat
import android.media.MediaMuxer
import android.util.Log
import java.io.IOException

object VideoEncoder {

    private const val TAG = "VideoEncoder"

    // --- 编码器配置常量 ---
    private const val MIME_TYPE = MediaFormat.MIMETYPE_VIDEO_AVC
    private const val BIT_FACTOR = 0.25f
    private const val FRAME_RATE = 60
    private const val IFRAME_INTERVAL = 1

    // --- 核心组件 ---
    private var mEncoder: MediaCodec? = null
    private var mMediaMuxer: MediaMuxer? = null

    // --- 状态管理 ---
    private var mIsInitialized = false // 标记编码器是否已初始化
    private var mIsMuxerStarted = false // 标记编码器是否已启动
    private var videoTrackIndex = -1

    // 存储初始化参数
    private var mOutputPath: String? = null
    private var mWidth: Int = 0
    private var mHeight: Int = 0

    /**
     * 初始化视频编码器
     *
     * @param outputPath 输出视频文件的路径
     * @param width 视频宽度
     * @param height 视频高度
     * @return 初始化是否成功
     */
    fun init(outputPath: String, width: Int, height: Int): Boolean {
        if (mIsInitialized) {
            Log.w(TAG, "Encoder already initialized.")
            return true
        }

        Log.i(TAG, "Starting encoder initialization...")

        // 存储参数
        mOutputPath = outputPath
        mWidth = width
        mHeight = height

        // 执行初始化逻辑
        val success = performInitialization()
        if (success) {
            mIsInitialized = true
            Log.i(TAG, "Encoder initialized successfully.")
        } else {
            Log.e(TAG, "Initialization failed.")
            // 重置参数
            mOutputPath = null
            mWidth = 0
            mHeight = 0
        }

        return success
    }

    /**
     * 编码一帧 YUV 数据。
     * 如果编码器尚未初始化，将返回错误。
     *
     * @param data YUV 数据 (NV12)
     * @param timeUs 这一帧的时间戳（微秒）
     */
    fun encodeFrame(data: ByteArray, timeUs: Long) {
        // 检查编码器是否已初始化
        if (!mIsInitialized) {
            Log.e(TAG, "Encoder not initialized. Call initialize() first.")
            return
        }

        // --- 编码逻辑
        mEncoder?.let { encoder ->
            // 将数据送入编码器
            val inputBufferIndex = encoder.dequeueInputBuffer(10000)
            if (inputBufferIndex >= 0) {
                val inputBuffer = encoder.getInputBuffer(inputBufferIndex)
                inputBuffer?.clear()
                inputBuffer?.put(data)
                encoder.queueInputBuffer(inputBufferIndex, 0, data.size, timeUs, 0)
            }

            // 从编码器获取输出数据
            val bufferInfo = MediaCodec.BufferInfo()
            while (mIsInitialized) {
                val outputBufferIndex = encoder.dequeueOutputBuffer(bufferInfo, 10000)

                when (outputBufferIndex) {
                    // 事件：输出格式已改变，这是添加轨道并启动 Muxer 的最佳时机
                    MediaCodec.INFO_OUTPUT_FORMAT_CHANGED -> {
                        Log.i(TAG, "Output format changed: ${encoder.outputFormat}")
                        videoTrackIndex = mMediaMuxer?.addTrack(encoder.outputFormat) ?: -1
                        if (videoTrackIndex != -1) {
                            mMediaMuxer?.start()
                            mIsMuxerStarted = true
                            Log.i(TAG, "MediaMuxer started.")
                        }
                    }

                    // 事件：当前没有可用的输出缓冲区，稍后重试
                    MediaCodec.INFO_TRY_AGAIN_LATER -> {
                        // 跳出本次循环，等待下一帧数据输入
                        break
                    }

                    // 情况：返回了一个有效的输出缓冲区索引
                    else -> {
                        if (outputBufferIndex >= 0) {
                            // 只有在 Muxer 启动后，才能写入实际的帧数据
                            if (mIsMuxerStarted) {
                                // 忽略 BUFFER_FLAG_CODEC_CONFIG 标志的缓冲区，
                                // 因为 CSD (SPS/PPS) 数据已经通过 outputFormat 处理了
                                if ((bufferInfo.flags and MediaCodec.BUFFER_FLAG_CODEC_CONFIG) == 0) {
                                    val outputBuffer = encoder.getOutputBuffer(outputBufferIndex)
                                    outputBuffer?.let { buffer ->
                                        mMediaMuxer?.writeSampleData(
                                            videoTrackIndex, buffer, bufferInfo
                                        )
                                    }
                                }
                            }
                            // 释放输出缓冲区，无论是否写入数据
                            encoder.releaseOutputBuffer(outputBufferIndex, false)
                        } else {
                            // 处理未知的负值返回
                            Log.e(TAG, "Unexpected outputBufferIndex: $outputBufferIndex")
                            break
                        }
                    }
                }
            }
        }
    }

    /**
     * 执行实际的初始化操作。
     */
    private fun performInitialization(): Boolean {
        if (mOutputPath.isNullOrEmpty() || mWidth <= 0 || mHeight <= 0) {
            Log.e(TAG, "Invalid parameters for initialization.")
            return false
        }
        try {
            val mediaFormat = MediaFormat.createVideoFormat(MIME_TYPE, mWidth, mHeight).apply {
                setInteger(
                    MediaFormat.KEY_COLOR_FORMAT,
                    MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420Flexible
                )
                setInteger(
                    MediaFormat.KEY_BIT_RATE,
                    (mWidth * mHeight * FRAME_RATE * BIT_FACTOR).toInt()
                )
                setInteger(MediaFormat.KEY_FRAME_RATE, FRAME_RATE)
                setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, IFRAME_INTERVAL)
            }

            mEncoder = MediaCodec.createEncoderByType(MIME_TYPE).apply {
                configure(mediaFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE)
                start()
            }

            mMediaMuxer = MediaMuxer(mOutputPath!!, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4)
            return true
        } catch (e: IOException) {
            Log.e(TAG, "Failed to initialize video encoder", e)
            release()
            return false
        }
    }

    /**
     * 停止编码并释放所有资源。
     */
    fun stop() {
        if (!mIsInitialized) {
            Log.w(TAG, "Encoder is not running.")
            return
        }
        Log.i(TAG, "Stopping video encoder...")
        mIsInitialized = false

        sendEndOfStream()
        drainEncoder()

        try {
            mMediaMuxer?.let {
                if (mIsMuxerStarted) it.stop()
                it.release()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error stopping MediaMuxer", e)
        } finally {
            mMediaMuxer = null
        }

        try {
            mEncoder?.let {
                it.stop()
                it.release()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error stopping MediaCodec", e)
        } finally {
            mEncoder = null
        }

        // 重置状态
        mIsMuxerStarted = false
        videoTrackIndex = -1
        mOutputPath = null
        mWidth = 0
        mHeight = 0
        Log.i(TAG, "Video encoder stopped and resources released.")
    }

    private fun sendEndOfStream() {
        mEncoder?.let { encoder ->
            val inputBufferIndex = encoder.dequeueInputBuffer(10000)
            if (inputBufferIndex >= 0) {
                encoder.queueInputBuffer(
                    inputBufferIndex, 0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM
                )
            }
        }
    }

    private fun drainEncoder() {
        mEncoder?.let { encoder ->
            val bufferInfo = MediaCodec.BufferInfo()
            while (true) {
                val outputBufferIndex = encoder.dequeueOutputBuffer(bufferInfo, 10000)
                if (outputBufferIndex == MediaCodec.INFO_TRY_AGAIN_LATER) break
                if (outputBufferIndex < 0) continue

                if (mIsMuxerStarted) {
                    val outputBuffer = encoder.getOutputBuffer(outputBufferIndex)
                    outputBuffer?.let {
                        if ((bufferInfo.flags and MediaCodec.BUFFER_FLAG_CODEC_CONFIG) == 0) {
                            mMediaMuxer?.writeSampleData(videoTrackIndex, it, bufferInfo)
                        }
                    }
                }
                encoder.releaseOutputBuffer(outputBufferIndex, false)

                if ((bufferInfo.flags and MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) break
            }
        }
    }

    private fun release() {
        mEncoder?.release()
        mEncoder = null
        mMediaMuxer?.release()
        mMediaMuxer = null
        mIsInitialized = false
        mIsMuxerStarted = false
        videoTrackIndex = -1
    }
}