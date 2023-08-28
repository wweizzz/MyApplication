package com.example.william.my.core.widget.blur;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.AttrRes;

/**
 * 高斯模糊
 * <p>
 * 先将图片进行最大程度的模糊处理，再将原图放置在模糊后的图片上面
 * 通过不断改变原图的透明度(Alpha值)来实现动态模糊效果
 */
public class BlurView extends FrameLayout {

    private static final String TAG = BlurView.class.getSimpleName();

    private final Context mContext;

    private final ImageView mBlurImageView;
    private final ImageView mUnclearImageView;

    public BlurView(Context context) {
        this(context, null);
    }

    public BlurView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BlurView(Context context, AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

        mBlurImageView = new ImageView(mContext);
        mBlurImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        mUnclearImageView = new ImageView(mContext);
        mUnclearImageView.setScaleType(ImageView.ScaleType.FIT_XY);

        addView(mBlurImageView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(mUnclearImageView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    public void setImageView(int resId) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resId);
        if (bitmap != null) {
            mBlurImageView.setImageBitmap(blur(mContext, bitmap));
        } else {
            Log.e(TAG, "bitmap is null !!");
        }
        mUnclearImageView.setImageResource(resId);
    }

    public void setImageBlur(int blur) {
        mUnclearImageView.setImageAlpha((int) (255 - blur * 2.55));
    }

    /**
     * 模糊图片的具体方法
     */
    public static Bitmap blur(Context context, Bitmap bitmap) {

        // 将缩小后的图片做为预渲染的图片。
        Bitmap inputBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), false);
        // 创建一张渲染后的输出图片。
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

        // 创建RenderScript内核对象
        RenderScript rs = RenderScript.create(context);
        // 创建一个模糊效果的RenderScript的工具对象
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

        // 由于RenderScript并没有使用VM来分配内存,所以需要使用Allocation类来创建和分配内存空间。
        // 创建Allocation对象的时候其实内存是空的,需要使用copyTo()将数据填充进去。
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);

        // 设置渲染的模糊程度, 25f是最大模糊度
        blurScript.setRadius(25f);
        // 设置blurScript对象的输入内存
        blurScript.setInput(tmpIn);
        // 将输出数据保存到输出内存中
        blurScript.forEach(tmpOut);

        // 将数据填充到Allocation中
        tmpOut.copyTo(outputBitmap);

        return outputBitmap;
    }
}
