package com.example.william.my.module.demo.utils

import android.R
import android.content.Context
import android.graphics.Bitmap
import android.media.MediaCodec
import android.media.MediaCodecInfo
import android.media.MediaFormat
import android.media.MediaMuxer
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.util.Size
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.core.resolutionselector.AspectRatioStrategy
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.core.resolutionselector.ResolutionStrategy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.example.william.my.module.demo.databinding.ActivityCameraBinding
import jp.co.cyberagent.android.gpuimage.GPUImage
import jp.co.cyberagent.android.gpuimage.util.Rotation
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding
    private lateinit var cameraExecutor: ExecutorService
    private var cameraProvider: ProcessCameraProvider? = null
    private var mediaMuxer: MediaMuxer? = null
    private var mediaCodec: MediaCodec? = null
    private var trackIndex = -1
    private var isMuxerStarted = false
    private var frameCount = 0
    private var isRecording = false
    private var videoFile: File? = null
    private val frameRate = 30
    private val handlerThread = HandlerThread("VideoEncoder").apply { start() }
    private val handler = Handler(handlerThread.looper)
    private lateinit var gpuImage: GPUImage
    private var rotation = Rotation.ROTATION_90

    private var targetWidth = 640
    private var targetHeight = 480

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cameraExecutor = Executors.newSingleThreadExecutor()

        binding.btnRecord.setOnClickListener {
            if (!isRecording) {
                startRecording()
                binding.btnRecord.setImageResource(R.drawable.ic_media_pause)
            } else {
                stopRecording()
                binding.btnRecord.setImageResource(R.drawable.ic_media_play)
            }
        }

        setupCamera()
    }

    private fun setupCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(binding.previewView.surfaceProvider)
            }

            val resolutionStrategy = ResolutionSelector.Builder()
                .setResolutionStrategy(
                    ResolutionStrategy(
                        Size(targetWidth, targetHeight), // 目标分辨率
                        ResolutionStrategy.FALLBACK_RULE_CLOSEST_HIGHER_THEN_LOWER // 回退规则
                    )
                )
                .build()

            val aspectRatioStrategy = ResolutionSelector.Builder()
                .setAspectRatioStrategy(
                    AspectRatioStrategy(
                        AspectRatio.RATIO_4_3, // 目标比例
                        AspectRatioStrategy.FALLBACK_RULE_AUTO // 回退规则
                    )
                )
                .build()

            val analyzer = ImageAnalysis.Analyzer { imageProxy ->
                if (isRecording) {
                    processWithGPUImage(this, imageProxy)
                }
                imageProxy.close()
            }

            // 图像分析
            val imageAnalysis = ImageAnalysis.Builder()
                .setResolutionSelector(resolutionStrategy)
                //.setResolutionSelector(aspectRatioStrategy)
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .apply {
                    setAnalyzer(cameraExecutor, analyzer)
                }

            // 选择后置摄像头
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider?.unbindAll()
                cameraProvider?.bindToLifecycle(
                    this, cameraSelector, preview, imageAnalysis
                )
            } catch (exc: Exception) {
                Log.e(TAG, "相机绑定失败: $exc")
            }
        }, ContextCompat.getMainExecutor(this))
    }


    private fun processWithGPUImage(context: Context, imageProxy: ImageProxy) {
        val bitmap = imageProxy.toBitmap()

        handler.post {
            // 创建GPUImage实例
            val gpuImage = GPUImage(context)
            gpuImage.setImage(bitmap)

            val processedBitmap = gpuImage.bitmapWithFilterApplied

            // 编码视频帧
            encodeFrame(processedBitmap)

            bitmap.recycle()
            processedBitmap.recycle()
        }
    }


    private fun startRecording() {
        videoFile = createVideoFile()
        if (videoFile == null) {
            Toast.makeText(this, "无法创建视频文件", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            // 初始化MediaCodec (H.264编码)
            val format = MediaFormat.createVideoFormat(
                MediaFormat.MIMETYPE_VIDEO_AVC,
                targetWidth,
                targetHeight
            )
            format.setInteger(
                MediaFormat.KEY_COLOR_FORMAT,
                MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420Flexible
            )
            format.setInteger(MediaFormat.KEY_BIT_RATE, 3_000_000)
            format.setInteger(MediaFormat.KEY_FRAME_RATE, frameRate)
            format.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 1)

            mediaCodec = MediaCodec.createEncoderByType(MediaFormat.MIMETYPE_VIDEO_AVC)
            mediaCodec?.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE)
            mediaCodec?.start()

            mediaMuxer =
                MediaMuxer(videoFile!!.absolutePath, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4)

            isRecording = true
            frameCount = 0
            isMuxerStarted = false
        } catch (e: Exception) {
            Log.e(TAG, "初始化录制失败: ${e.message}", e)
            isRecording = false
            Toast.makeText(this, "录制初始化失败: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun encodeFrame(bitmap: Bitmap) {
        if (!isRecording || mediaCodec == null) return

        try {
            // 将Bitmap转换为YUV420格式
            val yuvData = YuvUtils.convertBitmapToYUV420(bitmap, targetWidth, targetHeight)

            val inputBufferId = mediaCodec!!.dequeueInputBuffer(10_000)
            if (inputBufferId < 0) {
                Log.d(TAG, "没有可用的输入缓冲区")
                return
            }

            val inputBuffer = mediaCodec!!.getInputBuffer(inputBufferId) ?: return
            inputBuffer.clear()
            inputBuffer.put(yuvData)

            val presentationTimeUs = frameCount * 1_000_000L / frameRate

            mediaCodec!!.queueInputBuffer(
                inputBufferId,
                0,
                yuvData.size,
                presentationTimeUs,
                0
            )
            frameCount++

            // 获取编码后的数据
            drainEncoder(false)
        } catch (e: Exception) {
            Log.e(TAG, "编码错误: ${e.message}", e)
        }
    }

    private fun drainEncoder(endOfStream: Boolean) {
        if (mediaCodec == null) return

        val bufferInfo = MediaCodec.BufferInfo()
        while (true) {
            val outputBufferId = mediaCodec!!.dequeueOutputBuffer(bufferInfo, 10_000)
            when {
                outputBufferId == MediaCodec.INFO_TRY_AGAIN_LATER -> {
                    if (!endOfStream) break
                }

                outputBufferId == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED -> {
                    if (isMuxerStarted) {
                        Log.e(TAG, "格式在混音器启动后更改")
                        break
                    }
                    val newFormat = mediaCodec!!.outputFormat
                    trackIndex = mediaMuxer!!.addTrack(newFormat)
                    mediaMuxer!!.start()
                    isMuxerStarted = true
                }

                outputBufferId < 0 -> {
                    Log.d(TAG, "意外的输出缓冲区ID: $outputBufferId")
                }

                else -> {
                    val outputBuffer = mediaCodec!!.getOutputBuffer(outputBufferId) ?: break

                    if ((bufferInfo.flags and MediaCodec.BUFFER_FLAG_CODEC_CONFIG) != 0) {
                        bufferInfo.size = 0
                    }

                    if (bufferInfo.size > 0 && isMuxerStarted) {
                        outputBuffer.position(bufferInfo.offset)
                        outputBuffer.limit(bufferInfo.offset + bufferInfo.size)
                        mediaMuxer!!.writeSampleData(trackIndex, outputBuffer, bufferInfo)
                    }

                    mediaCodec!!.releaseOutputBuffer(outputBufferId, false)

                    if ((bufferInfo.flags and MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) {
                        Log.d(TAG, "接收到结束流标志")
                        break
                    }
                }
            }
        }
    }

    private fun sendEndOfStream() {
        if (mediaCodec == null) return

        try {
            // 获取一个输入缓冲区
            val inputBufferId = mediaCodec!!.dequeueInputBuffer(10_000)
            if (inputBufferId < 0) {
                Log.e(TAG, "无法获取缓冲区来发送结束流标志")
                return
            }

            // 入队一个空缓冲区，并设置结束流标志
            mediaCodec!!.queueInputBuffer(
                inputBufferId,
                0,
                0,
                frameCount * 1_000_000L / frameRate,
                MediaCodec.BUFFER_FLAG_END_OF_STREAM
            )
            Log.d(TAG, "已发送结束流标志")
        } catch (e: Exception) {
            Log.e(TAG, "发送结束流标志失败: ${e.message}")
        }
    }

    private fun stopRecording() {
        isRecording = false
        handler.post {
            try {
                // 关键修复：发送结束流标志而不是调用signalEndOfInputStream
                sendEndOfStream()

                // 处理剩余的编码数据
                drainEncoder(true)

                // 释放资源
                mediaCodec?.stop()
                mediaCodec?.release()
                mediaCodec = null

                if (isMuxerStarted) {
                    mediaMuxer?.stop()
                    mediaMuxer?.release()
                }
                mediaMuxer = null

                isMuxerStarted = false

                GalleryUtils.saveVideoToGallery(this, videoFile, "Hitta") { isSuccess, message ->
                    //ToastUtils.showShort(message)
                }
            } catch (e: Exception) {
                Log.e(TAG, "停止录制失败: ${e.message}", e)
                Toast.makeText(
                    this@CameraActivity,
                    "停止录制失败: ${e.localizedMessage}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun createVideoFile(): File? {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_MOVIES)
        return try {
            File.createTempFile("VID_${timeStamp}_", ".mp4", storageDir).apply {
                createNewFile()
                Log.d(TAG, "创建视频文件: ${absolutePath}")
            }
        } catch (e: Exception) {
            Log.e(TAG, "创建视频文件失败: ${e.message}")
            null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
        handlerThread.quitSafely()
        if (isRecording) stopRecording()
    }

    companion object {
        private const val TAG = "CameraActivity"
    }
}