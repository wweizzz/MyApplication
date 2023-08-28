package com.example.william.my.core.widget.sensor3d;

import android.content.Context;
import android.content.res.TypedArray;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.Scroller;

import androidx.annotation.IntDef;

import com.example.william.my.core.widget.R;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class SensorLayout extends FrameLayout implements SensorEventListener {

    private Scroller mScroller;
    private SensorManager mSensorManager;

    private Sensor mAccelerateSensor, mMagneticSensor;
    private float[] mAccelerateValues, mMagneticValues;

    private final float[] values = new float[3];//包含 x,y,z的偏移量
    private final float[] orientation = new float[9];//旋转矩阵

    private static double mDegreeYMin = -50;//最小偏移度数  Y
    private static double mDegreeYMax = 50;//最大偏移度数  Y
    private static final double mDegreeXMin = -50;//最小偏移度数  X
    private static final double mDegreeXMax = 50;//最大偏移度数  X
    private static final double mXMoveDistance = 50;//X轴移动偏移量 实际偏移为 mXMoveDistance * mAcclerateratio
    private static final double mYMoveDistance = 50;//Y轴移动偏移量 实际偏移为 mYMoveDistance * mAcclerateratio

    private float mAccelerateRatio = 1;//偏移加速的倍率 可以通过设置此倍率改变偏移速度

    public SensorLayout(Context context) {
        this(context, null);
    }

    public SensorLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SensorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAttrs(context, attrs);
        initView(context);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SensorLayout);
            mAccelerateRatio = a.getFloat(R.styleable.SensorLayout_AccelerateRatio, 1);
            a.recycle();
        }
    }

    private void initView(Context context) {
        mScroller = new Scroller(context);

        // 第一步：通过getSystemService获得SensorManager实例对象
        mSensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);

        if (mSensorManager != null) {
            // 第二步：通过SensorManager实例对象获得想要的传感器对象:参数决定获取哪个传感器
            mAccelerateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mMagneticSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

            // 第三步：在获得焦点时注册传感器，并让本类实现SensorEventListener接口
            mSensorManager.registerListener(this, mAccelerateSensor, SensorManager.SENSOR_DELAY_GAME);
            mSensorManager.registerListener(this, mMagneticSensor, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    /**
     * 传感器事件值改变时的回调接口：执行此方法的频率与注册传感器时的频率有关
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        // 第四步：计算偏转角度
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mAccelerateValues = event.values;
        }
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            mMagneticValues = event.values;
        }

        if (mMagneticValues != null && mAccelerateValues != null) {
            SensorManager.getRotationMatrix(orientation, null, mAccelerateValues, mMagneticValues);
        }

        SensorManager.getOrientation(orientation, values);
        // x轴的偏转角度
        double mDegreeX = (float) Math.toDegrees(values[1]);
        // y轴的偏转角度
        double mDegreeY = (float) Math.toDegrees(values[2]);

        boolean hasChangeX = false;
        boolean hasChangeY = false;
        int scrollX = mScroller.getFinalX();
        int scrollY = mScroller.getFinalY();

        // 第五步：根据偏转角度执行滑动
        if (mDegreeY <= 0 && mDegreeY > mDegreeYMin) {
            hasChangeX = true;
            scrollX = (int) (mDegreeY / Math.abs(mDegreeYMin) * mXMoveDistance * mAccelerateRatio);
        } else if (mDegreeY > 0 && mDegreeY < mDegreeYMax) {
            hasChangeX = true;
            scrollX = (int) (mDegreeY / Math.abs(mDegreeYMax) * mXMoveDistance * mAccelerateRatio);
        }
        if (mDegreeX <= 0 && mDegreeX > mDegreeXMin) {
            hasChangeY = true;
            scrollY = (int) (mDegreeX / Math.abs(mDegreeXMin) * mYMoveDistance * mAccelerateRatio);
        } else if (mDegreeX > 0 && mDegreeX < mDegreeXMax) {
            hasChangeY = true;
            scrollY = (int) (mDegreeX / Math.abs(mDegreeXMax) * mYMoveDistance * mAccelerateRatio);
        }
        smoothScrollTo(hasChangeX ? scrollX : mScroller.getFinalX(), hasChangeY ? scrollY : mScroller.getFinalY());
    }

    /**
     * 传感器精度发生改变的回调接口
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void smoothScrollTo(int destX, int destY) {
        int scrollY = getScrollY();
        int delta = destY - scrollY;
        mScroller.startScroll(destX, scrollY, 0, delta, 200);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    public void register() {
        /*
         *第一个参数：SensorEventListener接口的实例对象
         *第二个参数：需要注册的传感器实例
         *第三个参数：传感器获取传感器事件event值频率：
         *              SensorManager.SENSOR_DELAY_FASTEST = 0：对应0微秒的更新间隔，最快，1微秒 = 1 % 1000000秒
         *              SensorManager.SENSOR_DELAY_GAME = 1：对应20000微秒的更新间隔，游戏中常用
         *              SensorManager.SENSOR_DELAY_UI = 2：对应60000微秒的更新间隔
         *              SensorManager.SENSOR_DELAY_NORMAL = 3：对应200000微秒的更新间隔
         *              键入自定义的int值x时：对应x微秒的更新间隔
         */
        mSensorManager.registerListener(this, mAccelerateSensor, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mMagneticSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregister() {
        mSensorManager.unregisterListener(this);
    }

    public void setAccelerateRatio(float accelerateRatio) {
        this.mAccelerateRatio = accelerateRatio;
    }

    public void setDegree(double degreeYMin, double degreeYMax, double degreeXMin, double degreeXMax) {
        mDegreeYMin = degreeYMin;
        mDegreeYMax = degreeYMax;
        degreeXMin = degreeXMin;
        degreeXMax = degreeXMax;
    }

    @IntDef({DIRECTION_LEFT, DIRECTION_RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.PARAMETER)
    public @interface ADirection {

    }

    public static final int DIRECTION_LEFT = 1;
    public static final int DIRECTION_RIGHT = -1;
}
