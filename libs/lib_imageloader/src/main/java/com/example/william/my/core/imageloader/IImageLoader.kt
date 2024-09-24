package com.example.william.my.core.imageloader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import java.io.File

interface IImageLoader {

    fun pauseRequests(context: Context?)

    fun resumeRequests(context: Context?)

    fun clear(context: Context?, imageView: ImageView?)

    fun loadImage(
        context: Context?, imageView: ImageView?, @RawRes @DrawableRes resourceId: Int,
    )

    fun loadImageRound(
        context: Context?, imageView: ImageView?, @RawRes @DrawableRes resourceId: Int,
    )

    fun loadImageRadius(
        context: Context?, imageView: ImageView?, @RawRes @DrawableRes resourceId: Int,
        radius: Int,
    )

    fun loadImage(
        context: Context?, imageView: ImageView?, file: File?,
    )

    fun loadImageRound(
        context: Context?, imageView: ImageView?, file: File?,
    )

    fun loadImageRadius(
        context: Context?, imageView: ImageView?, file: File?, radius: Int,
    )

    fun loadImage(
        context: Context?, imageView: ImageView?, url: String?,
    )

    fun loadImageRound(
        context: Context?, imageView: ImageView?, url: String?,
    )

    fun loadImageRadius(
        context: Context?, imageView: ImageView?, url: String?, radius: Int,
    )

    fun loadGif(
        context: Context?, imageView: ImageView?, resourceId: Int,
    )

    fun loadGif(
        context: Context?, imageView: ImageView?, url: String?,
    )

    fun getImageDrawable(
        context: Context?, url: String?
    ): Drawable?

    fun getImageBitmap(
        context: Context?, url: String?
    ): Bitmap?

    fun <R> notNull(vararg args: Any?, block: () -> R) =
        when (args.filterNotNull().size) {
            args.size -> block()
            else -> null
        }
}