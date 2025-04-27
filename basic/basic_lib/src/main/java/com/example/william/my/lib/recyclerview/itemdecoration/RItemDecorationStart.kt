package com.sceneconsole.artefact.lib.base.recyclerview.itemdecoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * LinearLayoutManager
 */
class RItemDecorationStart(
    private val space: Int,
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

        if (position == 0) {
            outRect.left = space
        }
    }
}