package com.example.william.my.module.demo.activity

import android.app.DatePickerDialog
import android.content.res.Resources
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.R
import com.example.william.my.basic.basic_module.activity.BasicRecyclerActivity
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.module.demo.dialog.MyCreateDialogDialog
import com.example.william.my.module.demo.dialog.MyCreateViewDialog
import java.util.Calendar

@Route(path = RouterPath.Demo.Dialog)
class DialogActivity : BasicRecyclerActivity() {

    override fun buildList(): ArrayList<String> {
        return arrayListOf(
            "普通对话框",
            "列表对话框",
            "单选对话框",
            "日期对话框",
            "自定义对话框",
            "自定义对话框2",
            "DialogFragment——onCreateDialog",
            "DialogFragment——onCreateView",
        )
    }

    override fun onRecyclerClick(position: Int, string: String) {
        super.onRecyclerClick(position, string)
        when (position) {
            0 -> AlertDialog.Builder(this@DialogActivity)
                .setIcon(R.drawable.ic_launcher)
                .setTitle("标题")
                .setMessage("内容")
                .setPositiveButton("确定") { _, _ -> }
                .setNegativeButton("取消") { _, _ -> }
                .create()
                .show()

            1 -> {
                val items = arrayOf("item1", "item2")
                AlertDialog.Builder(this@DialogActivity)
                    .setIcon(R.drawable.ic_launcher)
                    .setTitle("标题")
                    .setItems(items) { _, _ -> }.show()
            }

            2 -> {
                val items2 = arrayOf("item1", "item2")
                AlertDialog.Builder(this@DialogActivity)
                    .setSingleChoiceItems(items2, 0) { _, _ -> }
                    .setNegativeButton("取消", null)
                    .setPositiveButton("确定") { _, _ -> }
                    .show()
            }

            3 -> {
                val calendar = Calendar.getInstance()
                val datePickerDialog = DatePickerDialog(
                    this@DialogActivity, { view, year, month, dayOfMonth ->

                    },
                    calendar[Calendar.YEAR],
                    calendar[Calendar.MONTH],
                    calendar[Calendar.DAY_OF_MONTH]
                )
                //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());//设置最大日期
                datePickerDialog.show()
            }

            4 -> {
                /*
                 * setContentView 为 Dialog 的方法 ，对应整个对话框窗口的view ，在dialog.show之后使用
                 * setView 是 AlertDialog 的方法 ，对应的是 CustomView 的部分而不是整个窗体 ，在dialog.show之前使用
                 */
                val view1 = layoutInflater.inflate(
                    R.layout.basics_layout_response,
                    window.decorView as ViewGroup,
                    false
                )
                val text1 = view1.findViewById<TextView>(R.id.basics_response)
                text1.setBackgroundColor(
                    ContextCompat.getColor(
                        this@DialogActivity,
                        R.color.colorPrimary
                    )
                )
                val dialog1 = AlertDialog.Builder(this@DialogActivity)
                    .create()
                dialog1.show()
                dialog1.setContentView(view1)
            }

            5 -> {
                val view2 = layoutInflater.inflate(
                    R.layout.basics_layout_response,
                    window.decorView as ViewGroup,
                    false
                )
                val text2 = view2.findViewById<TextView>(R.id.basics_response)
                text2.setBackgroundColor(
                    ContextCompat.getColor(
                        this@DialogActivity,
                        R.color.colorPrimary
                    )
                )
                val dialog2 = AlertDialog.Builder(this@DialogActivity)
                    .setView(view2)
                    .create()
                dialog2.show()
                //注意：需要在show()之后，才能再设置宽高属性
                val params2 = dialog2.window!!.attributes
                params2.height = dp2px(180f)
                dialog2.window!!.attributes = params2
            }

            6 -> {
                val dialogFragment = MyCreateDialogDialog()
                dialogFragment.show(supportFragmentManager, dialogFragment.tag)
            }

            7 -> {
                val dialogFragment2 = MyCreateViewDialog()
                dialogFragment2.show(supportFragmentManager, dialogFragment2.tag)
            }

            else -> {}
        }
    }

    companion object {
        fun dp2px(dpValue: Float): Int {
            val scale = Resources.getSystem().displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }
    }
}