package com.example.william.my.module.demo.activity2

import android.graphics.Bitmap
import android.os.Bundle
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.createBitmap
import androidx.core.graphics.drawable.toBitmap
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicImageActivity
import com.example.william.my.basic.basic_module.router.path.RouterPath
import kotlin.math.max
import kotlin.math.min

@Route(path = RouterPath.Demo.RenderScript)
class RenderScriptActivity : BasicImageActivity() {

    private var renderScript: RenderScript? = null

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        val drawable = ResourcesCompat.getDrawable(
            resources,
            com.example.william.my.basic.basic_module.R.drawable.ic_launcher,
            null
        )!!
        mBinding.basicsImage.setImageBitmap(buildRenderScript(drawable.toBitmap(), 10f))
    }

    private fun buildRenderScript(bitmap: Bitmap, radius: Float): Bitmap {
        // 初始化RenderScript
        renderScript = RenderScript.create(this);

        // 限制模糊半径在0.1到25之间
        var radius = radius
        radius = max(0.1f, min(25.0f, radius))

        // 创建输出Bitmap
        val output = createBitmap(bitmap.getWidth(), bitmap.getHeight())

        // 创建RenderScript的输入输出分配
        val input = Allocation.createFromBitmap(renderScript, bitmap)
        val outputAlloc = Allocation.createFromBitmap(renderScript, output)

        // 创建模糊脚本
        val blurScript = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))

        // 设置输入和模糊半径
        blurScript.setInput(input)
        blurScript.setRadius(radius)

        // 执行模糊
        blurScript.forEach(outputAlloc)

        // 将结果复制到输出Bitmap
        outputAlloc.copyTo(output)

        return output
    }
}