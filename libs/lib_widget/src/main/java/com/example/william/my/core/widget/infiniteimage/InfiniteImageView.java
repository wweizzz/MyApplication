package com.example.william.my.core.widget.infiniteimage;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.content.res.ResourcesCompat;

import com.example.william.my.core.widget.R;

/**
 * Infinite Animation Image View
 */
public class InfiniteImageView extends View {

    public static final int VERTICAL = 0;
    public static final int HORIZONTAL = 1;

    private int mDirection;

    public static final int ASC = 0; // 向上/左滚动
    public static final int DESC = 1; // 向上/右滚动

    private int mSort;
    private int mSpeed;
    private Drawable mDrawable;

    private int mOffset = 0;
    private boolean mScrolling = false;

    public InfiniteImageView(Context context) {
        this(context, null);
    }

    public InfiniteImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InfiniteImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.InfiniteImageView);
            mDirection = a.getInt(R.styleable.InfiniteImageView_direction, VERTICAL);
            mSort = a.getInt(R.styleable.InfiniteImageView_sort, ASC);
            mSpeed = (int) a.getDimension(R.styleable.InfiniteImageView_speed, 10);
            Drawable drawable = a.getDrawable(R.styleable.InfiniteImageView_src);
            setDrawable(drawable);
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mDrawable == null) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }

        int dWidth = mDrawable.getIntrinsicWidth();
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int wideSize = MeasureSpec.getSize(widthMeasureSpec);

        int resultWidth;
        if (widthMode == MeasureSpec.UNSPECIFIED) {
            resultWidth = dWidth;
        } else {
            resultWidth = wideSize;
        }

        int dHeight = mDrawable.getIntrinsicHeight();
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int resultHeight;
        if (heightMode == MeasureSpec.UNSPECIFIED) {
            resultHeight = dHeight;
        } else {
            resultHeight = heightSize;
        }

        setMeasuredDimension(resultWidth, resultHeight);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mDrawable == null) {
            return;
        }

        if (mDirection == VERTICAL) {
            scrollVertical(canvas);
        } else {
            scrollHorizontal(canvas);
        }
    }

    private void scrollVertical(Canvas canvas) {
        int viewSize = getMeasuredHeight();
        int drawableScrollSize = mDrawable.getIntrinsicHeight();
        int drawableFixSize = mDrawable.getIntrinsicWidth();

        int drawEndOffset = mOffset;
        do {
            mDrawable.setBounds(0, drawEndOffset, drawableFixSize, drawEndOffset + drawableScrollSize);
            mDrawable.draw(canvas);
            drawEndOffset += drawableScrollSize;
        } while (drawEndOffset < viewSize);

        int drawStartOffset = mOffset;
        while (drawStartOffset > 0) {
            drawStartOffset -= drawableScrollSize;
            mDrawable.setBounds(0, drawStartOffset, drawableFixSize, drawStartOffset + drawableScrollSize);
            mDrawable.draw(canvas);
        }

        if (mOffset <= -drawableScrollSize) {
            mOffset += drawableScrollSize;
        }
        nextFrame();
    }

    private void scrollHorizontal(Canvas canvas) {
        int viewSize = getMeasuredWidth();
        int drawableScrollSize = mDrawable.getIntrinsicWidth();
        int drawableFixSize = mDrawable.getIntrinsicHeight();

        int drawEndOffset = mOffset;
        do {
            mDrawable.setBounds(drawEndOffset, 0, drawEndOffset + drawableScrollSize, drawableFixSize);
            mDrawable.draw(canvas);
            drawEndOffset += drawableScrollSize;
        } while (drawEndOffset < viewSize);

        int drawStartOffset = mOffset;
        while (drawStartOffset > 0) {
            drawStartOffset -= drawableScrollSize;
            mDrawable.setBounds(drawStartOffset, 0, drawStartOffset + drawableScrollSize, drawableFixSize);
            mDrawable.draw(canvas);
        }

        if (mOffset <= -drawableScrollSize) {
            mOffset += drawableScrollSize;
        }
        nextFrame();
    }

    private void nextFrame() {
        if (mScrolling && mSpeed != 0) {
            if (mSort == ASC) {
                mOffset -= mSpeed;
            } else {
                mOffset += mSpeed;
            }
            postInvalidateOnAnimation();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    public void start() {
        if (mScrolling) {
            return;
        }
        mScrolling = true;
        nextFrame();
    }

    public void stop() {
        if (!mScrolling) {
            return;
        }
        mScrolling = false;
    }

    public void restart() {
        mScrolling = false;
        mOffset = 0;
        start();
    }

    public void setDrawable(Drawable drawable) {
        mDrawable = drawable;
        restart();
    }

    public void setDrawable(int drawableId) {
        setDrawable(ResourcesCompat.getDrawable(getResources(), drawableId, null));
    }

    public void setPixelSpeed(int pixelSpeed) {
        mSpeed = pixelSpeed;
    }

}