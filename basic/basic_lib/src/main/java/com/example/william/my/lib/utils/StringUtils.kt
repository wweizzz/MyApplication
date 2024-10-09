package com.example.william.my.lib.utils

import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View

object StringUtils {

    fun List<String>?.toString(separator: String): String {
        return if (this.isNullOrEmpty()) {
            ""
        } else {
            val sb = StringBuilder()
            for (i in this.indices) {
                sb.append(this[i]).append(separator)
            }
            sb.substring(0, sb.toString().length - 1)
        }
    }

    fun SpannableString.setTextColor(
        text: String, highlight: String, color: Int, onClick: () -> Unit
    ): SpannableString {
        setSpan(
            object : ClickableSpan() {
                override fun updateDrawState(ds: TextPaint) {
                    ds.setColor(color)
                    ds.isUnderlineText = false
                }

                override fun onClick(widget: View) {
                    onClick()
                }
            },
            text.indexOf(highlight),
            text.indexOf(highlight) + highlight.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return this
    }
}