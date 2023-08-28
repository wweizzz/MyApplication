package com.example.william.my.core.retrofit.loading;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.william.my.core.retrofit.R;

public class LoadingTip extends LinearLayout implements View.OnClickListener {

    private TextView mTextView;

    public LoadingTip(Context context) {
        super(context);
        initView(context);
    }

    public LoadingTip(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LoadingTip(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.basics_layout_loading, this);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setBackgroundColor(Color.WHITE);

        mTextView = findViewById(R.id.loading_textView);
        mTextView.setOnClickListener(this);
        setLoadingTip(Status.loading);
    }

    @Override
    public void onClick(View v) {
        if (onReloadListener != null) {
            onReloadListener.reload();
        }
    }

    public enum Status {
        loading, empty, finish, error
    }

    public void setMessage(String message) {
        mTextView.setText(message);
    }

    public void setLoadingTip(Status status) {
        this.setLoadingTip(status, null);
    }

    public void setLoadingTip(Status status, String message) {
        switch (status) {
            case loading:
                setVisibility(View.VISIBLE);
                setEnabled(false);
                mTextView.setText("加载中……");
                break;
            case empty:
                setVisibility(View.VISIBLE);
                setEnabled(false);
                if (message == null) {
                    mTextView.setText("暂无数据");
                } else {
                    mTextView.setText(message);
                }
                break;
            case finish:
                setVisibility(GONE);
                break;
            case error:
                setVisibility(View.VISIBLE);
                setEnabled(true);
                if (message == null) {
                    mTextView.setText("网络异常，请刷新页面");
                } else {
                    mTextView.setText(message);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 全屏LoadingTip
     */

    public static LoadingTip addLoadingTipFullScreen(Activity context) {
        LoadingTip loadingTip = new LoadingTip(context);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(0, getStatusBarHeight(), 0, 0);
        ((FrameLayout) context.getWindow().getDecorView()).addView(loadingTip);
        return loadingTip;
    }

    /**
     * 带标题栏LoadingTip
     */

    public static LoadingTip addLoadingTipWithTopBar(Activity context) {
        LoadingTip loadingTip = new LoadingTip(context);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(0, getToolBarHeight() + getStatusBarHeight(), 0, 0);
        ((FrameLayout) context.getWindow().getDecorView()).addView(loadingTip);
        return loadingTip;
    }

    public static int getToolBarHeight() {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (48 * scale + 0.5f);
    }

    public static int getStatusBarHeight() {
        Resources resources = Resources.getSystem();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    private LoadingTipListener onReloadListener;

    public void setOnReloadListener(LoadingTipListener listener) {
        onReloadListener = listener;
    }

    public interface LoadingTipListener {
        /**
         * 重新加载
         */
        void reload();
    }
}
