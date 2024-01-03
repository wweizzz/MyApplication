package com.example.william.my.lib.recyclerview.layoutmanager

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * 充满屏幕的RecyclerView
 */
class FullyGridLayoutManager(
    context: Context?,
    spanCount: Int = 1,
) :
    GridLayoutManager(context, spanCount) {

    private val mMeasuredDimension = IntArray(2)
    private val mRecyclerViewState = RecyclerView.State()

    override fun onMeasure(
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State,
        widthSpec: Int,
        heightSpec: Int
    ) {
        val widthMode = View.MeasureSpec.getMode(widthSpec)
        val heightMode = View.MeasureSpec.getMode(heightSpec)
        val widthSize = View.MeasureSpec.getSize(widthSpec)
        val heightSize = View.MeasureSpec.getSize(heightSpec)
        var width = 0
        var height = 0
        val count = itemCount
        val span = spanCount
        for (i in 0 until count) {
            measureScrapChild(
                recycler, i,
                View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
                mMeasuredDimension
            )
            if (orientation == HORIZONTAL) {
                if (i % span == 0) {
                    width += mMeasuredDimension[0]
                }
                if (i == 0) {
                    height = mMeasuredDimension[1]
                }
            } else {
                if (i % span == 0) {
                    height += mMeasuredDimension[1]
                }
                if (i == 0) {
                    width = mMeasuredDimension[0]
                }
            }
        }
        when (widthMode) {
            View.MeasureSpec.EXACTLY -> width = widthSize
            View.MeasureSpec.AT_MOST, View.MeasureSpec.UNSPECIFIED -> {}
        }
        when (heightMode) {
            View.MeasureSpec.EXACTLY -> height = heightSize
            View.MeasureSpec.AT_MOST, View.MeasureSpec.UNSPECIFIED -> {}
        }
        setMeasuredDimension(width, height)
    }

    private fun measureScrapChild(
        recycler: RecyclerView.Recycler, position: Int, widthSpec: Int,
        heightSpec: Int, measuredDimension: IntArray
    ) {
        val itemCount = mRecyclerViewState.itemCount
        if (position < itemCount) {
            try {
                val view = recycler.getViewForPosition(0)
                val p = view.layoutParams as RecyclerView.LayoutParams
                val childWidthSpec = ViewGroup.getChildMeasureSpec(
                    widthSpec,
                    paddingLeft + paddingRight, p.width
                )
                val childHeightSpec = ViewGroup.getChildMeasureSpec(
                    heightSpec,
                    paddingTop + paddingBottom, p.height
                )
                view.measure(childWidthSpec, childHeightSpec)
                measuredDimension[0] = view.measuredWidth + p.leftMargin + p.rightMargin
                measuredDimension[1] = view.measuredHeight + p.bottomMargin + p.topMargin
                recycler.recycleView(view)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}