package com.example.william.my.module.compose.activity.swipeable

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.path.RouterPath

@Route(path = RouterPath.Compose.AnchoredDraggable)
class AnchoredDraggableActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AnchoredDraggableExample()
        }
    }

    @Composable
    fun AnchoredDraggableExample() {

    }
}