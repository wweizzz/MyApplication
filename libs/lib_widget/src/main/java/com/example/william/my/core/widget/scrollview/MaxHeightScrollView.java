package com.example.william.my.core.widget.scrollview;

import android.content.Context;
import android.util.AttributeSet;

import androidx.core.widget.NestedScrollView;

import com.example.william.my.core.widget.utils.ScreenUtils;

public class MaxHeightScrollView extends NestedScrollView {

    private final Context mContext;

    public MaxHeightScrollView(Context context) {
        super(context);
        this.mContext = context;
    }

    public MaxHeightScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public MaxHeightScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        try {
            int maxHeight = ScreenUtils.getScreenHeight(mContext) / 2;
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.AT_MOST);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
