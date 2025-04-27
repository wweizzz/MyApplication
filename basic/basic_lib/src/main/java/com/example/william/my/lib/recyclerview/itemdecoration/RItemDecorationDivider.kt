package com.sceneconsole.artefact.lib.base.recyclerview.itemdecoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

/**
 * RecyclerView分割线
 */
class RItemDecorationDivider(
    context: Context,
    private val dividerHeight: Int,
    private val dividerColor: Int = 0xFFCCCC,
) : RItemDecoration() {

    private val mPaint: Paint = Paint()

    init {
        mPaint.isAntiAlias = true
        mPaint.color = ContextCompat.getColor(context, dividerColor)
    }

    /**
     * padding
     */
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = dividerHeight
    }

    /**
     * background
     */
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        for (i in 0 until parent.childCount - 1) {
            val view = parent.getChildAt(i)
            c.drawRect(
                left.toFloat(),
                view.bottom.toFloat(),
                right.toFloat(),
                (view.bottom + dividerHeight).toFloat(),
                mPaint
            )
        }
    }
}
