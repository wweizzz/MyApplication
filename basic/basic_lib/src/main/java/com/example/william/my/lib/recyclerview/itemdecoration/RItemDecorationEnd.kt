package com.sceneconsole.artefact.lib.base.recyclerview.itemdecoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * LinearLayoutManager
 */
class RItemDecorationEnd(
    private val space: Int,
    private val includeEnd: Boolean = true, // 是否包含最后一个
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

        if (includeEnd) {
            outRect.right = space
        } else {
            if (position != itemCount - 1) {
                outRect.right = space
            }
        }
    }
}