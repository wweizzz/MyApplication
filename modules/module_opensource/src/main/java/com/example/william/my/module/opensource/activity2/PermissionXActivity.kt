package com.example.william.my.module.opensource.activity2

import android.Manifest
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity
import com.example.william.my.basic.basic_module.router.path.ARouterPath
import com.example.william.my.library.utils.Utils
import com.permissionx.guolindev.PermissionX

@Route(path = ARouterPath.Opensource.PermissionX)
class PermissionXActivity : BasicResponseActivity() {

    override fun onResponseClick(view: View) {
        super.onResponseClick(view)

        requestPermission()
    }

    private fun requestPermission() {
        PermissionX.init(this)
            .permissions(
                Manifest.permission.CAMERA,
                Manifest.permission.POST_NOTIFICATIONS, // 消息通知
            )
            //.explainReasonBeforeRequest()
            .onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(
                    deniedList,
                    "PermissionX需要您同意以下权限才能正常使用",
                    "Allow",
                    "Deny"
                )
            }
            .onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(
                    deniedList,
                    "您需要手动在“设置”中允许必要的权限",
                    "OK",
                    "Cancel"
                )
            }
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    Utils.show("All permissions are granted: $grantedList")
                } else {
                    Utils.show("These permissions are denied: $deniedList")
                }
            }
    }

}