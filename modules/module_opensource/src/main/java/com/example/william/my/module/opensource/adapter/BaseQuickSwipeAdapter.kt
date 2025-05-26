package com.example.william.my.module.opensource.adapter

import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter4.BaseQuickAdapter
import com.daimajia.swipe.SwipeLayout
import com.daimajia.swipe.implments.SwipeItemRecyclerMangerImpl
import com.daimajia.swipe.interfaces.SwipeAdapterInterface
import com.daimajia.swipe.interfaces.SwipeItemMangerInterface
import com.daimajia.swipe.util.Attributes

abstract class BaseQuickSwipeAdapter<T : Any, VH : RecyclerView.ViewHolder> :
    BaseQuickAdapter<T, VH>(), SwipeItemMangerInterface, SwipeAdapterInterface {

    var mItemManger: SwipeItemRecyclerMangerImpl = SwipeItemRecyclerMangerImpl(this)

    override fun onBindViewHolder(holder: VH, position: Int, item: T?) {
        if (holder.itemView is SwipeLayout) {
            mItemManger.bindView(holder.itemView, position)
        }
    }

    override fun openItem(position: Int) {
        mItemManger.openItem(position)
    }

    override fun closeItem(position: Int) {
        mItemManger.closeItem(position)
    }

    override fun closeAllExcept(layout: SwipeLayout?) {
        mItemManger.closeAllExcept(layout)
    }

    override fun closeAllItems() {
        mItemManger.closeAllItems()
    }

    override fun getOpenItems(): MutableList<Int?>? {
        return mItemManger.openItems
    }

    override fun getOpenLayouts(): MutableList<SwipeLayout?>? {
        return mItemManger.openLayouts
    }

    override fun removeShownLayouts(layout: SwipeLayout?) {
        mItemManger.removeShownLayouts(layout)
    }

    override fun isOpen(position: Int): Boolean {
        return mItemManger.isOpen(position)
    }

    override fun getMode(): Attributes.Mode? {
        return mItemManger.mode
    }

    override fun setMode(mode: Attributes.Mode?) {
        mItemManger.setMode(mode)
    }
}