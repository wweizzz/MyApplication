package com.example.william.my.core.widget.marqueeview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

import com.example.william.my.core.widget.R;

import java.util.List;

/**
 * 轮播公告
 */
public class MarqueeView extends ViewFlipper {

    @SuppressWarnings("FieldCanBeLocal")
    private final int interval = 2000;//翻页间隔

    @SuppressWarnings("FieldCanBeLocal")
    private final int animDuration = 500;//翻页动画时间

    public MarqueeView(Context context) {
        this(context, null);
    }

    public MarqueeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setFlipInterval(interval);//设置翻页间隔

        Animation animIn = AnimationUtils.loadAnimation(context, R.anim.anim_marquee_in);
        animIn.setDuration(animDuration);
        setInAnimation(animIn);

        Animation animOut = AnimationUtils.loadAnimation(context, R.anim.anim_marquee_out);
        animOut.setDuration(animDuration);
        setOutAnimation(animOut);
    }

    /**
     * 设置循环滚动的View数组
     */
    public void setViews(final List<View> views) {
        if (views == null || views.size() == 0) return;
        removeAllViews();
        for (int i = 0; i < views.size(); i++) {
            final int position = i;
            //设置监听回调
            views.get(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(position, views.get(position));
                    }
                }
            });
            addView(views.get(i));
        }
        startFlipping();
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }
}
