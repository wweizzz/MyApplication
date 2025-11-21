package com.example.william.my.module.demo.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
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
import com.example.william.my.lib.utils.AppExecutorsHelper
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

    private val main = AppExecutorsHelper.main()
    private val diskIO = AppExecutorsHelper.diskIO()

    // 录像相关对象
    private var recordingFile: File? = null
    private var onRecordingStopped: ((file: File) -> Unit)? = null

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

    fun startRecording(activity: FragmentActivity, processComplete: (file: File) -> Unit) {
        val imageAnalysis = imageAnalysisUseCase ?: return

        if (isRecording) {
            println("录像已在进行中")
            return
        }
        isRecording = true

        recordingFile = createVideoFile(activity)
        if (recordingFile == null) {
            println("文件创建失败")
            return
        }

        onRecordingStopped = processComplete

        imageAnalysis.setAnalyzer(
            Executors.newSingleThreadExecutor()
        ) { image ->
            if (isRecording) {
                processWithGPUImage(image)
            }
            image.close()
        }
    }

    fun stopRecording() {
        if (!isRecording) {
            println("当前没有在录像")
            return
        }

        isRecording = false

        // 停止图像分析
        imageAnalysisUseCase?.clearAnalyzer()

        // 停止编码器
        diskIO.execute {
            VideoEncoder.stop()

            // 通知回调
            onRecordingStopped?.invoke(recordingFile!!)

            // 重置状态
            recordingFile = null
            onRecordingStopped = null
        }
    }

    private fun processWithGPUImage(
        context: Context,
        imageProxy: ImageProxy,
        processComplete: (bitmap: Bitmap) -> Unit
    ) {
        // 将ImageProxy转换为Bitmap数据
        val bitmap = imageProxy.toBitmap()

        // 创建GPUImage实例
        val gpuImage = GPUImage(context)
        gpuImage.setImage(bitmap)

        main.execute {
            // 获取处理后的Bitmap
            val processedBitmap = gpuImage.bitmapWithFilterApplied

            processComplete.invoke(processedBitmap)
        }
    }

    private fun processWithGPUImage(
        imageProxy: ImageProxy
    ) {
        // 将ImageProxy转换为YUV数据
        //val yuvData = imageProxyToYuv420(imageProxy)

        // 计算时间戳
        //val presentationTimeUs = frameCount * 1_000_000L / frameRate
        //frameCount++

        // 提交到编码器
        //diskIO.execute {
        //    VideoEncoder.encodeFrame(
        //        recordingFile!!.absolutePath,
        //        yuvData, presentationTimeUs,
        //        400,
        //        300
        //    )
        //}
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

    /**
     * 存储新捕获图像的选项。
     * Options to store the newly captured image.
     */
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