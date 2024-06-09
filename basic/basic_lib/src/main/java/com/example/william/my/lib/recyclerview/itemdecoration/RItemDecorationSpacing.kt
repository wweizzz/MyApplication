package com.example.william.my.lib.recyclerview.itemdecoration

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * GridLayoutManager间距
 * 需要确保每个item分到的偏移量（offsize）相同
 */
class RItemDecorationSpacing(
    private var spacing: Int,
    private var includeEdge: Boolean = false,
    private var bottom: Int = 0,
    private var includeBottom: Boolean = false,
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        val itemCount = getItemCount(parent)
        val spanCount = getSpanCount(parent)

        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount // 第几列
        val row = position / spanCount  // 第几行

        if (includeEdge) {
            outRect.left = spacing - spacing / spanCount * column
            outRect.right = spacing / spanCount * (column + 1)
        } else {
            outRect.left = spacing / spanCount * column
            outRect.right = spacing - spacing / spanCount * (column + 1)
        }

        if (includeBottom) {
            outRect.bottom = bottom
        } else if (row != itemCount / spanCount) {
            outRect.bottom = bottom
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