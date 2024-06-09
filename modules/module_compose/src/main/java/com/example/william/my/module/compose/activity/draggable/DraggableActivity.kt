package com.example.william.my.module.compose.activity.draggable

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.path.RouterPath
import kotlin.math.roundToInt

@Route(path = RouterPath.Compose.Draggable)
class DraggableActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DraggableExample()
        }
    }

    @Composable
    private fun DraggableExample() {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            var offsetX by remember { mutableFloatStateOf(0f) }

            Text(
                modifier = Modifier
                    .offset {
                        IntOffset(offsetX.roundToInt(), 0)
                    }
                    .draggable(
                        orientation = Orientation.Horizontal,
                        state = rememberDraggableState { delta ->
                            offsetX += delta
                        }
                    ),
                text = "Drag me!"
            )
        }
    }
}