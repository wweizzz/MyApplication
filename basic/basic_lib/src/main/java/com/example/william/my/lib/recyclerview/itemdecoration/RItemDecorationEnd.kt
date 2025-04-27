package com.example.william.my.lib.recyclerview.itemdecoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RItemDecorationEnd(
    private val space: Int,
    private val includeEnd: Boolean = true, // 是否包含最后一个
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)

        if (position == RecyclerView.NO_POSITION) return

        if (includeEnd) {
            outRect.right = space
        } else {
            if (position != parent.adapter!!.itemCount - 1) {
                outRect.right = space
            }
        }
    }
}