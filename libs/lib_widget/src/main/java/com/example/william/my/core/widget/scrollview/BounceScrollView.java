package com.example.william.my.core.widget.scrollview;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

/**
 * 弹性nestedScrollView
 */
public class BounceScrollView extends NestedScrollView {

    private View mInnerView;

    private float mDownY;

    private final Rect mRect = new Rect();
    private int offset;

    private boolean isCount = false;

    public BounceScrollView(@NonNull Context context) {
        this(context, null);
    }

    public BounceScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BounceScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //获取的就是 scrollview 的第一个子 View
        if (getChildCount() > 0) {
            mInnerView = getChildAt(0);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int mWidth = MeasureSpec.getSize(widthMeasureSpec);
        int mHeight = MeasureSpec.getSize(heightMeasureSpec);

        MarginLayoutParams params = (MarginLayoutParams) mInnerView.getLayoutParams();
        //减去 margin 的值
        offset = mInnerView.getMeasuredHeight() - params.topMargin - params.bottomMargin - mHeight;
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent e) {
        if (mInnerView != null) {
            commOnTouchEvent(e);
        }
        return super.onTouchEvent(e);
    }

    public void commOnTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                final float preY = mDownY;// 按下时的y坐标
                float nowY = e.getY();// 时时y坐标
                int deltaY = (int) (preY - nowY);// 滑动距离
                //排除出第一次移动计算无法得知y坐标
                if (!isCount) {
                    deltaY = 0;
                }

                mDownY = nowY;
                // 当滚动到最上或者最下时就不会再滚动，这时移动布局
                if (isNeedMove()) {
                    if (mRect.isEmpty()) {
                        //保存正常的布局位置
                        mRect.set(mInnerView.getLeft(), mInnerView.getTop(), mInnerView.getRight(), mInnerView.getBottom());
                    }
                    //移动布局
                    mInnerView.layout(mInnerView.getLeft(), mInnerView.getTop() - deltaY / 3, mInnerView.getRight(), mInnerView.getBottom() - deltaY / 3);
                }
                isCount = true;
                break;
            case MotionEvent.ACTION_UP:
                if (isNeedAnimation()) {
                    translateAnimator();
                    isCount = false;
                }
                break;
        }
    }

    public void translateAnimator() {
        Animation animation = new TranslateAnimation(0, 0, mInnerView.getTop(), mRect.top);
        animation.setDuration(200);
        animation.setFillAfter(true);
        mInnerView.startAnimation(animation);
        // 设置回到正常的布局位置
        mInnerView.layout(mRect.left, mRect.top, mRect.right, mRect.bottom);
        mRect.setEmpty();
    }

    // 是否需要开启动画
    public boolean isNeedAnimation() {
        return !mRect.isEmpty();
    }

    public boolean isNeedMove() {
        return getScrollY() == 0 || getScrollY() >= offset;
    }

}



