package com.example.william.my.lib.tab

import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder

abstract class RecyclerTabAdapter<T : Any> : BaseQuickAdapter<T, QuickViewHolder>() {

    protected var currentSelectPosition = 0
    protected var lastSelectPosition = 0

    fun setSelectPosition(currentSelectPosition: Int) {
        //items[lastSelectPosition].setTabSelected(false)
        if (lastSelectPosition > -1) {
            notifyItemChanged(lastSelectPosition, false)
        }

        //items[currentSelectPosition].setTabSelected(true)
        if (currentSelectPosition > -1) {
            notifyItemChanged(currentSelectPosition, true)
        }

        this.currentSelectPosition = currentSelectPosition
        this.lastSelectPosition = currentSelectPosition
    }
}