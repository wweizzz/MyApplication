package com.example.william.my.lib.recyclerview.itemdecoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RItemDecorationEdgeTop(
    private val marginTop: Int
) : RItemDecoration() {

    /**
     * padding
     */
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

        if (position < spanCount) {
            outRect.top = marginTop
        }
    }
}
