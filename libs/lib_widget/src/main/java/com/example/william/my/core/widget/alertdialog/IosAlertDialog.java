package com.example.william.my.core.widget.alertdialog;

import static android.view.Gravity.CENTER;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.william.my.core.widget.R;
import com.example.william.my.core.widget.utils.ScreenUtils;

/**
 * Ios风格的AlertDialog
 */
public class IosAlertDialog {

    private AlertDialog dialog;
    private final Activity context;

    private View ios_dialog_iv_line;
    private TextView ios_dialog_tv_title, ios_dialog_tv_msg;
    private TextView ios_dialog_btn_left, ios_dialog_btn_right;

    private boolean showTitle = false;
    private boolean showMsg = false;
    private boolean showRightBtn = false;
    private boolean showLeftBtn = false;

    public IosAlertDialog(Activity context) {
        this.context = context;
    }

    public IosAlertDialog builder() {
        View view = LayoutInflater.from(context).inflate(R.layout.alert_ios_dialog,
                (ViewGroup) context.getWindow().getDecorView(), false);

        ios_dialog_tv_title = view.findViewById(R.id.ios_dialog_tv_title);
        ios_dialog_tv_title.setVisibility(View.GONE);
        ios_dialog_tv_msg = view.findViewById(R.id.ios_dialog_tv_msg);
        ios_dialog_tv_msg.setVisibility(View.GONE);
        ios_dialog_btn_left = view.findViewById(R.id.ios_dialog_btn_left);
        ios_dialog_btn_left.setVisibility(View.GONE);
        ios_dialog_btn_right = view.findViewById(R.id.ios_dialog_btn_right);
        ios_dialog_btn_right.setVisibility(View.GONE);
        ios_dialog_iv_line = view.findViewById(R.id.ios_dialog_line_horizontal);
        ios_dialog_iv_line.setVisibility(View.GONE);

        // 定义Dialog布局和参数
        dialog = new AlertDialog.Builder(context, R.style.IosAlertDialogStyle).setView(view).create();
        dialog.show();//先show再set属性，否则dialog的大小不起作用

        Window dialogWindow = dialog.getWindow();
        if (dialogWindow != null) {
            WindowManager.LayoutParams params = dialogWindow.getAttributes();
            params.width = (int) (ScreenUtils.getScreenWidth(context) * 0.8);
            dialogWindow.setAttributes(params);
            dialogWindow.setGravity(CENTER);
        }

        return this;
    }

    /**
     * 设置标题
     */
    public IosAlertDialog setTitle(String title) {
        showTitle = true;
        if ("".equals(title)) {
            ios_dialog_tv_title.setText(context.getResources().getString(R.string.dialog_title));
        } else {
            ios_dialog_tv_title.setText(title);
        }
        return this;
    }

    /**
     * 设置提示
     */
    public IosAlertDialog setMsg(String msg) {
        setMsg(Gravity.CENTER, msg);
        return this;
    }

    /**
     * 设置提示
     */
    public IosAlertDialog setMsg(int gravity, String msg) {
        showMsg = true;
        if ("".equals(msg)) {
            ios_dialog_tv_msg.setGravity(gravity);
            ios_dialog_tv_msg.setText(context.getResources().getString(R.string.dialog_message));
        } else {
            ios_dialog_tv_msg.setGravity(gravity);
            ios_dialog_tv_msg.setText(msg);
        }
        return this;
    }

    /**
     * 是否可以取消
     */
    public IosAlertDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    /**
     * 是否可以取消
     */
    public IosAlertDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public IosAlertDialog setRightButton(String text, final View.OnClickListener listener) {
        showRightBtn = true;
        if ("".equals(text)) {
            ios_dialog_btn_right.setText(context.getResources().getString(R.string.dialog_ok));
        } else {
            ios_dialog_btn_right.setText(text);
        }
        ios_dialog_btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v);
                }
                dialog.dismiss();
            }
        });
        return this;
    }

    public IosAlertDialog setLeftButton(String text, final View.OnClickListener listener) {
        showLeftBtn = true;
        if ("".equals(text)) {
            ios_dialog_btn_left.setText(context.getResources().getString(R.string.dialog_cancel));
        } else {
            ios_dialog_btn_left.setText(text);
        }
        ios_dialog_btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v);
                }
                dialog.dismiss();
            }
        });
        return this;
    }

    private void setLayout() {
        if (!showTitle && !showMsg) {
            ios_dialog_tv_title.setText(context.getResources().getString(R.string.dialog_title));
            ios_dialog_tv_title.setVisibility(View.VISIBLE);
        }

        if (showTitle) {
            ios_dialog_tv_title.setVisibility(View.VISIBLE);
        }

        if (showMsg) {
            ios_dialog_tv_msg.setVisibility(View.VISIBLE);
        }

        if (!showRightBtn && !showLeftBtn) {
            ios_dialog_btn_right.setText(context.getResources().getString(R.string.dialog_ok));
            ios_dialog_btn_right.setVisibility(View.VISIBLE);
            ios_dialog_btn_right.setBackgroundResource(R.drawable.alert_selector_btn_single);
            ios_dialog_btn_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }

        if (showRightBtn && showLeftBtn) {
            ios_dialog_btn_right.setVisibility(View.VISIBLE);
            ios_dialog_btn_right.setBackgroundResource(R.drawable.alert_selector_btn_right);
            ios_dialog_btn_left.setVisibility(View.VISIBLE);
            ios_dialog_btn_left.setBackgroundResource(R.drawable.alert_selector_btn_left);
            ios_dialog_iv_line.setVisibility(View.VISIBLE);
        }

        if (showRightBtn && !showLeftBtn) {
            ios_dialog_btn_right.setVisibility(View.VISIBLE);
            ios_dialog_btn_right.setBackgroundResource(R.drawable.alert_selector_btn_single);
        }

        if (!showRightBtn && showLeftBtn) {
            ios_dialog_btn_left.setVisibility(View.VISIBLE);
            ios_dialog_btn_left.setBackgroundResource(R.drawable.alert_selector_btn_single);
        }
    }

    public void show() {
        setLayout();
        dialog.show();
    }
}
