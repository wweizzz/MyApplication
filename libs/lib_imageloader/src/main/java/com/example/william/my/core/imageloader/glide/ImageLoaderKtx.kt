package com.example.william.my.core.imageloader.glide

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.william.my.core.imageloader.IImageLoaderKtx
import com.example.william.my.core.imageloader.glide.ImageLoaderKtx.loadImage
import java.io.File
import java.util.concurrent.ExecutionException

/**
 * https://bumptech.github.io/glide
 * https://github.com/bumptech/glide
 */
object ImageLoaderKtx : IImageLoaderKtx {

    override fun pauseRequests(context: Context?) {
        context?.let {
            Glide.with(it)
                .pauseRequests()
        }
    }

    override fun resumeRequests(context: Context?) {
        context?.let {
            Glide.with(it)
                .pauseRequests()
        }
    }

    override fun ImageView.clear(context: Context?) {
        context?.let {
            Glide.with(it)
                .clear(this)
        }
    }


    override fun ImageView.loadImage(context: Context?, resourceId: Int) {
        context?.let {
            Glide.with(it)
                .load(resourceId)
                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))//禁用缓存功能
                .into(this)
        }
    }

    override fun ImageView.loadImageRound(
        context: Context?, resourceId: Int
    ) {
        context?.let {
            Glide.with(it)
                .load(resourceId)
                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))//禁用缓存功能
                .transform(CircleCrop())
                .into(this)
        }
    }

    override fun ImageView.loadImageRadius(
        context: Context?, resourceId: Int, radius: Int
    ) {
        context?.let {
            Glide.with(it)
                .load(resourceId)
                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))//禁用缓存功能
                .transform(CenterCrop(), RoundedCorners(radius))
                .into(this)
        }
    }

    override fun ImageView.loadImage(context: Context?, file: File?) {
        context?.let {
            Glide.with(it)
                .load(file)
                .into(this)
        }
    }

    override fun ImageView.loadImageRound(context: Context?, file: File?) {
        context?.let {
            Glide.with(it)
                .load(file)
                .transform(CircleCrop())
                .into(this)
        }
    }

    override fun ImageView.loadImageRadius(
        context: Context?,
        file: File?,
        radius: Int
    ) {
        context?.let {
            Glide.with(it)
                .load(file)
                .transform(CenterCrop(), RoundedCorners(radius))
                .into(this)
        }
    }

    override fun ImageView.loadImage(
        context: Context?, url: String?
    ) {
        context?.let {
            Glide.with(it)
                .load(url)
                .into(this)
        }
    }

    override fun ImageView.loadImageRound(
        context: Context?, url: String?
    ) {
        context?.let {
            Glide.with(it)
                .load(url)
                .transform(CircleCrop())
                .into(this)
        }
    }

    override fun ImageView.loadImageRadius(
        context: Context?, url: String?, radius: Int
    ) {
        context?.let {
            Glide.with(it)
                .load(url)
                .transform(CenterCrop(), RoundedCorners(radius))
                .into(this)
        }
    }

    override fun ImageView.loadGif(context: Context?, resourceId: Int) {
        context?.let {
            Glide.with(it)
                .asGif()
                .load(resourceId)
                .into(this)
        }
    }

    override fun ImageView.loadGif(context: Context?, url: String?) {
        context?.let {
            Glide.with(it)
                .asGif()
                .load(url)
                .into(this)
        }
    }

    override fun getImageDrawable(context: Context?, url: String?): Drawable? {
        if (TextUtils.isEmpty(url)) {
            return null
        }
        context?.let {
            try {
                return Glide.with(it)
                    .load(url)
                    .submit()
                    .get()
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
        context?.let {
            try {
                return Glide.with(it)
                    .asBitmap()
                    .load(url)
                    .submit()
                    .get()
            } catch (e: ExecutionException) {
                e.printStackTrace()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        return null
    }
}