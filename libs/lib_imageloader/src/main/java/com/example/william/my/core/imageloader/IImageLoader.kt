package com.example.william.my.core.imageloader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes

interface IImageLoader {
    fun clear(context: Context?)

    fun clear(context: Context?, imageView: ImageView?)

    fun resumeRequests(context: Context?)

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
        context: Context?, imageView: ImageView?, url: String?,
    )

    fun loadImage(
        context: Context?, imageView: ImageView?, url: String?,
        @RawRes @DrawableRes errorResId: Int,
    )

    fun loadImageRound(
        context: Context?, imageView: ImageView?, url: String?,
    )

    fun loadImageRound(
        context: Context?, imageView: ImageView?, url: String?,
        @RawRes @DrawableRes errorResId: Int = 0,
    )

    fun loadImageRadius(
        context: Context?, imageView: ImageView?, url: String?, radius: Int,
    )

    fun loadImageRadius(
        context: Context?, imageView: ImageView?, url: String?, radius: Int,
        @RawRes @DrawableRes errorResId: Int = 0,
    )

    fun getImageDrawable(
        context: Context?, url: String?
    ): Drawable?

    fun getImageBitmap(
        context: Context?, url: String?
    ): Bitmap?


    fun loadGif(
        context: Context?, imageView: ImageView?, resourceId: Int,
    )

    fun loadGif(
        context: Context?, imageView: ImageView?, url: String?,
    )

    fun <R> notNull(vararg args: Any?, block: () -> R) =
        when (args.filterNotNull().size) {
            args.size -> block()
            else -> null
        }
}