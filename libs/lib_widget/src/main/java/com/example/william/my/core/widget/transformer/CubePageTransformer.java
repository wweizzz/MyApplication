package com.example.william.my.core.widget.transformer;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class CubePageTransformer extends BasePageTransformer {

    private static final float DEFAULT_MIN_CUBE = 90f;
    private float mMaxCube = DEFAULT_MIN_CUBE;

    public CubePageTransformer() {

    }

    public CubePageTransformer(float minAlpha) {
        this(minAlpha, NonPageTransformer.INSTANCE);
    }

    public CubePageTransformer(ViewPager.PageTransformer pageTransformer) {
        this(DEFAULT_MIN_CUBE, pageTransformer);
    }

    public CubePageTransformer(float minAlpha, ViewPager.PageTransformer pageTransformer) {
        mMaxCube = minAlpha;
        mPageTransformer = pageTransformer;
    }


    @Override
    public void handleInvisiblePage(View view, float position) {
        view.setPivotX(view.getMeasuredWidth());
        view.setPivotY(view.getMeasuredHeight() * 0.5f);
        view.setRotationY(0);
    }

    @Override
    public void handleLeftPage(View view, float position) {
        view.setPivotX(view.getMeasuredWidth());
        view.setPivotY(view.getMeasuredHeight() * 0.5f);
        view.setRotationY(mMaxCube * position);
    }

    @Override
    public void handleRightPage(View view, float position) {
        view.setPivotX(0);
        view.setPivotY(view.getMeasuredHeight() * 0.5f);
        view.setRotationY(mMaxCube * position);
    }

}
