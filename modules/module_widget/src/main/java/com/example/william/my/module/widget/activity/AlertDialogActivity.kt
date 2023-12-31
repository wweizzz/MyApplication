package com.example.william.my.module.widget.activity

import android.view.Gravity
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicRecyclerActivity
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.core.widget.alertdialog.IosAlertDialog
import com.example.william.my.core.widget.alertdialog.IosAlertItemDialog
import com.example.william.my.module.widget.dialog.MyBottomSheetDialog

@Route(path = RouterPath.Widget.AlertDialog)
class AlertDialogActivity : BasicRecyclerActivity() {

    override fun buildList(): ArrayList<String> {
        return arrayListOf(
            "IosAlertDialog",
            "IosAlertItemDialog",
            "ViewPagerBottomSheetDialog"
        )
    }

    override fun onRecyclerClick(position: Int, string: String) {
        super.onRecyclerClick(position, string)
        when (position) {
            0 -> {
                IosAlertDialog(this@AlertDialogActivity).builder()
                    .setTitle("标题")
                    .setMsg(Gravity.CENTER, "内容")
                    .setCancelable(false)
                    .setCanceledOnTouchOutside(false)
                    .setLeftButton("左", View.OnClickListener { })
                    .setRightButton("右", View.OnClickListener { })
                    .show()
            }

            1 -> {
                IosAlertItemDialog(this@AlertDialogActivity).builder()
                    .setTitle("标题")
                    .setCancelable(false)
                    .setCanceledOnTouchOutside(false)
                    .addAlertItem("ITEM 1") { }
                    .addAlertItem("ITEM 2") { }
                    .addAlertItem(
                        "ITEM 3",
                        com.example.william.my.basic.basic_module.R.color.colorPrimary
                    ) {

                    }
                    .show()
            }

            2 -> {
                val dialogFragment3 = MyBottomSheetDialog()
                dialogFragment3.show(supportFragmentManager, dialogFragment3.tag)
            }
        }
    }
}