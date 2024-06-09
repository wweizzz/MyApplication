package com.example.william.my.module.compose.activity.basic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

/**
 * LaunchedEffect
 * 用于在Composable的生命周期内运行挂起代码块，通常用于处理副作用，如网络请求、数据库操作等。当LaunchedEffect的关键参数发生变化时，它会取消之前的任务并重新启动一个新的任务。
 * SideEffect
 * 用于在重组时运行代码，但不依赖于任何状态或效应参数。它通常用于执行一些与Composable状态无关的副作用，如更新系统设置、记录日志等。
 */
class EffectActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EffectExample()
        }
    }

    @Composable
    fun EffectExample() {
        Column {
            LaunchedEffectExample()
            SideEffectExample()
        }
    }

    @Composable
    fun LaunchedEffectExample() {
        // 用于显示消息的状态
        var message by remember { mutableStateOf("Initial message") }

        Column {
            Text(text = "Message: ${message}")
            LaunchedEffect(key1 = Unit) {
                delay(3000) // 模拟网络请求
                message = "Hello from LaunchedEffect"
            }
        }
    }

    @Composable
    fun SideEffectExample() {
        // 用于显示消息的状态
        val message by remember { mutableStateOf("Initial message") }

        Column {
            Text(text = "Message: ${message}")
            SideEffect {
                // 每次重组时都会执行
                println("SideEffect executed")
            }
        }
    }
}