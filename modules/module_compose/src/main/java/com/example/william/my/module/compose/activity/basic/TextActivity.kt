package com.example.william.my.module.compose.activity.basic

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.path.RouterPath

@Route(path = RouterPath.Compose.Text)
class TextActivity : ComponentActivity() {

    private val TAG = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TextExample("Hello Text")
        }
    }

    @Composable
    fun TextExample(str: String) {
        Text(
            text = str,
            modifier = Modifier
                .padding(6.dp)
                .clickable {
                    Log.e(TAG, "clickable")
                },
            color = Color.Black,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            textDecoration = TextDecoration.Underline,
            fontStyle = FontStyle.Italic,
            maxLines = 1
        )
    }
}