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
import com.example.william.my.core.imageloader.IImageLoaderKtx
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
                .into(this)
        }
    }

    override fun ImageView.loadImageRound(
        context: Context?, resourceId: Int
    ) {
        context?.let {
            Glide.with(it)
                .load(resourceId)
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

    override fun ImageView.loadImage(
        context: Context?, url: String?, errorResId: Int
    ) {
        context?.let {
            Glide.with(it)
                .load(url)
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

    override fun ImageView.loadImageRound(
        context: Context?, url: String?, errorResId: Int
    ) {
        context?.let {
            Glide.with(it)
                .load(url)
                .error(errorResId)
                .transform(CircleCrop())
                .into(this)
        }
    }

    override fun ImageView.loadImageRadius(
        context: Context?, url: String?, radius: Int, errorResId: Int
    ) {
        context?.let {
            Glide.with(it)
                .load(url)
                .error(errorResId)
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