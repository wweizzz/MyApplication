package com.example.william.my.module.sample.activity.androidx

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.View
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.lib.utils.Utils

/**
 * ActivityResultContracts
 */
@Route(path = RouterPath.Sample.ActivityResultContract)
class ActivityResultActivity : BasicResponseActivity() {

    override fun onResponseClick(view: View) {
        super.onResponseClick(view)

        //launcher.launch("input")
        val intent = Intent(this, ActivityResultActivity2::class.java).apply {
            putExtra("input", "input")
        }
        startActivityForResult.launch(intent)
    }

    // 创建 ActivityResultLauncher
    private val startActivityForResult =
        registerForActivityResult(StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val result = it.data?.getStringExtra("result")
                showResponse("result value is :${result}")
            }
        }

    //自定义 ActivityResultContract
    private val launcher =
        registerForActivityResult(CustomResultContract()) { result ->
            showResponse("result value is :${result}")
        }

    //打开相机拍照
    private val takePicturePreview =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {

        }

    //获取单个权限请求
    private var requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {// 同意
                Utils.show("您同意了权限申请")
            } else {// 拒绝
                Utils.show("您拒绝了权限申请")
            }
        }

    //获取多个权限请求
    private var requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if (it[Manifest.permission.CAMERA]!!) {// 同意
                Utils.show("您同意了权限申请")
            } else {// 拒绝
                Utils.show("您拒绝了权限申请")
            }
        }

    /**
     * 自定义ActivityResultContract
     */
    class CustomResultContract : ActivityResultContract<String, String?>() {

        override fun createIntent(context: Context, input: String): Intent {
            return Intent(context, ActivityResultActivity2::class.java).apply {
                putExtra("input", input)
            }
        }

        override fun parseResult(resultCode: Int, intent: Intent?): String? {
            val data = intent?.getStringExtra("result")
            return if (resultCode == Activity.RESULT_OK && !TextUtils.isEmpty(data)) data else "null"
        }
    }
}