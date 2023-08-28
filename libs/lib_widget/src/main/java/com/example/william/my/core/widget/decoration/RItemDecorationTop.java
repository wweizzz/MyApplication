package com.example.william.my.core.widget.decoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.william.my.core.widget.utils.SizeUtils;

/**
 * RecyclerView分割线
 */
public class RItemDecorationTop extends RecyclerView.ItemDecoration {

    private final float marginTop;

    public RItemDecorationTop() {
        this(8);
    }

    public RItemDecorationTop(float marginTop) {
        this.marginTop = SizeUtils.dp2px(marginTop);
    }

    /**
     * padding
     */
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int spanCount = -1;

        if (parent.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) parent.getLayoutManager()).getSpanCount();
        } else if (parent.getLayoutManager() instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) parent.getLayoutManager()).getSpanCount();
        } else if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            spanCount = 1;
        }
        if (parent.getChildAdapterPosition(view) < spanCount) {
            outRect.top = (int) marginTop;
        }
    }

    /**
     * background
     */
    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    /**
     * above
     */
    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }
}
