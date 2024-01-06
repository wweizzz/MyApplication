package com.example.william.my.lib.recyclerview.itemdecoration

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * GridLayoutManager间距
 */
class RItemDecorationSpacing(
    private val spacing: Int,
    private val startEnd: Int = 0,
    private var bottom: Int = 0,
    private var includeBottom: Boolean = false,
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        val itemCount = getItemCount(parent)
        val spanCount = getSpanCount(parent)

        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount // 第几个
        val row = position / spanCount // 第几行

        if (column == 0) {
            outRect.left = startEnd
        } else {
            outRect.left = spacing
        }
        if (column == spanCount - 1) {
            outRect.right = startEnd
        } else {
            outRect.right = spacing
        }

        if (includeBottom) {
            outRect.bottom = if (bottom == 0) spacing else bottom
        } else if (row != itemCount / spanCount - 1) {
            outRect.bottom = if (bottom == 0) spacing else bottom
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

    private fun getItemCount(parent: RecyclerView): Int {
        return parent.layoutManager?.itemCount ?: 0
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