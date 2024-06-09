package com.example.william.my.module.compose.activity.basic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.path.RouterPath

/**
 * CompositionLocal 是通过组合隐式向下传递数据的工具
 */
@Route(path = RouterPath.Compose.CompositionLocal)
class CompositionLocalActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CompositionLocalExample()
        }
    }

    data class User(val name: String)

    //编译器会提示变量名应该以“Local”为前缀
    private val LocalUser = compositionLocalOf { User("张三") }
    //不想提供或无法提供有意义的默认值，可以直接抛异常。
    //val LocalUser = compositionLocalOf { error("LocalUser没有提供值！") }

    @Composable
    fun CompositionLocalExample() {
        Column {
            //CompositionLocalProvider函数提供作用域，provides中缀表达式提供修改，current取出当前值
            CompositionLocalProvider(LocalUser provides User("李四")) {
                val newUser = LocalUser.current //这里取出的值是修改过的“李四”
                Text(text = newUser.name)
            }
            val oldUser = LocalUser.current    //这里取出的值是未修改过的“张三”
            Text(text = oldUser.name)
        }
    }
}