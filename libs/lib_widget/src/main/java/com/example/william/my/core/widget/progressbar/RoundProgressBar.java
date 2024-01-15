package com.example.william.my.core.widget.progressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.AttrRes;
import androidx.core.content.ContextCompat;

import com.example.william.my.core.widget.R;

/**
 * 圆形进度条
 */
public class RoundProgressBar extends View {

    private final RectF mRectF;
    private final Paint mPaint;

    private final int roundProgressColor;//圆环进度的颜色
    private final int roundBackgroundColor;//圆环背景的颜色

    private final float roundWidth;//圆环的宽度

    private final int max;//最大进度
    private int progress;//当前进度

    private final int style;//样式，空心或实心
    public static final int STROKE = 0;
    public static final int FILL = 1;

    private final int textColor;//中间进度字体颜色
    private final float textSize;//中间进度字体颜色大小
    private final boolean textDisplayable;//是否显示中间进度

    public RoundProgressBar(Context context) {
        this(context, null);
    }

    public RoundProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgressBar(Context context, AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mRectF = new RectF();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBar);
        //进度条颜色
        roundProgressColor = a.getColor(R.styleable.RoundProgressBar_roundProgressColor, ContextCompat.getColor(context, R.color.colorProgress));
        roundBackgroundColor = a.getColor(R.styleable.RoundProgressBar_roundBackgroundColor, ContextCompat.getColor(context, R.color.colorProgressBg));
        //进度条宽度
        roundWidth = a.getDimension(R.styleable.RoundProgressBar_roundWidth, 10);
        //进度条最大值
        max = a.getInteger(R.styleable.RoundProgressBar_max, 100);
        progress = 30;
        //进度条样式
        style = a.getInt(R.styleable.RoundProgressBar_style, STROKE);
        //进度条进度
        textColor = a.getColor(R.styleable.RoundProgressBar_textColor, ContextCompat.getColor(context, R.color.colorProgressText));
        textSize = a.getDimension(R.styleable.RoundProgressBar_textSize, 120);
        textDisplayable = a.getBoolean(R.styleable.RoundProgressBar_textDisplayable, false);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mWidth = MeasureSpec.getSize(widthMeasureSpec);
        int mHeight = MeasureSpec.getSize(heightMeasureSpec);
        int sizeMeasureSpec = Math.min(mWidth, mHeight);
        setMeasuredDimension(sizeMeasureSpec, sizeMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centerX = getWidth() / 2;//获取圆心的x坐标
        int centerY = getHeight() / 2;//获取圆心的x坐标
        int radius = (int) (centerX - roundWidth / 2);//圆环的半径

        //进度条背景
        mPaint.setStyle(Paint.Style.STROKE);//设置空心
        mPaint.setColor(roundBackgroundColor);//设置圆环的颜色
        mPaint.setStrokeWidth(roundWidth);//设置圆环的宽度
        canvas.drawCircle(centerX, centerY, radius, mPaint);//画出圆环

        //进度条进度
        mPaint.setStyle(Paint.Style.STROKE);//设置空心
        mPaint.setColor(roundProgressColor);  //设置进度的颜色
        mPaint.setStrokeWidth(roundWidth); //设置圆环的宽度
        mRectF.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);

        //起始角度
        int startAngle = 270;

        switch (style) {
            case STROKE:
                mPaint.setStyle(Paint.Style.STROKE);
                canvas.drawArc(mRectF, startAngle, (float) 360 * progress / max, false, mPaint);  //根据进度画圆弧
                break;
            case FILL:
                mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                if (progress != 0) {
                    canvas.drawArc(mRectF, startAngle, (float) 360 * progress / max, true, mPaint);  //根据进度画圆弧
                }
                break;
        }
        //中间进度百分比
        mPaint.setStrokeWidth(0);
        mPaint.setColor(textColor);
        mPaint.setTextSize(textSize);
        mPaint.setTypeface(Typeface.DEFAULT);//设置字体

        if (textDisplayable && style == STROKE) {
            int percent = (int) (((float) progress / (float) max) * 100);  //中间的进度百分比，先转换成float在进行除法运算，不然都为0
            float textWidth = mPaint.measureText(percent + "%");//测量字体宽度，我们需要根据字体的宽度设置在圆环中间
            canvas.drawText(percent + "%", centerX - textWidth / 2, centerY + textSize / 2, mPaint); //画出进度百分比
        }
    }

    public synchronized int getProgress() {
        return progress;
    }

    /**
     * 设置进度，此为线程安全控件
     * 由于考虑多线的问题，需要同步刷新界面调用postInvalidate()能在非UI线程刷新
     */
    public synchronized void setProgress(int progress) {
        if (progress < 0) {
            throw new IllegalArgumentException("progress not less than 0");
        }
        if (progress > max) {
            progress = max;
        }
        if (progress < max) {
            this.progress = progress;
            postInvalidate();
        }
    }
}
