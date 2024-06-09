package com.example.william.my.module.compose.activity.basic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.module.compose.R

@Route(path = RouterPath.Compose.Image)
class ImageActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImageExample()
        }
    }

    @Composable
    fun ImageExample() {
        Image(
            painter = painterResource(R.drawable.ic_launcher),
            contentDescription = "",
        )
    }
}