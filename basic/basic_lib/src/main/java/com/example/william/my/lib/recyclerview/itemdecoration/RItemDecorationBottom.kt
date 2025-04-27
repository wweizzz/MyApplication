package com.example.william.my.lib.recyclerview.itemdecoration

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * RecyclerView 下间距(每行)
 */
class RItemDecorationBottom(
    private val space: Int,
    private val includeBottom: Boolean = false, // 是否包含底部
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val itemCount = getItemCount(parent)
        val spanCount = getSpanCount(parent)

        val position = parent.getChildAdapterPosition(view)
        val row = position / spanCount // 第几行

        if (includeBottom) {
            outRect.bottom = space
        } else if (row != itemCount / spanCount) {
            outRect.bottom = space
        }
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