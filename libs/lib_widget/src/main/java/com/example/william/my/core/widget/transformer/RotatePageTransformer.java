package com.example.william.my.core.widget.transformer;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class RotatePageTransformer extends BasePageTransformer {

    private static final float DEFAULT_MIN_ROTATE = 15f;
    private float mMinRotate = DEFAULT_MIN_ROTATE;

    public RotatePageTransformer() {

    }

    public RotatePageTransformer(float minRotate) {
        this(minRotate, NonPageTransformer.INSTANCE);
    }

    public RotatePageTransformer(ViewPager.PageTransformer pageTransformer) {
        this(DEFAULT_MIN_ROTATE, pageTransformer);
    }

    public RotatePageTransformer(float minRotate, ViewPager.PageTransformer pageTransformer) {
        mMinRotate = minRotate;
        mPageTransformer = pageTransformer;
    }

    @Override
    public void handleInvisiblePage(View view, float position) {
        view.setPivotX(view.getMeasuredWidth() * 0.5f);
        view.setPivotY(view.getMeasuredHeight());
        view.setRotation(0);
    }

    @Override
    public void handleLeftPage(View view, float position) {
        float rotation = (mMinRotate * position);
        view.setPivotX(view.getMeasuredWidth() * 0.5f);
        view.setPivotY(view.getMeasuredHeight());
        view.setRotation(rotation);
    }

    @Override
    public void handleRightPage(View view, float position) {
        handleLeftPage(view, position);
    }
}