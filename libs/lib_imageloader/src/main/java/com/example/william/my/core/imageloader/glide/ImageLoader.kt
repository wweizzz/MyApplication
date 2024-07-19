package com.example.william.my.core.imageloader.glide

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.william.my.core.imageloader.IImageLoader
import java.util.concurrent.ExecutionException

/**
 * https://bumptech.github.io/glide
 * https://github.com/bumptech/glide
 */
object ImageLoader : IImageLoader {

    override fun pauseRequests(context: Context?) {
        notNull(context) {
            Glide.with(context!!).pauseRequests()
        }
    }

    override fun resumeRequests(context: Context?) {
        notNull(context) {
            Glide.with(context!!).resumeRequests()
        }
    }

    override fun clear(context: Context?, imageView: ImageView?) {
        notNull(context, imageView) {
            Glide.with(context!!).clear(imageView!!)
        }
    }

    override fun loadImage(context: Context?, imageView: ImageView?, resourceId: Int) {
        notNull(context, imageView) {
            Glide.with(context!!)
                .load(resourceId)
                .into(imageView!!)
        }
    }

    override fun loadImageRound(
        context: Context?, imageView: ImageView?, resourceId: Int
    ) {
        notNull(context, imageView) {
            Glide.with(context!!)
                .load(resourceId)
                .transform(CircleCrop())
                .into(imageView!!)
        }
    }

    override fun loadImageRadius(
        context: Context?, imageView: ImageView?, resourceId: Int, radius: Int
    ) {
        notNull(context, imageView) {
            Glide.with(context!!)
                .load(resourceId)
                .transform(CenterCrop(), RoundedCorners(radius))
                .into(imageView!!)
        }
    }

    override fun loadImage(
        context: Context?, imageView: ImageView?, url: String?
    ) {
        notNull(context, imageView) {
            Glide.with(context!!)
                .load(url)
                .into(imageView!!)
        }
    }

    override fun loadImage(
        context: Context?, imageView: ImageView?, url: String?, errorResId: Int
    ) {
        notNull(context, imageView) {
            Glide.with(context!!)
                .load(url)
                .into(imageView!!)
        }
    }

    override fun loadImageRound(
        context: Context?, imageView: ImageView?, url: String?
    ) {
        notNull(context, imageView) {
            Glide.with(context!!)
                .load(url)
                .transform(CircleCrop())
                .into(imageView!!)
        }
    }

    override fun loadImageRound(
        context: Context?, imageView: ImageView?, url: String?, errorResId: Int
    ) {
        notNull(context, imageView) {
            Glide.with(context!!)
                .load(url)
                .error(errorResId)
                .transform(CircleCrop())
                .into(imageView!!)
        }
    }

    override fun loadImageRadius(
        context: Context?, imageView: ImageView?, url: String?, radius: Int
    ) {
        notNull(context, imageView) {
            Glide.with(context!!)
                .load(url)
                .transform(CenterCrop(), RoundedCorners(radius))
                .into(imageView!!)
        }
    }

    override fun loadImageRadius(
        context: Context?, imageView: ImageView?, url: String?, radius: Int, errorResId: Int
    ) {
        notNull(context, imageView) {
            Glide.with(context!!)
                .load(url)
                .error(errorResId)
                .transform(CenterCrop(), RoundedCorners(radius))
                .into(imageView!!)
        }
    }


    override fun getImageDrawable(context: Context?, url: String?): Drawable? {
        if (TextUtils.isEmpty(url)) {
            return null
        }
        notNull(context) {
            try {
                return@notNull Glide.with(context!!).load(url).submit().get()
            } catch (e: ExecutionException) {
                e.printStackTrace()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        return null
    }

    override fun getImageBitmap(context: Context?, url: String?): Bitmap? {
        if (TextUtils.isEmpty(url)) {
            return null
        }
        notNull(context) {
            try {
                return@notNull Glide.with(context!!).asBitmap().load(url).submit().get()
            } catch (e: ExecutionException) {
                e.printStackTrace()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        return null
    }

    override fun loadGif(context: Context?, imageView: ImageView?, resourceId: Int) {

    }

    override fun loadGif(context: Context?, imageView: ImageView?, url: String?) {

    }

}