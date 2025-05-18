package com.example.william.my.lib.recyclerview.itemdecoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RItemDecorationSpacing(
    private var spacing: Int,
    private var includeEdge: Boolean = false,
    private var bottom: Int = 0,
    private var includeBottom: Boolean = false,
) : RItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        val itemCount = getItemCount(parent)
        val spanCount = getSpanCount(parent)

        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount // 第几列
        val row = position / spanCount  // 第几行

        if (position == RecyclerView.NO_POSITION) return

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
}