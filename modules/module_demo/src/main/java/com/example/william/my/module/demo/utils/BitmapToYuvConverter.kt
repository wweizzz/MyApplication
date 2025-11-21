package com.example.william.my.module.demo.utils

import android.graphics.Bitmap
import android.graphics.Color

object BitmapToYuvConverter {

    /**
     * 将Bitmap转换为NV12格式数据
     * NV12格式：Y分量平面和UV分量交错平面 (YYYY...UVUV...)
     *
     * @return NV12格式字节数组，如果转换失败返回null
     */
    fun Bitmap.convertToNv12(): ByteArray {
        val width = this.width
        val height = this.height

        // NV12格式数据大小：Y分量占width*height字节，UV分量占width*height/2字节
        val nv12 = ByteArray(width * height * 3 / 2)

        // 获取RGB数据
        val rgba = IntArray(width * height)
        this.getPixels(rgba, 0, width, 0, 0, width, height)

        // 转换RGB到NV12
        convertRgbaToNv12(rgba, nv12, width, height)

        return nv12
    }

    /**
     * 将Bitmap转换为NV21格式数据
     * NV21格式：Y分量平面和VU分量交错平面 (YYYY...VUVU...)
     *
     * @return NV21格式字节数组，如果转换失败返回null
     */
    fun Bitmap.convertToNv21(): ByteArray {
        val width = this.width
        val height = this.height

        // NV21格式数据大小：Y分量占width*height字节，VU分量占width*height/2字节
        val nv21 = ByteArray(width * height * 3 / 2)

        // 获取RGB数据
        val rgba = IntArray(width * height)
        this.getPixels(rgba, 0, width, 0, 0, width, height)

        // 转换RGB到NV21
        convertRgbaToNv21(rgba, nv21, width, height)

        return nv21
    }

    /**
     * RGB转NV21的具体实现
     *
     * @param rgba RGB数据数组
     * @param nv21 输出的NV12数据数组
     * @param width 图像宽度
     * @param height 图像高度
     */
    private fun convertRgbaToNv21(rgba: IntArray, nv21: ByteArray, width: Int, height: Int) {
        val ySize = width * height

        var yIndex = 0               // Y平面索引 (0 ~ ySize-1)
        var vuIndex = ySize     // UV平面索引 (ySize ~ 结束)

        for (row in 0 until height) {
            for (col in 0 until width) {
                // 获取RGB值
                val pixel = rgba[width * row + col]
                val r = Color.red(pixel)
                val g = Color.green(pixel)
                val b = Color.blue(pixel)

                // 转换为YUV，并确保值在有效范围内 [0, 255]
                val y = (0.299 * r + 0.587 * g + 0.114 * b).toInt().coerceIn(0, 255)
                val u = ((-0.147 * r - 0.289 * g + 0.436 * b) + 128).toInt().coerceIn(0, 255)
                val v = ((0.615 * r - 0.515 * g - 0.100 * b) + 128).toInt().coerceIn(0, 255)

                // 存储Y分量
                nv21[yIndex++] = y.toByte()

                // 每四个像素处理一次VU分量（4:2:0采样）
                if (row % 2 == 0 && col % 2 == 0) {
                    nv21[vuIndex++] = v.toByte()  // V分量
                    nv21[vuIndex++] = u.toByte()  // U分量
                }
            }
        }
    }

    /**
     * RGB转NV12的具体实现
     *
     * @param rgba RGB数据数组
     * @param nv12 输出的NV12数据数组
     * @param width 图像宽度
     * @param height 图像高度
     */
    private fun convertRgbaToNv12(rgba: IntArray, nv12: ByteArray, width: Int, height: Int) {
        val ySize = width * height

        var yIndex = 0               // Y平面索引 (0 ~ ySize-1)
        var uvIndex = ySize     // VU平面索引 (ySize ~ 结束)

        for (row in 0 until height) {
            for (col in 0 until width) {
                // 获取RGB值
                val pixel = rgba[width * row + col]
                val r = Color.red(pixel)
                val g = Color.green(pixel)
                val b = Color.blue(pixel)

                // 转换为YUV，并确保值在有效范围内 [0, 255]
                val y = (0.299 * r + 0.587 * g + 0.114 * b).toInt().coerceIn(0, 255)
                val u = ((-0.147 * r - 0.289 * g + 0.436 * b) + 128).toInt().coerceIn(0, 255)
                val v = ((0.615 * r - 0.515 * g - 0.100 * b) + 128).toInt().coerceIn(0, 255)

                // 存储Y分量
                nv12[yIndex++] = y.toByte()

                // 每四个像素处理一次UV分量（4:2:0采样）
                if (row % 2 == 0 && col % 2 == 0) {
                    nv12[uvIndex++] = u.toByte()
                    nv12[uvIndex++] = v.toByte()
                }
            }
        }
    }

    private fun convertRgbaToNv12HighQuality(
        rgba: IntArray,
        nv21: ByteArray,
        width: Int,
        height: Int
    ): ByteArray {
        val ySize = width * height

        var yIndex = 0               // Y平面索引 (0 ~ ySize-1)
        var uvIndex = ySize     // VU平面索引 (ySize ~ 结束)

        // 填充 Y 分量
        for (row in 0 until height) {
            for (col in 0 until width) {
                val pixel = rgba[row * width + col]
                val r = (pixel shr 16) and 0xFF
                val g = (pixel shr 8) and 0xFF
                val b = pixel and 0xFF

                // BT.601 标准 Y 分量
                val yVal = (((66 * r + 129 * g + 25 * b + 128) shr 8) + 16).coerceIn(0, 255)
                nv21[yIndex++] = yVal.toByte()
            }
        }

        // 填充 UV 分量（NV21：VU 交错）
        for (row in 0 until height step 2) {
            for (col in 0 until width step 2) {

                var sumU = 0
                var sumV = 0

                // 采样 2x2 区域的四个点
                val p1 = rgba[row * width + col]
                val p2 = rgba[row * width + col + 1]
                val p3 = rgba[(row + 1) * width + col]
                val p4 = rgba[(row + 1) * width + col + 1]

                // 计算四个点的 U 和 V 值并求和
                for (p in listOf(p1, p2, p3, p4)) {
                    val r = (p shr 16) and 0xFF
                    val g = (p shr 8) and 0xFF
                    val b = p and 0xFF

                    // BT.601 标准 VU 分量
                    sumU += (((-38 * r - 74 * g + 112 * b + 128) shr 8) + 128).coerceIn(0, 255)
                    sumV += (((112 * r - 94 * g - 18 * b + 128) shr 8) + 128).coerceIn(0, 255)
                }

                // 计算平均值并写入
                val u = sumU / 4
                val v = sumV / 4

                nv21[uvIndex++] = u.toByte() // U
                nv21[uvIndex++] = v.toByte() // V
            }
        }
        return nv21
    }

    private fun convertRgbaToNv21HighQuality(
        rgba: IntArray,
        nv21: ByteArray,
        width: Int,
        height: Int
    ): ByteArray {
        val ySize = width * height

        var yIndex = 0               // Y平面索引 (0 ~ ySize-1)
        var uvIndex = ySize     // VU平面索引 (ySize ~ 结束)

        // 填充 Y 分量
        for (row in 0 until height) {
            for (col in 0 until width) {
                val pixel = rgba[row * width + col]
                val r = (pixel shr 16) and 0xFF
                val g = (pixel shr 8) and 0xFF
                val b = pixel and 0xFF

                // BT.601 标准 Y 分量
                val yVal = (((66 * r + 129 * g + 25 * b + 128) shr 8) + 16).coerceIn(0, 255)
                nv21[yIndex++] = yVal.toByte()
            }
        }

        // 填充 UV 分量（NV21：VU 交错）
        for (row in 0 until height step 2) {
            for (col in 0 until width step 2) {

                var sumU = 0
                var sumV = 0

                // 采样 2x2 区域的四个点
                val p1 = rgba[row * width + col]
                val p2 = rgba[row * width + col + 1]
                val p3 = rgba[(row + 1) * width + col]
                val p4 = rgba[(row + 1) * width + col + 1]

                // 计算四个点的 U 和 V 值并求和
                for (p in listOf(p1, p2, p3, p4)) {
                    val r = (p shr 16) and 0xFF
                    val g = (p shr 8) and 0xFF
                    val b = p and 0xFF

                    // BT.601 标准 VU 分量
                    sumU += (((-38 * r - 74 * g + 112 * b + 128) shr 8) + 128).coerceIn(0, 255)
                    sumV += (((112 * r - 94 * g - 18 * b + 128) shr 8) + 128).coerceIn(0, 255)
                }

                // 计算平均值并写入
                val u = sumU / 4
                val v = sumV / 4

                nv21[uvIndex++] = v.toByte() // V
                nv21[uvIndex++] = u.toByte() // U
            }
        }
        return nv21
    }
}