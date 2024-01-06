package com.example.william.my.lib.recyclerview.layoutmanager

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager

/**
 * 禁止滑动的RecyclerView
 */
class NoScrollGridLayoutManager(
    context: Context?,
    spanCount: Int = 1,
    private val horizontally: Boolean = false,
    private val vertically: Boolean = false
) :
    GridLayoutManager(context, spanCount) {

    override fun canScrollHorizontally(): Boolean {
        return horizontally
    }

    override fun canScrollVertically(): Boolean {
        return vertically
    }
}
