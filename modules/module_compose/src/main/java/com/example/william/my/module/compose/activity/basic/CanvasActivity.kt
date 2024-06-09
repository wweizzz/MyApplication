package com.example.william.my.module.compose.activity.basic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.path.RouterPath

@Route(path = RouterPath.Compose.Canvas)
class CanvasActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CanvasExample()
        }
    }

    @Composable
    fun CanvasExample() {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            // 绘制一条从左下角到右上角的蓝色的线
            drawLine(
                start = Offset(x = canvasWidth, y = 0f),
                end = Offset(x = 0f, y = canvasHeight),
                color = Color.Blue
            )

            // 在以 200，1200 位置 120 为半径绘制一个圆
            drawCircle(
                color = Color.Blue,
                radius = 120f,
                center = Offset(200f, 200f),
            )
        }
    }
}