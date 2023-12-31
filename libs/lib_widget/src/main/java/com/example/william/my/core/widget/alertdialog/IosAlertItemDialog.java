package com.example.william.my.core.widget.alertdialog;

import static android.view.Gravity.BOTTOM;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.example.william.my.core.widget.R;
import com.example.william.my.core.widget.utils.ScreenUtils;
import com.example.william.my.core.widget.utils.SizeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Ios风格的底部AlertDialog
 */
public class IosAlertItemDialog {

    private AlertDialog dialog;
    private final Activity context;

    private List<AlertItem> alertItems;

    private LinearLayout ios_item_dialog_layout_content;
    private TextView ios_item_dialog_tv_title, ios_item_dialog_tv_cancel;

    private boolean showTitle;

    public IosAlertItemDialog(Activity context) {
        this.context = context;
    }

    public IosAlertItemDialog builder() {
        View view = LayoutInflater.from(context).inflate(R.layout.alert_ios_dialog_item,
                (ViewGroup) context.getWindow().getDecorView(), false);

        ios_item_dialog_tv_title = view.findViewById(R.id.ios_item_dialog_tv_title);
        ios_item_dialog_tv_cancel = view.findViewById(R.id.ios_item_dialog_tv_cancel);
        ios_item_dialog_layout_content = view.findViewById(R.id.ios_item_dialog_layout_content);

        // 定义Dialog布局和参数
        dialog = new AlertDialog.Builder(context, R.style.IosItemAlertDialogStyle).setView(view).create();
        dialog.show();//先show再set属性，否则dialog的大小不起作用

        Window dialogWindow = dialog.getWindow();
        if (dialogWindow != null) {
            WindowManager.LayoutParams params = dialogWindow.getAttributes();
            params.width = (int) (ScreenUtils.getScreenWidth(context) * 0.9);
            dialogWindow.setAttributes(params);
            dialogWindow.setGravity(BOTTOM);
        }
        return this;
    }

    /**
     * 设置标题
     */
    public IosAlertItemDialog setTitle(String title) {
        showTitle = true;
        ios_item_dialog_tv_title.setVisibility(View.VISIBLE);
        if ("".equals(title)) {
            ios_item_dialog_tv_title.setText("Message");
        } else {
            ios_item_dialog_tv_title.setText(title);
        }
        return this;
    }

    /**
     * 是否可以取消
     */
    public IosAlertItemDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    /**
     * 是否可以取消
     */
    public IosAlertItemDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public IosAlertItemDialog addAlertItem(String strItem, OnItemClickListener listener) {
        if (alertItems == null) {
            alertItems = new ArrayList<>();
        }
        alertItems.add(new AlertItem(strItem, listener));
        return this;
    }

    public IosAlertItemDialog addAlertItem(String strItem, int color, OnItemClickListener listener) {
        if (alertItems == null) {
            alertItems = new ArrayList<>();
        }
        alertItems.add(new AlertItem(strItem, color, listener));
        return this;
    }


    private void setLayout() {
        if (alertItems == null || alertItems.size() <= 0) {
            return;
        }

        for (int i = 0; i < alertItems.size(); i++) {
            final int index = i;
            TextView textView = new TextView(context);
            textView.setTextSize(16);
            textView.setGravity(Gravity.CENTER);

            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(48)));

            if (showTitle) {
                if (alertItems.size() == 1) {
                    textView.setBackgroundResource(R.drawable.alert_selector_item_bottom);
                } else {
                    if (i == alertItems.size() - 1) {
                        textView.setBackgroundResource(R.drawable.alert_selector_item_bottom);
                    } else {
                        textView.setBackgroundResource(R.drawable.alert_selector_item_middle);
                    }
                }
            } else {
                if (alertItems.size() == 1) {
                    textView.setBackgroundResource(R.drawable.alert_selector_item_single);
                } else {
                    if (i == 0) {
                        textView.setBackgroundResource(R.drawable.alert_selector_item_top);
                    } else if (i == alertItems.size() - 1) {
                        textView.setBackgroundResource(R.drawable.alert_selector_item_bottom);
                    } else {
                        textView.setBackgroundResource(R.drawable.alert_selector_item_middle);
                    }
                }
            }

            textView.setText(alertItems.get(index).name);
            try {
                textView.setTextColor(ContextCompat.getColor(context, alertItems.get(index).color));
            } catch (Exception ignored) {

            }
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertItems.get(index).itemClickListener.onClick(index);
                    dialog.dismiss();
                }
            });

            ios_item_dialog_layout_content.addView(textView);
        }

        ios_item_dialog_tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void show() {
        setLayout();
        dialog.show();
    }


    public static class AlertItem {

        private final String name;
        private final int color;
        private final OnItemClickListener itemClickListener;

        public AlertItem(String name, OnItemClickListener itemClickListener) {
            this.name = name;
            this.color = R.color.colorAlertMessage;
            this.itemClickListener = itemClickListener;
        }

        public AlertItem(String name, int color, OnItemClickListener itemClickListener) {
            this.name = name;
            this.color = color;
            this.itemClickListener = itemClickListener;
        }
    }

    public interface OnItemClickListener {
        void onClick(int which);
    }
}
