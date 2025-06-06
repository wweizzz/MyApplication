package com.example.william.my.module.sample.activity.androidx

import android.app.Activity
import android.content.Intent
import android.view.View
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity

/**
 * ActivityResultContracts
 */
class ActivityResultActivity2 : BasicResponseActivity() {

    override fun initView() {
        super.initView()

        showResponse(intent?.getStringExtra("input"))
    }

    override fun onResponseClick(view: View) {
        super.onResponseClick(view)

        setActivityResult()
    }

    private fun setActivityResult() {
        val intent = Intent().apply {
            putExtra("result", "Hello，我是回传的数据！")
        }
        setResult(RESULT_OK, intent)
        finish()
    }
}