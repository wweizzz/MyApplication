package com.example.william.my.core.ninepatch

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.william.my.core.ninepatch.ninepatchbuilder.NinePatchBuilder
import com.example.william.my.core.ninepatch.ninepatchchunk.NinePatchChunk

object NinePatchHelper {

    /**
     * https://github.com/xesam/InfiniteImageView
     */
    fun ninePatchChunk(context: Context?, view: View, url: String?) {
        Glide.with(context!!).asBitmap().load(url).into(object : CustomTarget<Bitmap?>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap?>?) {
                val drawable = NinePatchChunk.create9PatchDrawable(context, resource, url)
                view.background = drawable
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })
    }

    /**
     * https://stackoverflow.com/questions/5079868/create-a-ninepatch-ninepatchdrawable-in-runtime
     */
    fun ninePatchBuilder(context: Context, view: View, url: String?) {
        Glide.with(context).asBitmap().load(url).into(object : CustomTarget<Bitmap?>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap?>?) {
                val builder = NinePatchBuilder(context.resources, resource)
                val chunk = builder.buildChunk()
                val ninepatch = builder.buildNinePatch()
                val ninePatchDrawable = builder.buildNinePatchDrawable()
                view.background = ninePatchDrawable
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })
    }
}