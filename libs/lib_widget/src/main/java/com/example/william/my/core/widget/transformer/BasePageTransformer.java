package com.example.william.my.core.widget.transformer;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

public abstract class BasePageTransformer implements ViewPager.PageTransformer {

    protected ViewPager.PageTransformer mPageTransformer = NonPageTransformer.INSTANCE;

    public static final float DEFAULT_CENTER = 0.5f;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void transformPage(@NonNull View view, float position) {
        if (mPageTransformer != null) {
            mPageTransformer.transformPage(view, position);
        }
        if (view.getParent() instanceof ViewPager) {
            position = getRealPosition((ViewPager) view.getParent(), view);
        }
        if (position < -1.0f) {
            handleInvisiblePage(view, position);
        } else if (position <= 0.0f) {
            handleLeftPage(view, position);
        } else if (position <= 1.0f) {
            handleRightPage(view, position);
        } else if (position > 1.0f) {
            handleInvisiblePage(view, position);
        }
    }

    protected abstract void handleInvisiblePage(View view, float position);

    protected abstract void handleLeftPage(View view, float position);

    protected abstract void handleRightPage(View view, float position);

    private float getRealPosition(ViewPager viewPager, View page) {
        int width = viewPager.getMeasuredWidth() - viewPager.getPaddingLeft() - viewPager.getPaddingRight();
        return (float) (page.getLeft() - viewPager.getScrollX() - viewPager.getPaddingLeft()) / width;
    }
}
