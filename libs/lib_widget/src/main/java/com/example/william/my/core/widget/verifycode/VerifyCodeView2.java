package com.example.william.my.core.widget.verifycode;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.william.my.core.widget.R;

/**
 * 验证码
 */
public class VerifyCodeView2 extends LinearLayout {

    /**
     * 输入框的宽度
     */
    private int mEditTextWidth = 0;

    /**
     * 输入框的个数
     */
    private int mEditTextNumber = 0;

    /**
     * 编辑框文字大小
     */
    private int mTextSize = 0;

    /**
     * 编辑框文字颜色
     */
    private int mTextColor = Color.WHITE;

    /**
     * 编辑框文字间距
     */
    private int mTextSpacing = 0;

    /**
     * 提示文字颜色
     */
    private int mHintTextColor = Color.WHITE;

    /**
     * 默认是否平分
     */
    private boolean isBisection = true;

    private int focusIndex = 0;

    private int touchNumber = 0;

    private OnCodeInputCompleteListener onCodeInputCompleteListener;

    public VerifyCodeView2(Context context) {
        this(context, null);
    }

    public VerifyCodeView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerifyCodeView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        initView();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = null;
        try {
            typedArray = context.obtainStyledAttributes(attrs, R.styleable.VerifyCodeView2);
            mEditTextWidth = typedArray.getDimensionPixelSize(R.styleable.VerifyCodeView2_editWidth, 0);
            mEditTextNumber = typedArray.getInteger(R.styleable.VerifyCodeView2_editNumber, 6);
            mTextSize = typedArray.getDimensionPixelSize(R.styleable.VerifyCodeView2_editTextSize, 0);
            mTextColor = typedArray.getColor(R.styleable.VerifyCodeView2_editTextColor, Color.WHITE);
            mTextSpacing = typedArray.getDimensionPixelSize(R.styleable.VerifyCodeView2_editTextSpacing, 0);
            mHintTextColor = typedArray.getColor(R.styleable.VerifyCodeView2_editHintTextColor, Color.WHITE);
            isBisection = typedArray.getBoolean(R.styleable.VerifyCodeView2_isShowBisection, true);
        } finally {
            if (typedArray != null) {
                typedArray.recycle();
            }
        }
    }

    private void initView() {
        setGravity(Gravity.CENTER);
        for (int i = 0; i < mEditTextNumber; i++) {
            EditText editText = new EditText(getContext());
            initEditText(editText, i);
            addView(editText);
            if (i == 0) {
                focusIndex = 0;
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
                editText.requestFocus();
            }
        }
        setOnClickListener(v ->
                showSoftInput()
        );
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initEditText(EditText editText, int index) {
        editText.setLayoutParams(initLayoutParams(index));
        editText.setGravity(Gravity.CENTER);
        editText.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);

        SpannableString spannableString = new SpannableString("●");
        AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(8, true);
        spannableString.setSpan(absoluteSizeSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setHint(spannableString);

        editText.setId(index);
        editText.setTextColor(mTextColor);
        editText.setHintTextColor(mHintTextColor);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_PT, mTextSize);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
        editText.setMinEms(1);
        editText.setMaxEms(1);
        editText.setLines(1);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setBackground(null);
        editText.setCursorVisible(false);
        editText.setPadding(0, 0, 0, 0);
        editText.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    focus();
                }
                if (onCodeInputCompleteListener != null) {
                    EditText lastEditText = (EditText) getChildAt(getChildCount() - 1);
                    if (!TextUtils.isEmpty(lastEditText.getText())) {
                        onCodeInputCompleteListener.onCodeComplete(getValue());
                    }
                }
            }
        });
        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (v.getId() != focusIndex && hasFocus) {
                focus();
            }
        });
        editText.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                backFocus();
                return true;
            }
            return false;
        });
        editText.setOnTouchListener((v, event) -> {
            touchNumber++;
            if (touchNumber == 2) {
                touchNumber = 0;
                showSoftInput();
            }
            return false;
        });
    }

    private void focus() {
        for (int i = 0; i < getChildCount(); i++) {
            EditText editText = (EditText) getChildAt(i);
            if (!TextUtils.isEmpty(editText.getText()) && editText.isFocused()) {
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
                continue;
            }
            if (TextUtils.isEmpty(editText.getText()) && !editText.isFocused()) {
                focusIndex = i;
                editText.requestFocus();
                editText.findFocus();
                break;
            }
        }
    }

    private void backFocus() {
        for (int i = getChildCount() - 1; i >= 0; i--) {
            EditText editText = (EditText) getChildAt(i);
            if (!TextUtils.isEmpty(editText.getText())) {
                editText.setText("");
                focusIndex = i;
                editText.requestFocus();
                editText.findFocus();
                break;
            }
        }
    }

    private void showSoftInput() {
        for (int i = 0; i < getChildCount(); i++) {
            EditText editText = (EditText) getChildAt(i);
            if (editText.isFocused()) {
                //KeyboardUtils.showSoftInput(editText);
                break;
            }
        }
    }

    @NonNull
    private LayoutParams initLayoutParams(int index) {
        LayoutParams layoutParams = new LayoutParams(mEditTextWidth / 2, mEditTextWidth);
        if (isBisection) {
            mTextSpacing = Math.abs((getMeasuredWidth() - mEditTextNumber * mEditTextWidth / 2)) / (mEditTextNumber + 1);
            switch (index) {
                case 2:
                    layoutParams.setMarginEnd(mEditTextWidth / 3);
                    break;
                case 3:
                    layoutParams.setMarginStart(mEditTextWidth / 3);
                    layoutParams.setMarginEnd(mEditTextWidth / 4);
                    break;
                default:
                    if (index != mEditTextNumber - 1) {
                        layoutParams.setMarginEnd(mEditTextWidth / 4);
                    }
                    break;
            }
        } else {
            layoutParams.setMarginStart(mTextSpacing / 2);
            layoutParams.setMarginEnd(mTextSpacing / 2);
        }
        return layoutParams;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < mEditTextNumber; i++) {
            EditText editText = (EditText) getChildAt(i);
            editText.setLayoutParams(initLayoutParams(i));
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setEnabled(false);
        }
    }

    public String getValue() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getChildCount(); i++) {
            EditText editText = (EditText) getChildAt(i);
            sb.append(editText.getText());
        }
        return sb.toString();
    }

    public void setEmpty() {
        for (int i = getChildCount() - 1; i >= 0; i--) {
            EditText editText = (EditText) getChildAt(i);
            editText.setText("");
            if (i == 0) {
                focusIndex = 0;
                editText.requestFocus();
                editText.findFocus();
            }
        }
    }

    public void setOnCodeInputCompleteListener(OnCodeInputCompleteListener listener) {
        this.onCodeInputCompleteListener = listener;
    }

    public interface OnCodeInputCompleteListener {
        void onCodeComplete(String verCode);
    }
}
