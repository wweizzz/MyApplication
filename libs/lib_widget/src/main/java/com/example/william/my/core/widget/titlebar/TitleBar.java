package com.example.william.my.core.widget.titlebar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.william.my.core.widget.utils.SizeUtils;

public class TitleBar extends RelativeLayout {
    private Activity activity;

    private TextView mTitle, mBtnBack, mBtnRight;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTitleBar(context);
    }

    private void initTitleBar(Context context) {
        this.activity = (Activity) context;

        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, SizeUtils.dp2px(48)));
        setBackgroundColor(ContextCompat.getColor(activity, android.R.color.transparent));
        setGravity(Gravity.CENTER_VERTICAL);

        initTitle();

        initBtnBack();

        initBtnRight();

        setBackPressed(true);
    }

    private void initTitle() {
        mTitle = new TextView(activity);
        mTitle.setTextSize(16);
        mTitle.setTypeface(mTitle.getTypeface(), Typeface.BOLD);
        //mTitle.setTextColor(ContextCompat.getColor(activity, R.color.colorAccent));

        mTitle.setGravity(Gravity.CENTER);
        mTitle.setPadding(SizeUtils.dp2px(8), 0, SizeUtils.dp2px(8), 0);

        LayoutParams titleParam = new LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        titleParam.addRule(CENTER_IN_PARENT);//居中

        addView(mTitle, titleParam);
    }

    private void initBtnBack() {
        mBtnBack = new TextView(activity);
        mBtnBack.setText("返回");
        mBtnBack.setTextSize(14);
        //mBtnBack.setTextColor(ContextCompat.getColor(activity, R.color.colorAccent));

        mBtnBack.setGravity(Gravity.CENTER);
        mBtnBack.setPadding(SizeUtils.dp2px(8), 0, SizeUtils.dp2px(8), 0);

        LayoutParams backParam = new LayoutParams(SizeUtils.dp2px(48), SizeUtils.dp2px(48));
        backParam.addRule(ALIGN_PARENT_START);

        addView(mBtnBack, backParam);
    }

    private void initBtnRight() {
        mBtnRight = new TextView(activity);
        mBtnRight.setTextSize(14);
        //mBtnRight.setTextColor(ContextCompat.getColor(activity, R.color.colorAccent));

        mBtnRight.setGravity(Gravity.CENTER);
        mBtnRight.setPadding(SizeUtils.dp2px(8), 0, SizeUtils.dp2px(8), 0);

        LayoutParams rightParam = new LayoutParams(SizeUtils.dp2px(48), SizeUtils.dp2px(48));
        rightParam.addRule(ALIGN_PARENT_END);

        addView(mBtnRight, rightParam);
    }

    public void setToolBarColor(int color) {
        setBackgroundColor(ContextCompat.getColor(activity, color));
        //BarUtils.addMarginTopEqualStatusBarHeight(this);
        //BarUtils.setStatusBarColor(activity, ColorUtils.getColor(color));
    }

    public void setTitle(String title) {
        if (mTitle != null) {
            mTitle.setText(title);
        }
    }

    public void setTitleColor(int color) {
        if (mTitle != null) {
            mTitle.setTextColor(ContextCompat.getColor(activity, color));
        }
    }

    public void setBackPressed(boolean b) {
        if (mBtnBack != null) {
            if (b) {
                mBtnBack.setVisibility(VISIBLE);
                mBtnBack.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        activity.finish();
                    }
                });
            } else {
                mBtnBack.setVisibility(GONE);
            }
        }
    }

    public void setBackPressed(String str) {
        if (mBtnBack != null) {
            mBtnBack.setText(str);
            mBtnBack.setBackground(null);
            mBtnBack.setVisibility(VISIBLE);
            mBtnBack.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.finish();
                }
            });
        }
    }

    public void setBackPressed(int resId) {
        if (mBtnBack != null) {
            mBtnBack.setText("");
            mBtnBack.setBackgroundResource(resId);
            mBtnBack.setVisibility(VISIBLE);
            mBtnBack.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.finish();
                }
            });
        }
    }

    public void setBtnRight(String str, OnClickListener l) {
        if (mBtnRight != null) {
            mBtnRight.setText(str);
            mBtnRight.setOnClickListener(l);
        }
    }

    public void setBtnRight(int resId, OnClickListener l) {
        if (mBtnRight != null) {
            mBtnRight.setBackgroundResource(resId);
            mBtnRight.setOnClickListener(l);
        }
    }

    public static TitleBar build(Activity activity, int resId) {
        ViewGroup contentParent = activity.findViewById(android.R.id.content);
        contentParent.removeAllViews();

        LinearLayout contentView = new LinearLayout(activity);
        contentView.setOrientation(LinearLayout.VERTICAL);

        TitleBar titleBar = new TitleBar(activity);
        contentView.addView(titleBar);

        //将 resId 添加到 contentView
        LayoutInflater.from(activity).inflate(resId, contentView);

        contentParent.addView(contentView);

        return titleBar;
    }

    public static TitleBar build(View view) {
        Activity activity = (Activity) view.getContext();

        ViewGroup contentParent = activity.findViewById(android.R.id.content);
        contentParent.removeAllViews();

        LinearLayout contentView = new LinearLayout(activity);
        contentView.setOrientation(LinearLayout.VERTICAL);

        TitleBar titleBar = new TitleBar(activity);
        contentView.addView(titleBar);

        //将 view 添加到 contentView
        contentView.addView(view);

        contentParent.addView(contentView);

        return titleBar;
    }
}