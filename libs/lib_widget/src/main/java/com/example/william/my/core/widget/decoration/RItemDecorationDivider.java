package com.example.william.my.core.widget.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.william.my.core.widget.R;

/**
 * RecyclerView分割线
 */
public class RItemDecorationDivider extends RecyclerView.ItemDecoration {

    private final Paint mPaint;

    private final float mDividerHeight;

    public RItemDecorationDivider(Context context) {
        this(context, R.color.colorDivider, 8);
    }

    public RItemDecorationDivider(Context context, int color) {
        this(context, color, 8);
    }

    public RItemDecorationDivider(Context context, float height) {
        this(context, R.color.colorDivider, height);
    }

    public RItemDecorationDivider(Context context, int color, float height) {
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setColor(ContextCompat.getColor(context, color));

        this.mDividerHeight = height;
    }

    /**
     * padding
     */
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = (int) mDividerHeight;
    }

    /**
     * background
     */
    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);

        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        for (int i = 0; i < parent.getChildCount() - 1; i++) {
            View view = parent.getChildAt(i);
            c.drawRect(left, view.getBottom(), right, view.getBottom() + mDividerHeight, mPaint);
        }
    }

    /**
     * above
     */
    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }
}
