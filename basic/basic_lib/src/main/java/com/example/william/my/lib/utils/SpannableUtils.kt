package com.example.william.my.lib.utils

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View

object SpannableUtils {

    fun SpannableStringBuilder.setSpan(
        highlight: String,
        color: Int,
        isUnderlineText: Boolean = false,
        onClick: () -> Unit = {}
    ): SpannableStringBuilder {
        setSpan(
            object : ClickableSpan() {
                override fun updateDrawState(ds: TextPaint) {
                    ds.setColor(color)
                    ds.isUnderlineText = isUnderlineText
                }

                override fun onClick(widget: View) {
                    onClick()
                }
            },
            this.indexOf(highlight),
            this.indexOf(highlight) + highlight.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return this
    }
}