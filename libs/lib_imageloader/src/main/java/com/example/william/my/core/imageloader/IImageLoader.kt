package com.example.william.my.core.imageloader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import com.bumptech.glide.request.RequestOptions
import java.io.File

interface IImageLoader {

    fun pauseRequests(context: Context?)

    fun resumeRequests(context: Context?)

    fun ImageView.clear(
        context: Context?,
    )

    fun ImageView.loadImage(
        context: Context?, @RawRes @DrawableRes resourceId: Int,
    )

    fun ImageView.loadImage(
        context: Context?, uri: Uri,
    )

    fun ImageView.loadImage(
        context: Context?, file: File,
    )

    fun ImageView.loadImage(
        context: Context?,
        url: String?,
        options: RequestOptions? = null,
        requestListener: (() -> Unit)? = null,
    )

    fun ImageView.loadImageRound(
        context: Context?, @RawRes @DrawableRes resourceId: Int,
    )

    fun ImageView.loadImageRound(
        context: Context?, uri: Uri,
    )

    fun ImageView.loadImageRound(
        context: Context?, file: File,
    )

    fun ImageView.loadImageRound(
        context: Context?, url: String?,
    )

    fun ImageView.loadImageRadius(
        context: Context?, @RawRes @DrawableRes resourceId: Int, radius: Int,
    )

    fun ImageView.loadImageRadius(
        context: Context?, uri: Uri, radius: Int,
    )

    fun ImageView.loadImageRadius(
        context: Context?, file: File, radius: Int,
    )

    fun ImageView.loadImageRadius(
        context: Context?, url: String?, radius: Int,
    )

    fun ImageView.loadGif(
        context: Context?, resourceId: Int,
    )

    fun ImageView.loadGif(
        context: Context?, url: String?,
    )

    fun getImageDrawable(
        context: Context?, url: String?
    ): Drawable?

    fun getImageBitmap(
        context: Context?, url: String?
    ): Bitmap?
}