package com.example.william.my.lib.recyclerview.itemdecoration

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * RecyclerView 左右边距
 */
class RItemDecorationStartEnd(
    private val space: Int,
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val spanCount = getSpanCount(parent)

        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount // 第几列

        if (column == 0) {
            outRect.left = space
        }
        if (column == spanCount - 1) {
            outRect.right = space
        }
    }

    /**
     * background
     */
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
    }

    /**
     * above
     */
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
    }

    private fun getSpanCount(parent: RecyclerView): Int {
        val spanCount = when (parent.layoutManager) {
            is StaggeredGridLayoutManager -> {
                (parent.layoutManager as StaggeredGridLayoutManager).spanCount
            }

            is GridLayoutManager -> {
                (parent.layoutManager as GridLayoutManager).spanCount
            }

            else -> {
                1
            }
        }
        return spanCount
    }
}