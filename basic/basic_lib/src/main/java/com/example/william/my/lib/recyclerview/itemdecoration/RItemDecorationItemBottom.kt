package com.example.william.my.lib.recyclerview.itemdecoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * RecyclerView 下间距(每行)
 */
class RItemDecorationItemBottom(
    private val space: Int,
    private val includeBottom: Boolean = false, // 是否包含底部
) : RItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val itemCount = getItemCount(parent)
        val spanCount = getSpanCount(parent)

        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount // 第几列
        val row = position / spanCount  // 第几行

        if (position == RecyclerView.NO_POSITION) return

        if (includeBottom) {
            outRect.bottom = space
        } else if (row != itemCount / spanCount) {
            outRect.bottom = space
        }
    }
}