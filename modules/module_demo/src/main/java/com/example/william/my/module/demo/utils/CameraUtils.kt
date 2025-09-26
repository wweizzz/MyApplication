package com.example.william.my.module.demo.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.media.MediaCodec
import android.media.MediaCodecInfo
import android.media.MediaFormat
import android.media.MediaMuxer
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Size
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.core.resolutionselector.AspectRatioStrategy
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.core.resolutionselector.ResolutionStrategy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.william.my.basic.basic_module.utils.Utils
import jp.co.cyberagent.android.gpuimage.GPUImage
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.Executors

object CameraUtils {

    private const val TAG = "CameraUtils"

    // 尺寸配置
    private var targetWidth = 640
    private var targetHeight = 480

    // 状态标志
    private var isRecording = false
    private var muxerStarted = false

    private val handler = Handler(Looper.getMainLooper())

    // 录像相关对象
    private var recordingFile: File? = null
    private var previewBitmap: Bitmap? = null
    private var onRecordingStopped: ((file: File, preview: Bitmap?) -> Unit)? = null

    // 图像捕获
    private var imageCaptureUseCase: ImageCapture? = null

    // 图像分析
    private var imageAnalysisUseCase: ImageAnalysis? = null

    fun setupCamera(activity: FragmentActivity, preview: PreviewView) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(activity)

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            // 预览配置
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(preview.surfaceProvider)
                }

            // 图像捕获配置
            imageCaptureUseCase = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build()

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

            // 图像分析
            imageAnalysisUseCase = ImageAnalysis.Builder()
                .setResolutionSelector(resolutionStrategy)
                //.setResolutionSelector(aspectRatioStrategy)
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            // 选择后置摄像头
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // 解除所有绑定
                cameraProvider.unbindAll()

                // 绑定用例到相机
                cameraProvider.bindToLifecycle(
                    activity, cameraSelector, preview, imageCaptureUseCase, imageAnalysisUseCase
                )
            } catch (e: Exception) {
                println(e.message)
            }
        }, ContextCompat.getMainExecutor(activity))
    }

    fun captureImage(activity: FragmentActivity, processComplete: (bitmap: Bitmap) -> Unit) {
        val imageCapture = imageCaptureUseCase ?: return

        // 保存原始相机图片
//        imageCapture.takePicture(
//            getOutputFileOptions(activity),
//            ContextCompat.getMainExecutor(activity),
//            object : ImageCapture.OnImageSavedCallback {
//                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
//                    val uri = outputFileResults.savedUri ?: return
//
//                    // 从URI获取Bitmap
//                    //val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
//                    val bitmap = activity.contentResolver.openInputStream(uri).use { inputStream ->
//                        BitmapFactory.decodeStream(inputStream)
//                    }
//
//                    // 使用GPUImage处理
//                    processWithGPUImage(activity, bitmap, processComplete)
//                }
//
//                override fun onError(exception: ImageCaptureException) {
//
//                }
//            }
//        )

        // 内存回调（不保存原始相机图片）
        imageCapture.takePicture(
            ContextCompat.getMainExecutor(activity),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {

                    processWithGPUImage(activity, image, processComplete)

                    image.close()
                }

                override fun onError(exception: ImageCaptureException) {

                }
            }
        )
    }

    fun startRecording(activity: FragmentActivity) {
        if (isRecording) {
            println("Recording is already in progress")
            return
        }

        val imageAnalysis = imageAnalysisUseCase ?: return

        imageAnalysis.setAnalyzer(
            Executors.newSingleThreadExecutor(),
            object : ImageAnalysis.Analyzer {
                override fun analyze(image: ImageProxy) {

                }
            })

        try {
            // 1. 配置 MediaFormat
            val mediaFormat = MediaFormat.createVideoFormat(
                MediaFormat.MIMETYPE_VIDEO_AVC,
                targetWidth,
                targetHeight
            )
            mediaFormat.setInteger(
                MediaFormat.KEY_COLOR_FORMAT,
                MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface
            )
            mediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, BIT_RATE)
            mediaFormat.setInteger(MediaFormat.KEY_FRAME_RATE, FRAME_RATE)
            mediaFormat.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, IFRAME_INTERVAL)

            // 2. 创建编码器
            mediaCodec = MediaCodec.createEncoderByType(MediaFormat.MIMETYPE_VIDEO_AVC)
            mediaCodec?.configure(mediaFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE)
            mediaCodec?.start()

            // 3. 创建混合器
            mediaMuxer =
                MediaMuxer(videoFile!!.absolutePath, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4)

        } catch (e: IOException) {
            println("Error encoding video", e)
        } finally {
            // 6. 释放资源
            mediaCodec?.stop()
            mediaCodec?.release()

            if (muxerStarted) {
                mediaMuxer?.stop()
                mediaMuxer?.release()
            }
        }
    }

    private fun processWithGPUImage(
        context: Context,
        imageProxy: ImageProxy,
        processComplete: (bitmap: Bitmap) -> Unit
    ) {
        val bitmap = imageProxy.toBitmap()

        // 创建GPUImage实例
        val gpuImage = GPUImage(context)
        gpuImage.setImage(bitmap)

        handler.post {
            // 获取处理后的Bitmap
            val processedBitmap = gpuImage.bitmapWithFilterApplied

            processComplete.invoke(processedBitmap)
        }
    }

    private fun processWithGPUImage(
        context: Context,
        imageProxy: ImageProxy,
    ) {
        val bitmap = imageProxy.toBitmap()

        // 创建GPUImage实例
        val gpuImage = GPUImage(context)
        gpuImage.setImage(bitmap)

        handler.post {

            // 获取处理后的Bitmap
            val processedBitmap = gpuImage.bitmapWithFilterApplied

            // 编码视频帧
            //encodeFrame(processedBitmap)
        }
    }

    private const val BIT_RATE = 3_000_000 // 比特率
    private const val FRAME_RATE = 30 // 帧率
    private const val IFRAME_INTERVAL = 1 // 关键帧间隔

    private var videoFile: File? = null
    private var mediaCodec: MediaCodec? = null
    private var mediaMuxer: MediaMuxer? = null

    private fun start(context: Context) {
        videoFile = createVideoFile(context)
        if (videoFile == null) return

        try {
            // 1. 配置 MediaFormat
            val mediaFormat = MediaFormat.createVideoFormat(
                MediaFormat.MIMETYPE_VIDEO_AVC,
                targetWidth,
                targetHeight
            )
            mediaFormat.setInteger(
                MediaFormat.KEY_COLOR_FORMAT,
                MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface
            )
            mediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, BIT_RATE)
            mediaFormat.setInteger(MediaFormat.KEY_FRAME_RATE, FRAME_RATE)
            mediaFormat.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, IFRAME_INTERVAL)

            // 2. 创建编码器
            mediaCodec = MediaCodec.createEncoderByType(MediaFormat.MIMETYPE_VIDEO_AVC)
            mediaCodec?.configure(mediaFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE)
            mediaCodec?.start()

            // 3. 创建混合器
            mediaMuxer =
                MediaMuxer(
                    videoFile!!.absolutePath,
                    MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4
                )

//            // 4. 编码循环
//            val frameDuration = (1000000 / FRAME_RATE).toLong() // 微秒
//            var presentationTimeUs: Long = 0
//
//            for (bitmap in bitmaps) {
//                // 等待输入缓冲区可用
//                val inputBufferId = mediaCodec.dequeueInputBuffer(TIMEOUT_US.toLong())
//                if (inputBufferId >= 0) {
//
//                    // 将 Bitmap 转换为 YUV 格式
//                    val inputBuffer = mediaCodec.getInputBuffer(inputBufferId)
//                    convertBitmapToYUV(bitmap, inputBuffer!!, width, height)
//
//                    // 提交到编码器
//                    mediaCodec.queueInputBuffer(
//                        inputBufferId,
//                        0,
//                        width * height * 3 / 2,  // YUV420 数据大小
//                        presentationTimeUs,
//                        0
//                    )
//                    presentationTimeUs += frameDuration
//
//                }
//
//                // 处理输出
//                val bufferInfo = MediaCodec.BufferInfo()
//                val outputBufferId =
//                    mediaCodec.dequeueOutputBuffer(bufferInfo, TIMEOUT_US.toLong())
//                if (outputBufferId >= 0) {
//                    // 首次获取格式信息时会返回特殊值
//                    if ((bufferInfo.flags and MediaCodec.BUFFER_FLAG_CODEC_CONFIG) != 0) {
//                        bufferInfo.size = 0
//                    }
//
//                    if (bufferInfo.size > 0) {
//                        if (!muxerStarted) {
//                            val outputFormat = mediaCodec.getOutputFormat()
//                            trackIndex = mediaMuxer.addTrack(outputFormat)
//                            mediaMuxer.start()
//                            muxerStarted = true
//                        }
//
//                        val outputBuffer = mediaCodec.getOutputBuffer(outputBufferId)
//                        mediaMuxer.writeSampleData(trackIndex, outputBuffer!!, bufferInfo)
//                    }
//                    mediaCodec.releaseOutputBuffer(outputBufferId, false)
//                }
//            }
//
//            // 5. 结束编码
//            signalEndOfStream(mediaCodec, mediaMuxer, muxerStarted, trackIndex)
        } catch (e: IOException) {
            println("Error encoding video", e)
        } finally {
            // 6. 释放资源
            mediaCodec?.stop()
            mediaCodec?.release()

            if (muxerStarted) {
                mediaMuxer?.stop()
                mediaMuxer?.release()
            }
        }
    }

    private fun createVideoFile(context: Context): File? {
        val timeStamp =
            SimpleDateFormat("yyyy_MM_dd-HH_mm_ss", Locale.getDefault()).format(Date())
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_MOVIES)
        return try {
            File.createTempFile("VID_${timeStamp}_", ".mp4", storageDir).apply {
                createNewFile()
                println("创建视频文件: $absolutePath")
            }
        } catch (e: Exception) {
            println("创建视频文件失败: ${e.message}")
            null
        }
    }

    private fun getOutputFileOptions(context: Context): ImageCapture.OutputFileOptions {
        // 创建临时文件选项
        return ImageCapture.OutputFileOptions
            .Builder(
                context.contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                ContentValues().apply {
                    put(
                        MediaStore.Images.Media.DISPLAY_NAME,
                        "camera_capture_${System.currentTimeMillis()}.jpg"
                    )
                })
            .build()
    }

    private fun println(msg: String, e: IOException? = null) {
        Utils.logcat("CameraUtils", msg)
    }
}