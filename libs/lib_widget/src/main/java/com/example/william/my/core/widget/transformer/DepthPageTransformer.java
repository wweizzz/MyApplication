package com.example.william.my.core.widget.transformer;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class DepthPageTransformer extends BasePageTransformer {

    private static final float DEFAULT_MIN_DEPTH = 0.85f;
    private float mMinDepth = DEFAULT_MIN_DEPTH;

    public DepthPageTransformer() {

    }

    public DepthPageTransformer(float minDepth) {
        this(minDepth, NonPageTransformer.INSTANCE);
    }

    public DepthPageTransformer(ViewPager.PageTransformer pageTransformer) {
        this(DEFAULT_MIN_DEPTH, pageTransformer);
    }

    public DepthPageTransformer(float minDepth, ViewPager.PageTransformer pageTransformer) {
        mMinDepth = minDepth;
        mPageTransformer = pageTransformer;
    }

    @Override
    public void handleInvisiblePage(View view, float position) {
        view.setAlpha(mMinDepth);
    }

    @Override
    public void handleLeftPage(View view, float position) {
        view.setAlpha(1);
        view.setTranslationX(0);
        view.setScaleX(1);
        view.setScaleY(1);
    }

    @Override
    public void handleRightPage(View view, float position) {
        view.setAlpha(1 - position);
        view.setTranslationX(-view.getWidth() * position);
        float scale = mMinDepth + (1 - mMinDepth) * (1 - position);
        view.setScaleX(scale);
        view.setScaleY(scale);
    }
}