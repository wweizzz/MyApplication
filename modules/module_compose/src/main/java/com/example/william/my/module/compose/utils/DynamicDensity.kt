package com.example.william.my.module.compose.utils

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/**
 * 根据UI设计图得出动态密度适配不同屏幕
 * @param designWidth 填入UI设计图的屏幕短边dp值（绝对宽度）
 * @param designHeight 填入UI设计图的屏幕长边dp值（绝对高度）
 */
@Composable
fun dynamicDensity(designWidth: Float, designHeight: Float): Float {

    val isPortrait =
        LocalContext.current.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT  //判断横竖屏
    val displayMetrics =
        LocalContext.current.resources.displayMetrics

    val widthPixels = displayMetrics.widthPixels    //屏幕短边像素（绝对宽度）
    val heightPixels = displayMetrics.heightPixels  //屏幕长边像素（绝对高度）

    return if (isPortrait) widthPixels / designWidth else heightPixels / designHeight //计算密度
}