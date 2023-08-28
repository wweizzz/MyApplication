package com.example.william.my.core.widget.transformer;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class AlphaPageTransformer extends BasePageTransformer {

    private static final float DEFAULT_MIN_ALPHA = 0.5f;
    private float mMinAlpha = DEFAULT_MIN_ALPHA;

    public AlphaPageTransformer() {

    }

    public AlphaPageTransformer(float minAlpha) {
        this(minAlpha, NonPageTransformer.INSTANCE);
    }

    public AlphaPageTransformer(ViewPager.PageTransformer pageTransformer) {
        this(DEFAULT_MIN_ALPHA, pageTransformer);
    }

    public AlphaPageTransformer(float minAlpha, ViewPager.PageTransformer pageTransformer) {
        mMinAlpha = minAlpha;
        mPageTransformer = pageTransformer;
    }

    @Override
    protected void handleInvisiblePage(View view, float position) {
        view.setAlpha(mMinAlpha);
    }

    @Override
    protected void handleLeftPage(View view, float position) {
        view.setAlpha(mMinAlpha + (1 - mMinAlpha) * (1 + position));
    }

    @Override
    protected void handleRightPage(View view, float position) {
        view.setAlpha(mMinAlpha + (1 - mMinAlpha) * (1 - position));
    }
}
