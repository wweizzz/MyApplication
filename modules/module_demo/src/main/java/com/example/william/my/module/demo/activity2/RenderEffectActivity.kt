package com.example.william.my.module.demo.activity2

import android.graphics.Bitmap
import android.graphics.HardwareRenderer
import android.graphics.PixelFormat
import android.graphics.RenderEffect
import android.graphics.RenderNode
import android.graphics.Shader
import android.hardware.HardwareBuffer
import android.media.Image
import android.media.ImageReader
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.R
import com.example.william.my.basic.basic_module.activity.BasicImageActivity
import com.example.william.my.basic.basic_module.router.path.RouterPath
import kotlin.math.max
import kotlin.math.min

/**
 * https://developer.android.com/guide/topics/renderscript
 */
@Route(path = RouterPath.Demo.RenderEffect)
class RenderEffectActivity : BasicImageActivity() {

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        val drawable = ResourcesCompat.getDrawable(
            resources,
            R.drawable.ic_launcher,
            null
        )!!

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            //buildRenderEffect(mBinding.basicsImage, 10f)
            mBinding.basicsImage.setImageBitmap(buildRenderEffect(drawable.toBitmap(), 10f))
        }
    }

    /**
     * @see Shader.TileMode.CLAMP // 边缘自然过渡，首选
     * @see Shader.TileMode.REPEAT // 边缘产生重复条纹，应避免
     * @see Shader.TileMode.MIRROR // 边缘产生镜像图案，不常用
     */
    @RequiresApi(Build.VERSION_CODES.S)
    private fun buildRenderEffect(view: View, radius: Float) {
        val blurRenderEffect = RenderEffect.createBlurEffect(radius, radius, Shader.TileMode.CLAMP)
        view.setRenderEffect(blurRenderEffect)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun buildRenderEffect(bitmap: Bitmap, radius: Float): Bitmap {

        // 限制模糊半径在0.1到25之间
        var radius = radius
        radius = max(0.1f, min(25.0f, radius))

        val imageReader = ImageReader.newInstance(
            bitmap.width,
            bitmap.height,
            PixelFormat.RGBA_8888,
            1,
            HardwareBuffer.USAGE_GPU_SAMPLED_IMAGE or HardwareBuffer.USAGE_GPU_COLOR_OUTPUT
        )

        // 创建 RenderNode 和 HardwareRenderer
        val renderNode = RenderNode("BlurEffect")
        val hardwareRenderer = HardwareRenderer()

        hardwareRenderer.setSurface(imageReader.surface)
        hardwareRenderer.setContentRoot(renderNode)
        renderNode.setPosition(0, 0, imageReader.width, imageReader.height)

        // 创建模糊效果
        val blurRenderEffect = RenderEffect.createBlurEffect(
            radius, radius, Shader.TileMode.CLAMP
        )
        renderNode.setRenderEffect(blurRenderEffect)

        // 记录绘制操作
        val renderCanvas = renderNode.beginRecording()
        renderCanvas.drawBitmap(bitmap, 0f, 0f, null)
        renderNode.endRecording()

        // 渲染并等待完成
        hardwareRenderer.createRenderRequest().setWaitForPresent(true).syncAndDraw()

        // 获取渲染后的图像
        val image: Image = imageReader.acquireNextImage()
            ?: throw RuntimeException("No Image")
        val hardwareBuffer: HardwareBuffer = image.hardwareBuffer
            ?: throw RuntimeException("No HardwareBuffer")

        // 将 HardwareBuffer 包装成 Bitmap
        val resultBitmap = Bitmap.wrapHardwareBuffer(hardwareBuffer, null)
            ?: throw RuntimeException("Create Bitmap Failed")

        // 清理资源
        image.close()

        return resultBitmap
    }
}