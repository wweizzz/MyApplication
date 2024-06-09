package com.example.william.my.module.demo.cache

import android.util.SparseArray
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerCacheExtension : RecyclerView.ViewCacheExtension() {

    private val mViewCache: SparseArray<View> = SparseArray(4)

    override fun getViewForPositionAndType(
        recycler: RecyclerView.Recycler,
        position: Int,
        type: Int
    ): View? {
        return if (mViewCache.size() > position) {
            mViewCache[position]
        } else null
    }

    fun addCache(position: Int, view: View) {
        if (mViewCache[position] !== view) {
            mViewCache.put(position, view)
        }
    }

    fun clearCache() {
        mViewCache.clear()
    }
}