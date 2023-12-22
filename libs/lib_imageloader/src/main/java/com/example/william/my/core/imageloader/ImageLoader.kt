package com.example.william.my.core.imageloader

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader.TileMode
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.request.RequestOptions
import com.example.william.my.core.imageloader.module.GlideApp
import java.security.MessageDigest
import java.util.concurrent.ExecutionException

/**
 * https://bumptech.github.io/glide
 * https://github.com/bumptech/glide
 */
object ImageLoader {

    fun clear(context: Context?) {
        notNull(context) {
            GlideApp.with(context!!).pauseRequests()
        }
    }

    fun clear(context: Context?, imageView: ImageView?) {
        notNull(context, imageView) {
            GlideApp.with(context!!).clear(imageView!!)
        }
    }

    fun loadImage(
        context: Context?, imageView: ImageView?,
        @RawRes @DrawableRes resourceId: Int,
    ) {
        notNull(context, imageView) {
            GlideApp.with(context!!).load(resourceId).into(imageView!!)
        }
    }

    fun loadImage(
        context: Context?, imageView: ImageView?, url: String?,
    ) {
        loadImage(context, imageView, url, 0, 0f)
    }

    fun loadImage(
        context: Context?, imageView: ImageView?, url: String?,
        @DrawableRes errorResId: Int,
    ) {
        loadImage(context, imageView, url, errorResId, 0f)
    }

    fun loadImageRadius(
        context: Context?, imageView: ImageView?, url: String?, radius: Float
    ) {
        loadImage(context, imageView, url, 0, radius)
    }

    fun loadImageRadius(
        context: Context?, imageView: ImageView?, url: String?,
        @DrawableRes errorResId: Int, radius: Float,
    ) {
        loadImage(
            context, imageView, url, errorResId, radius
        )
    }

    private fun loadImage(
        context: Context?, imageView: ImageView?, url: String?,
        @DrawableRes errorResId: Int, radius: Float,
    ) {
        if (TextUtils.isEmpty(url)) {
            if (errorResId != 0) {
                imageView?.setImageResource(errorResId)
            }
            return
        }
        notNull(context, imageView) {
            GlideApp.with(context!!)
                .load(url)
                .error(loadTransform(context, errorResId, radius))
                .into(imageView!!)
        }
    }

    private fun loadTransform(
        context: Context?, @DrawableRes placeholderId: Int, radius: Float,
    ): RequestBuilder<Drawable?> {
        return GlideApp.with(context!!).load(placeholderId)
            .apply(RequestOptions().centerCrop().transform(GlideRoundTransform(context, radius)))
    }

    fun getImageBitmap(
        context: Context?, url: String?, width: Int, height: Int,
    ): Bitmap? {
        if (TextUtils.isEmpty(url)) {
            return null
        }
        notNull(context) {
            try {
                return GlideApp.with(context!!)
                    .asBitmap()
                    .load(url)
                    .submit(width, height)
                    .get()
            } catch (e: ExecutionException) {
                e.printStackTrace()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        return null
    }

    fun loadImageThumbnail(
        context: Context?, imageView: ImageView?, url: String?,
        @DrawableRes resourceId: Int, radius: Int,
    ) {
        notNull(context, imageView) {
            GlideApp.with(context!!)
                .load(url)
                .apply(
                    RequestOptions()
                        .placeholder(resourceId)
                        .error(resourceId)
                        .centerCrop()
                        .transform(GlideRoundTransform(imageView?.context, radius.toFloat()))
                )
                .thumbnail(loadTransform(imageView?.context, resourceId, radius.toFloat()))
                .into(imageView!!)
        }
    }

    fun loadGifImage(
        context: Context?, imageView: ImageView?, url: String?,
    ) {
        loadGifImage(context, imageView, url, 0)
    }

    fun loadGifImage(
        context: Context?, imageView: ImageView?, url: String?,
        @DrawableRes errorResId: Int,
    ) {
        if (TextUtils.isEmpty(url)) {
            if (errorResId != 0) {
                imageView?.setImageResource(errorResId)
            }
            return
        }
        notNull(context, imageView) {
            GlideApp.with(context!!)
                .asGif()
                .load(url)
                .into(imageView!!)
        }
    }

    private inline fun <R> notNull(vararg args: Any?, block: () -> R) =
        when (args.filterNotNull().size) {
            args.size -> block()
            else -> null
        }
}

class GlideRoundTransform(context: Context?, dp: Float) : BitmapTransformation() {

    private var radius = 0f

    init {
        radius = Resources.getSystem().displayMetrics.density * dp
    }

    override fun transform(
        pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int
    ): Bitmap {
        return roundCrop(pool, toTransform)!!
    }

    private fun roundCrop(pool: BitmapPool, source: Bitmap?): Bitmap? {
        if (source == null) {
            return null
        }
        val result = pool[source.width, source.height, Bitmap.Config.ARGB_8888]
        val canvas = Canvas(result)
        val paint = Paint()
        paint.shader = BitmapShader(source, TileMode.CLAMP, TileMode.CLAMP)
        paint.isAntiAlias = true
        val rectF = RectF(0f, 0f, source.width.toFloat(), source.height.toFloat())
        canvas.drawRoundRect(rectF, radius, radius, paint)
        return result
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {

    }
}