package com.example.william.my.module.demo.proxy;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class HookProxyFrameLayout extends FrameLayout {

    private final String TAG = HookProxyFrameLayout.class.getSimpleName();

    private final Activity resumedActivity;

    public HookProxyFrameLayout(Context context) {
        this(context, null);
    }

    public HookProxyFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HookProxyFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.resumedActivity = (Activity) context;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                //ACTION_DOWN do some thing
                View touchViewDown = findEventSrcView(ev, this);
                if (touchViewDown != null) {
                    ViewUtils.println(TAG, "Activity:" + resumedActivity.getClass().getName()
                            + "- ACTION_DOWN:" + ViewUtils.getAbsolutePath(touchViewDown));
                }
            }
            case MotionEvent.ACTION_MOVE: {
                //ACTION_MOVE do some thing
            }
            case MotionEvent.ACTION_UP: {
                //ACTION_UP do some thing
                View touchViewUp = findEventSrcView(ev, this);
                if (touchViewUp != null) {
                    ViewUtils.println(TAG, "Activity:" + resumedActivity.getClass().getName()
                            + "- ACTION_UP:" + ViewUtils.getAbsolutePath(touchViewUp));
                }
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    private View findEventSrcView(MotionEvent event, View srcView) {
        if (srcView instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) srcView;
            int size = viewGroup.getChildCount();
            for (int i = 0; i < size; i++) {
                View view = viewGroup.getChildAt(i);
                if (!(view instanceof HookProxyFrameLayout) && isEventInView(event, view)) {
                    View tmpRetView = findEventSrcView(event, view);
                    if (tmpRetView != null) {
                        return tmpRetView;
                    }
                }
            }
        } else if (isEventInView(event, srcView)) {
            return srcView;
        }
        return null;
    }

    /**
     * 判断是否在view的rect范围内
     */
    private Boolean isEventInView(MotionEvent event, View srcView) {
        Rect currentViewRect = new Rect();
        if (srcView.getGlobalVisibleRect(currentViewRect)) {
            RectF rectF = new RectF(currentViewRect);
            return rectF.contains(event.getRawX(), event.getRawY());
        }
        return false;
    }
}
