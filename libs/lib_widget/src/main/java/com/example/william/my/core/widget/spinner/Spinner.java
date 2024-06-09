package com.example.william.my.core.widget.spinner;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Spinner extends PopupWindow implements SpinnerAdapter.SpinnerClickListener {

    private SpinnerAdapter.SpinnerClickListener listener;

    public Spinner(Activity context, List<String> data) {
        super(context);
        RelativeLayout relativeLayout = new RelativeLayout(context);
        setContentView(relativeLayout);

        //SDK > 21 覆盖状态栏
        setClippingEnabled(false);
        //设置 PopupWindow 宽高
        //setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        //setHeight(LinearLayout.LayoutParams.MATCH_PARENT);

        setFocusable(true); // 设置PopupWindow获得焦点，为false时不处理返回键

        //setTouchable(true); // 设置PopupWindow区域可触摸，默认为true
        setOutsideTouchable(true); // 设置非PopupWindow区域可触摸，点击其他区域 PopupWindow 消失

        //去除黑边
        setBackgroundDrawable(new ColorDrawable());
        setBackgroundDrawable(ContextCompat.getDrawable(context, android.R.color.transparent));

        RecyclerView recyclerView = new RecyclerView(context);
        relativeLayout.addView(recyclerView);
        recyclerView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        SpinnerAdapter adapter = new SpinnerAdapter(context, data);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {
        dismiss();
        if (listener != null) {
            listener.onItemClick(position);
        }
    }

    public void setItemListener(SpinnerAdapter.SpinnerClickListener listener) {
        this.listener = listener;
    }
}
