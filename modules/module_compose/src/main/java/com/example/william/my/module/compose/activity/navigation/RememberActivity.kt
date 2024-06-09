package com.example.william.my.module.compose.activity.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.ImeAction
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.path.RouterPath

/**
 * 1.重组和持久化:
 * remember：在Composable函数重组时记住状态，但不涉及持久化。
 * rememberSaveable：在Composable函数重组时记住状态，并将其保存到持久存储中。
 * 2.使用场景:
 * remember通常用于需要在Composable函数重组时保持不变的状态。
 * rememberSaveable通常用于需要在应用关闭和重新启动后保持不变的状态。
 * 3.依赖项:
 * remember的依赖项通常是Composable函数中的状态或参数。
 * rememberSaveable的依赖项通常是Composable函数中的状态或参数，以及一个用于保存和恢复状态的键（key）。
 * 4.性能:
 * remember不会影响性能，因为它只在Composable函数重组时运行。
 * rememberSaveable可能会影响性能，因为它涉及到与持久存储的交互。
 */
@Route(path = RouterPath.Compose.Remember)
class RememberActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RememberExample()
        }
    }

    // listSaver, mapSaver
    @Composable
    fun RememberExample() {
        val state = remember { mutableStateOf("") }
        val state2 by remember { mutableStateOf("") }
        val (state3, setState3) = remember { mutableStateOf("") }

        val stateSaveable = rememberSaveable { mutableStateOf("") }
        val stateSaveable2 by rememberSaveable { mutableStateOf("") }
        val (stateSaveable3, setStateSaveable3) = rememberSaveable { mutableStateOf("") }

        val (text, setText) = rememberSaveable { mutableStateOf("") }

        var clickCount by remember { mutableIntStateOf(0) }
        val clickedALot by remember { derivedStateOf { clickCount >= 3 } }

        Column {

            // 显示当前文本状态的 Text
            Text(text = "Current text: $text")

            // 输入框，用户可以输入文本
            OutlinedTextField(
                value = text,
                onValueChange = setText,
                label = { Text("Enter text here") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    // 当用户点击键盘上的“完成”按钮时调用
                    println("Final text: $text")
                })
            )

            Button(onClick = { clickCount++ }) {
                Text(text = "Click me")
            }

            if (clickedALot) {
                Text(text = "You clicked a lot")
            }
        }
    }
}