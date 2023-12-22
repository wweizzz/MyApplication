package com.example.william.my.core.widget.verifycode;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.william.my.core.widget.R;
import com.example.william.my.core.widget.utils.SizeUtils;

public class VerifyCodeView extends RelativeLayout {

    private int mCodeNum;
    private int mCodeWidth, mCodeHeight, mCodeMargin;
    private int mTextSize, mTextColor;
    private Drawable mTextBgNormal, mTextBgFocus;

    private EditText mEditText;

    private TextView[] mTextViews;

    private int mCurrentFocusPosition = 0;

    private StringBuffer mStringBuffer;

    public VerifyCodeView(Context context) {
        this(context, null);
    }

    public VerifyCodeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerifyCodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAttrs(context, attrs);
        initView(context);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.VerifyCodeView);
            mCodeNum = a.getInteger(R.styleable.VerifyCodeView_code_number, 4);
            mCodeWidth = a.getDimensionPixelSize(R.styleable.VerifyCodeView_code_width, 48);
            mCodeHeight = a.getDimensionPixelSize(R.styleable.VerifyCodeView_code_height, 48);
            mCodeMargin = a.getDimensionPixelSize(R.styleable.VerifyCodeView_code_margin, 8);
            mTextSize = a.getDimensionPixelSize(R.styleable.VerifyCodeView_code_size, 14);
            mTextColor = a.getColor(R.styleable.VerifyCodeView_code_color, Color.GRAY);
            mTextBgNormal = a.getDrawable(R.styleable.VerifyCodeView_code_bg_normal);
            mTextBgFocus = a.getDrawable(R.styleable.VerifyCodeView_code_bg_focus);
            if (mTextBgFocus == null) {
                mTextBgFocus = mTextBgNormal;
            }
            a.recycle();
        }
    }

    private void initView(Context context) {
        LinearLayout mLinearLayout = new LinearLayout(context);
        mLinearLayout.setGravity(Gravity.CENTER);
        mLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        mTextViews = new TextView[mCodeNum];
        for (int i = 0; i < mCodeNum; i++) {
            TextView textView = new TextView(context);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(mTextSize);
            textView.setTextColor(mTextColor);
            LayoutParams params = new LayoutParams(SizeUtils.dp2px(mCodeWidth), SizeUtils.dp2px(mCodeHeight));
            params.setMarginStart(SizeUtils.dp2px(mCodeMargin));
            textView.setLayoutParams(params);
            mTextViews[i] = textView;
            mLinearLayout.addView(textView);
        }
        addView(mLinearLayout);
        mEditText = new EditText(context);
        mEditText.setBackground(null);
        mEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        mEditText.setCursorVisible(false);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, SizeUtils.dp2px(mCodeHeight));
        mEditText.setLayoutParams(params);
        addView(mEditText);
        resetCursorPosition();
        setListener();
    }

    private void resetCursorPosition() {
        if (mTextBgNormal != null && mTextBgFocus != null) {
            for (int i = 0; i < mCodeNum; i++) {
                TextView textView = mTextViews[i];
                if (i == mCurrentFocusPosition) {
                    textView.setBackground(mTextBgNormal);
                } else {
                    textView.setBackground(mTextBgFocus);
                }
            }
        }
    }

    private void setListener() {
        mStringBuffer = new StringBuffer();
        mEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //如果字符不为""时才进行操作
                if (!TextUtils.isEmpty(editable)) {
                    setVerifyCodeText(editable.toString());
                }
            }
        });

        mEditText.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    return onKeyDelete();
                }
                return false;
            }
        });
    }

    private void setVerifyCodeText(String editable) {
        if (mStringBuffer.length() < mCodeNum) {
            mStringBuffer.append(editable);
            mCurrentFocusPosition = mStringBuffer.length();
            for (int i = 0; i < mStringBuffer.length(); i++) {
                mTextViews[i].setText(String.valueOf(mStringBuffer.charAt(i)));
            }

            if (inputCompleteListener != null) {
                if (mStringBuffer.length() == mCodeNum) {
                    inputCompleteListener.inputComplete();
                }
            }
        }
        resetCursorPosition();
        mEditText.setText("");
    }

    public boolean onKeyDelete() {
        if (mStringBuffer.length() > 0) {
            //删除相应位置的字符
            mStringBuffer.delete((mCurrentFocusPosition - 1), mCurrentFocusPosition);
            mCurrentFocusPosition = mStringBuffer.length();
            mTextViews[mCurrentFocusPosition].setText("");

            if (inputCompleteListener != null) {
                inputCompleteListener.deleteContent(true);
            }
        }
        resetCursorPosition();
        return false;
    }

    public void clearEditText() {
        mStringBuffer.delete(0, mStringBuffer.length());
        for (TextView textView : mTextViews) {
            textView.setText("");
        }
        resetCursorPosition();
    }

    public String getEditContent() {
        return mStringBuffer.toString();
    }

    public void setEditContent(String content) {
        mEditText.setText(content);
    }

    private InputCompleteListener inputCompleteListener;

    public void setInputCompleteListener(InputCompleteListener inputCompleteListener) {
        this.inputCompleteListener = inputCompleteListener;
    }

    public interface InputCompleteListener {
        void inputComplete();

        void deleteContent(boolean isDelete);
    }
}