package com.example.william.my.core.widget.sensor3d;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.example.william.my.core.widget.R;

public class Sensor3DView extends LinearLayout {

    private ImageView imgbackground, imgmid, imgforeground;
    private SensorLayout sensorbackground, sensormid, sensorforeground;

    private int backgroundUrl, midUrl, foregroundUrl;
    private float backgroundAccelerateRatio, midAccelerateRatio, foregroundAccelerateRatio;


    public Sensor3DView(Context context) {
        this(context, null);
    }

    public Sensor3DView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Sensor3DView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAttrs(context, attrs);
        initView(context);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Sensor3DView);
            backgroundUrl = a.getResourceId(R.styleable.Sensor3DView_backgrounddrawable, 1);
            midUrl = a.getResourceId(R.styleable.Sensor3DView_middrawable, 1);
            foregroundUrl = a.getResourceId(R.styleable.Sensor3DView_foregrounddrawable, 1);
            backgroundAccelerateRatio = a.getFloat(R.styleable.Sensor3DView_backgroundAccelerateRatio, 1);
            midAccelerateRatio = a.getFloat(R.styleable.Sensor3DView_midAccelerateRatio, 1);
            foregroundAccelerateRatio = a.getFloat(R.styleable.Sensor3DView_foregroundAccelerateRatio, 1);
            a.recycle();
        }
    }

    private void initView(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.sensor3d_item, this);
        sensorforeground = inflate.findViewById(R.id.sensorforeground);
        sensorbackground = inflate.findViewById(R.id.sensorbackground);
        sensormid = inflate.findViewById(R.id.sensormid);
        imgmid = inflate.findViewById(R.id.imgmid);
        imgbackground = inflate.findViewById(R.id.imgbackground);
        imgforeground = inflate.findViewById(R.id.imgforeground);
        setAllResources(backgroundUrl, midUrl, foregroundUrl);
        setAllRatio(backgroundAccelerateRatio, midAccelerateRatio, foregroundAccelerateRatio);
    }

    public void setAllResources(int backgroundUrl, int midUrl, int foregroundUrl) {
        imgbackground.setBackground(ResourcesCompat.getDrawable(getResources(), backgroundUrl, null));
        imgmid.setBackground(ResourcesCompat.getDrawable(getResources(), midUrl, null));
        imgforeground.setBackground(ResourcesCompat.getDrawable(getResources(), foregroundUrl, null));
    }

    public void setAllRatio(float backgroundRatio, float midRatio, float foregroundRatio) {
        sensorbackground.setAccelerateRatio(backgroundRatio);
        sensormid.setAccelerateRatio(midRatio);
        sensorforeground.setAccelerateRatio(foregroundRatio);
    }

    //设置限制角度
    public void setDegree(float MinX, float MinY, float MaxX, float MaxY, View3DLayer layer) {
        if (MinX >= MaxX || MinY >= MaxY) {
            return;
        }
        switch (layer) {
            case all:
                setDegree(MinY, MaxY, MinX, MaxX, sensorforeground);
                setDegree(MinY, MaxY, MinX, MaxX, sensormid);
                setDegree(MinY, MaxY, MinX, MaxX, sensorbackground);
                break;
            case mid:
                setDegree(MinY, MaxY, MinX, MaxX, sensormid);
                break;
            case background:
                setDegree(MinY, MaxY, MinX, MaxX, sensorbackground);
                break;
            case foreground:
                setDegree(MinY, MaxY, MinX, MaxX, sensorforeground);
                break;
        }
    }

    //sensorLayout 设置限制角度
    private void setDegree(float MinY, float MaxY, float MinX, float MaxX, SensorLayout sensorLayout) {
        sensorLayout.setDegree(MinY, MaxY, MinX, MaxX);
    }

    @Override
    public void destroyDrawingCache() {
        super.destroyDrawingCache();
        sensorbackground.unregister();
        sensormid.unregister();
        sensorforeground.unregister();
    }

    public enum View3DLayer {
        foreground,
        background,
        mid,
        all
    }
}