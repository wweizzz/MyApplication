package com.example.william.my.module.demo.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.william.my.basic.basic_module.R

class MyCreateDialogDialog : DialogFragment() {
    /**
     * 重新onCreateDialog方法， 返回一个创建的dialog对象
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireActivity())
            .setIcon(R.drawable.ic_launcher)
            .setTitle("标题")
            .setMessage("内容")
            .setPositiveButton("确定") { _, _ -> }
            .setNegativeButton("取消") { _, _ -> }
            .create()
    }
}