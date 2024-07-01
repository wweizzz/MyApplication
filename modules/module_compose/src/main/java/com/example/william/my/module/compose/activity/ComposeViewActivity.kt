package com.example.william.my.module.compose.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.ComposeView
import com.example.william.my.module.compose.R

class ComposeViewActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_compose_view)

        findViewById<ComposeView>(R.id.compose_view).setContent {

        }
    }
}