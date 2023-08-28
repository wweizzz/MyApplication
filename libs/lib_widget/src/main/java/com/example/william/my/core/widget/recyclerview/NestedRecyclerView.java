package com.example.william.my.core.widget.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 解决滑动冲突的内部RecyclerView
 */
public class NestedRecyclerView extends RecyclerView {

    private final String TAG = getClass().getSimpleName();

    private int lastVisibleItemPosition;
    private int firstVisibleItemPosition;
    private float mLastY = 0;// 记录上次Y位置
    private boolean isTopToBottom = false;
    private boolean isBottomToTop = false;

    public NestedRecyclerView(Context context) {
        this(context, null);
    }

    public NestedRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestedRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastY = event.getY();
                //不允许父View拦截事件
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                float nowY = event.getY();
                isIntercept(nowY);
                if (isBottomToTop || isTopToBottom) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                    return false;
                } else {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                mLastY = nowY;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return super.onTouchEvent(event);
    }

    private void isIntercept(float nowY) {

        isTopToBottom = false;
        isBottomToTop = false;

        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            //得到当前界面，最后一个子视图对应的position
            lastVisibleItemPosition = ((GridLayoutManager) layoutManager)
                    .findLastVisibleItemPosition();
            //得到当前界面，第一个子视图的position
            firstVisibleItemPosition = ((GridLayoutManager) layoutManager)
                    .findFirstVisibleItemPosition();
        } else if (layoutManager instanceof LinearLayoutManager) {
            //得到当前界面，最后一个子视图对应的position
            lastVisibleItemPosition = ((LinearLayoutManager) layoutManager)
                    .findLastVisibleItemPosition();
            //得到当前界面，第一个子视图的position
            firstVisibleItemPosition = ((LinearLayoutManager) layoutManager)
                    .findFirstVisibleItemPosition();
        }
        if (layoutManager != null) {
            //得到当前界面可见数据的大小
            int visibleItemCount = layoutManager.getChildCount();
            //得到RecyclerView对应所有数据的大小
            int totalItemCount = layoutManager.getItemCount();
            Log.i(TAG, "onScrollStateChanged");
            if (visibleItemCount > 0) {
                if (lastVisibleItemPosition == totalItemCount - 1) {
                    //最后视图对应的position等于总数-1时，说明上一次滑动结束时，触底了
                    Log.i(TAG, "触底了");
                    /*
                     * 1.canScrollVertically传的正负值问题，判断向上用正值1，向下则反过来用负值-1，
                     * 2.canScrollVertically返回值的问题，true时是代表可以滑动，false时才代表划到顶部或者底部不可以再滑动了，所以这个判断前要加逻辑非!运算符
                     */
                    if (!NestedRecyclerView.this.canScrollVertically(1) && nowY < mLastY) {
                        // 不能向上滑动
                        Log.i(TAG, "不能向上滑动");
                        isBottomToTop = true;
                    } else {
                        Log.i(TAG, "向下滑动");
                    }
                } else if (firstVisibleItemPosition == 0) {
                    //第一个视图的position等于0，说明上一次滑动结束时，触顶了
                    Log.i(TAG, "触顶了");
                    if (!NestedRecyclerView.this.canScrollVertically(-1) && nowY > mLastY) {
                        // 不能向下滑动
                        Log.i(TAG, "不能向下滑动");
                        isTopToBottom = true;
                    } else {
                        Log.i(TAG, "向上滑动");
                    }
                }
            }
        }
    }
}