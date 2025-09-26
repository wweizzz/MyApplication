package com.example.william.my.module.demo.utils

import android.graphics.Bitmap
import androidx.core.graphics.scale
import kotlin.math.max
import kotlin.math.min

object YuvUtils {

    fun convertBitmapToYUV420(bitmap: Bitmap, width: Int, height: Int): ByteArray {
        // 获取缩放后的Bitmap（保持原始比例避免变形）
        val scaledBitmap = if (bitmap.width != width || bitmap.height != height) {
            bitmap.scale(width, height)
        } else {
            bitmap
        }

        // YUV420SP (NV21) 格式：Y平面 + VU交错平面
        val yuv = ByteArray(width * height * 3 / 2)

        // 获取 ARGB_8888 格式像素数据
        val pixels = IntArray(width * height)
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)

        var yIndex = 0         // Y平面索引 (0 ~ ySize-1)
        var uvIndex = width * height    // UV平面索引 (ySize ~ 结束)

        // 填充 Y 分量
        for (y in 0 until height) {
            for (x in 0 until width) {
                val pixel = pixels[y * width + x]
                val r = (pixel shr 16) and 0xFF
                val g = (pixel shr 8) and 0xFF
                val b = pixel and 0xFF

                // BT.601 标准 Y 分量
                val yVal = ((66 * r + 129 * g + 25 * b + 128) shr 8) + 16
                yuv[yIndex++] = max(0, min(255, yVal)).toByte()
            }
        }

        // 填充 UV 分量（NV21：VU 交错）
        for (y in 0 until height step 2) {
            for (x in 0 until width step 2) {
                var sumU = 0
                var sumV = 0
                var count = 0

                // 处理2x2像素块
                for (dy in 0 until min(2, height - y)) {
                    for (dx in 0 until min(2, width - x)) {
                        val px = x + dx
                        val py = y + dy
                        val pixel = pixels[py * width + px]
                        val r = (pixel shr 16) and 0xFF
                        val g = (pixel shr 8) and 0xFF
                        val b = pixel and 0xFF

                        // BT.601标准UV计算
                        sumU += ((-38 * r - 74 * g + 112 * b + 128) shr 8) + 128
                        sumV += ((112 * r - 94 * g - 18 * b + 128) shr 8) + 128
                        count++
                    }
                }

                if (count > 0) {
                    // 计算平均值并钳制值域
                    val u = max(0, min(255, sumU / count))
                    val v = max(0, min(255, sumV / count))

                    // NV21格式: VU交错存储
                    yuv[uvIndex++] = v.toByte() // V分量
                    yuv[uvIndex++] = u.toByte() // U分量
                }
            }
        }

//        for (j in 0 until height) {
//            for (i in 0 until width) {
//                val pixel = pixels[j * width + i]
//                val r = (pixel shr 16) and 0xFF
//                val g = (pixel shr 8) and 0xFF
//                val b = pixel and 0xFF
//
//                // BT.601 标准 Y 分量
//                val y = ((66 * r + 129 * g + 25 * b + 128) shr 8) + 16
//                yuv[yIndex++] = max(0, min(255, y)).toByte()
//
//                // 每隔 2x2 像素写入 UV（NV21: VU 交错）
//                if (j % 2 == 0 && i % 2 == 0) {
//                    // 当前像素作为 UV 的采样点
//                    val u = ((-38 * r - 74 * g + 112 * b + 128) shr 8) + 128
//                    val v = ((112 * r - 94 * g - 18 * b + 128) shr 8) + 128
//                    yuv[uvIndex++] = max(0, min(255, v)).toByte()
//                    yuv[uvIndex++] = max(0, min(255, u)).toByte()
//                }
//            }
//        }

        if (scaledBitmap != bitmap) {
            scaledBitmap.recycle()
        }
        return yuv
    }
}