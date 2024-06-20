package com.example.william.my.module.compose.activity.backhandler

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.path.RouterPath

@Route(path = RouterPath.Compose.BackHandler)
class BackHandlerActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BackHandlerExample()
        }
    }

    @Composable
    fun BackHandlerExample() {

        val activity: Activity = LocalContext.current as Activity
        // 用于控制对话框显示的状态
        var showConfirmDialog by remember { mutableStateOf(false) }

        // 当用户尝试后退时，显示确认对话框
        BackHandler {
            showConfirmDialog = true
        }

        if (showConfirmDialog) {
            AlertDialog(
                onDismissRequest = {
                    showConfirmDialog = false
                },
                title = { Text("Confirm Exit") },
                text = { Text("Are you sure you want to exit?") },
                confirmButton = {
                    Button(onClick = {
                        activity.finish()
                    }) {
                        Text("Yes")
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        showConfirmDialog = false
                    }) {
                        Text("No")
                    }
                }
            )
        } else {
            // 你的主要内容
            Text("Press the back button to show the dialog.")
        }
    }

}