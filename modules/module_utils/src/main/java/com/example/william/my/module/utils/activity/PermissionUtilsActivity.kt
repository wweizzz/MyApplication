package com.example.william.my.module.utils.activity

import android.os.Build
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.SnackbarUtils
import com.example.william.my.basic.basic_module.activity.BasicRecyclerActivity
import com.example.william.my.basic.basic_module.router.path.RouterPath

/**
 * 当 targetSdkVersion >= 30时，
 * 如果要申请 ACCESS_BACKGROUND_LOCATION 权限，则需要先申请 ACCESS_FINE_LOCATION 权限或者 ACCESS_COARSE_LOCATION
 * 如果 ACCESS_BACKGROUND_LOCATION 权限跟 ACCESS_FINE_LOCATION 权限或者 ACCESS_COARSE_LOCATION 权限一起申请，则不会弹出权限申请对话框，如果还有其他权限一起申请，则会导致所有权限申请都不会弹窗。
 */
@Route(path = RouterPath.Utils.PermissionUtils)
class PermissionUtilsActivity : BasicRecyclerActivity() {

    override fun buildList(): ArrayList<String> {
        return arrayListOf(
            "requestCalendar",
            "requestWriteSettings",
            "requestDrawOverlays",
            "launchAppDetailsSettings",
        )
    }

    override fun onRecyclerClick(position: Int, string: String) {
        super.onRecyclerClick(position, string)
        when (position) {
            0 -> {
                requestCalendar()
            }

            1 -> {
                requestWriteSettings()
            }

            2 -> {
                requestDrawOverlays()
            }

            3 -> {
                launchAppDetailsSettings()
            }
        }
    }

    private fun requestCalendar() {
        PermissionUtils.permissionGroup(
            PermissionConstants.CALENDAR,  //日历
            PermissionConstants.CAMERA,  //相机
            PermissionConstants.CONTACTS,  //联系人
            PermissionConstants.LOCATION,  //位置
            PermissionConstants.MICROPHONE,  //麦克风
            PermissionConstants.PHONE,  //手机权限
            PermissionConstants.SENSORS,  //传感器
            PermissionConstants.SMS,  //短信
            PermissionConstants.STORAGE //存储
        )
            .callback(object : PermissionUtils.FullCallback {
                override fun onGranted(granted: MutableList<String>) {
                    showSnackBar(true, "Permission is granted")
                }

                override fun onDenied(
                    deniedForever: MutableList<String>,
                    denied: MutableList<String>
                ) {
                    if (deniedForever.isNotEmpty()) {
                        showSnackBar(false, "Permission is denied forever")
                    } else {
                        showSnackBar(false, "Permission is denied")
                    }
                }

            })
            .request()
    }

    /**
     * 申请修改系统权限
     */
    private fun requestWriteSettings() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionUtils.requestWriteSettings(object : PermissionUtils.SimpleCallback {
                override fun onGranted() {
                    showSnackBar(true, "Write Settings is granted")
                }

                override fun onDenied() {
                    showSnackBar(false, "Write Settings is denied")
                }
            })
        }
    }

    /**
     * 申请悬浮窗权限
     */
    private fun requestDrawOverlays() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionUtils.requestDrawOverlays(object : PermissionUtils.SimpleCallback {
                override fun onGranted() {
                    showSnackBar(true, "Draw Overlays is granted")
                }

                override fun onDenied() {
                    showSnackBar(false, "Draw Overlays is denied")
                }
            })
        }
    }

    /**
     * 启动应用程序详细信息设置
     */
    private fun launchAppDetailsSettings() {
        PermissionUtils.launchAppDetailsSettings()
    }

    private fun showSnackBar(isSuccess: Boolean, msg: String) {
        SnackbarUtils.with(mBinding.root).setDuration(SnackbarUtils.LENGTH_LONG).setMessage(msg)
            .apply {
                if (isSuccess) {
                    showSuccess()
                } else {
                    showError()
                }
            }
    }
}