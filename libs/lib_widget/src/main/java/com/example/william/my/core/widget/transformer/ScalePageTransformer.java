package com.example.william.my.core.widget.transformer;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class ScalePageTransformer extends BasePageTransformer {

    private static final float DEFAULT_MIN_SCALE = 0.85f;
    private float mMinScale = DEFAULT_MIN_SCALE;

    public ScalePageTransformer() {

    }

    public ScalePageTransformer(float minScale) {
        this(minScale, NonPageTransformer.INSTANCE);
    }

    public ScalePageTransformer(ViewPager.PageTransformer pageTransformer) {
        this(DEFAULT_MIN_SCALE, pageTransformer);
    }

    public ScalePageTransformer(float minScale, ViewPager.PageTransformer pageTransformer) {
        mMinScale = minScale;
        mPageTransformer = pageTransformer;
    }

    @Override
    public void handleInvisiblePage(View view, float position) {
        view.setScaleY(mMinScale);
    }

    @Override
    public void handleLeftPage(View view, float position) {
        float scale = Math.max(mMinScale, 1 - Math.abs(position));
        view.setScaleY(scale);
    }

    @Override
    public void handleRightPage(View view, float position) {
        float scale = Math.max(mMinScale, 1 - Math.abs(position));
        view.setScaleY(scale);
    }
}
