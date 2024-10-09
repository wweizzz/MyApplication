package com.example.william.my.lib.utils

import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.Typeface
import android.text.TextUtils
import android.widget.TextView
import androidx.core.content.ContextCompat

class TextUtils {

    fun TextView.setTypeface(path: String): TextView {
        this.typeface = Typeface.createFromAsset(this.context.assets, path)
        return this
    }

    fun TextView.setMarquee() {
        this.setSingleLine()
        this.setHorizontallyScrolling(true)
        this.ellipsize = TextUtils.TruncateAt.MARQUEE
        this.marqueeRepeatLimit = -1
        this.isSelected = true
    }

    fun TextView.setGradientColor(startColor: Int, endColor: Int) {
        this.paint.shader = LinearGradient(
            0f,
            0f,
            this.paint.textSize * this.text.length,
            0f,
            ContextCompat.getColor(this.context, startColor),
            ContextCompat.getColor(this.context, endColor),
            Shader.TileMode.CLAMP
        )
    }

    fun TextView.setGradientColor(startColor: Int, midColor: Int, endColor: Int) {
        this.paint.shader = LinearGradient(
            0f,
            0f,
            this.paint.textSize * this.text.length,
            0f,
            intArrayOf(
                ContextCompat.getColor(this.context, startColor),
                ContextCompat.getColor(this.context, midColor),
                ContextCompat.getColor(this.context, endColor),
            ),
            null,
            Shader.TileMode.CLAMP
        )
    }
}
