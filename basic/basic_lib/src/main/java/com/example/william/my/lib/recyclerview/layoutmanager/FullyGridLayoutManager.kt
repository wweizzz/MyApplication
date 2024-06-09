package com.example.william.my.lib.recyclerview.layoutmanager

import android.content.Context
import android.util.Log
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
) : GridLayoutManager(context, spanCount) {

    private val tag = this.javaClass.simpleName

    private val mMeasuredDimension = IntArray(2)
    private val mRecyclerViewState = RecyclerView.State()

    override fun onMeasure(
        recycler: RecyclerView.Recycler, state: RecyclerView.State, widthSpec: Int, heightSpec: Int
    ) {
        val widthSize = View.MeasureSpec.getSize(widthSpec)
        val heightSize = View.MeasureSpec.getSize(heightSpec)
        var width = 0
        var height = 0
        val count = itemCount
        val span = spanCount
        Log.e("TAG", " count $count span $span")
        for (position in 0 until count) {
            measureScrapChild(
                recycler,
                position,
                View.MeasureSpec.makeMeasureSpec(position, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(position, View.MeasureSpec.UNSPECIFIED),
                mMeasuredDimension
            )
            if (orientation == HORIZONTAL) {
                if (position % span == 0) {
                    width += mMeasuredDimension[0]
                }
                if (position == 0) {
                    height = mMeasuredDimension[1]
                }
            } else {
                if (position % span == 0) {
                    height += mMeasuredDimension[1]
                }
                if (position == 0) {
                    width = mMeasuredDimension[0]
                }
            }
        }
        val widthMode = View.MeasureSpec.getMode(widthSpec)
        val heightMode = View.MeasureSpec.getMode(heightSpec)
        when (widthMode) {
            View.MeasureSpec.EXACTLY -> {
                width = widthSize
            }

            View.MeasureSpec.AT_MOST -> {

            }

            View.MeasureSpec.UNSPECIFIED -> {

            }
        }
        when (heightMode) {
            View.MeasureSpec.EXACTLY -> {
                height = heightSize
            }

            View.MeasureSpec.AT_MOST -> {

            }

            View.MeasureSpec.UNSPECIFIED -> {

            }
        }
        setMeasuredDimension(width, height)
    }

    private fun measureScrapChild(
        recycler: RecyclerView.Recycler,
        position: Int,
        widthSpec: Int,
        heightSpec: Int,
        measuredDimension: IntArray
    ) {
        if (position < itemCount) {
            try {
                val view = recycler.getViewForPosition(0)
                val params = view.layoutParams as RecyclerView.LayoutParams
                val childWidthSpec = ViewGroup.getChildMeasureSpec(
                    widthSpec, paddingLeft + paddingRight, params.width
                )
                Log.e(
                    "TAG",
                    " widthSpec " + widthSpec + " params.width " + params.width + " childWidthSpec " + childWidthSpec
                )
                val childHeightSpec = ViewGroup.getChildMeasureSpec(
                    heightSpec, paddingTop + paddingBottom, params.height
                )
                view.measure(childWidthSpec, childHeightSpec)
                measuredDimension[0] = view.measuredWidth + params.leftMargin + params.rightMargin
                measuredDimension[1] = view.measuredHeight + params.bottomMargin + params.topMargin
                Log.e(tag, "measuredDimension[0] : " + measuredDimension[0].toString())
                Log.e(tag, "measuredDimension[1] : " + measuredDimension[1].toString())
                recycler.recycleView(view)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}