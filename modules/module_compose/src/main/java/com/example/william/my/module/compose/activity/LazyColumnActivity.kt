package com.example.william.my.module.compose.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.alibaba.android.arouter.exception.HandlerException
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.william.my.basic.basic_module.router.item.RouterItem
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.module.compose.activity.dragging.GuaguaCardActivity

@Route(path = RouterPath.Compose.Main)
class LazyColumnActivity : ComponentActivity() {

    private val routerItems: ArrayList<RouterItem> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        buildRouterItems()

        setContent {
            LazyColumnExample(routerItems)
        }
    }

    private fun buildRouterItems() {
        routerItems.add(RouterItem("Text", RouterPath.Compose.Text))
        routerItems.add(RouterItem("Button", RouterPath.Compose.Button))
        routerItems.add(RouterItem("Image", RouterPath.Compose.Image))
        routerItems.add(RouterItem("Canvas", RouterPath.Compose.Canvas))
        routerItems.add(RouterItem("ConstraintLayout", RouterPath.Compose.ConstraintLayout))
        routerItems.add(RouterItem("HorizontalPager", RouterPath.Compose.HorizontalPager))

        routerItems.add(RouterItem("CompositionLocal", RouterPath.Compose.CompositionLocal))

        routerItems.add(RouterItem("NavHost", RouterPath.Compose.NavHost))
        routerItems.add(RouterItem("BackHandler", RouterPath.Compose.BackHandler))
        routerItems.add(RouterItem("Remember", RouterPath.Compose.Remember))

        routerItems.add(RouterItem("Draggable", RouterPath.Compose.Draggable))
        routerItems.add(RouterItem("DragGestures", RouterPath.Compose.DragGestures))

        routerItems.add(RouterItem("AnchoredDraggable", RouterPath.Compose.AnchoredDraggable))

        routerItems.add(RouterItem("GuaguaCard", RouterPath.Compose.GuaguaCard))
    }
}

@Composable
fun LazyColumnExample(itemsList: List<RouterItem>) {
    // 使用 rememberLazyListState 保存滚动的位置
    val scrollState = rememberLazyListState()

    LazyColumn(state = scrollState) {
        items(itemsList) { item ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(6.dp)
                    .height(48.dp)
                    .background(color = Color.White)
            ) {
                LazyColumnItemExample(item) {
                    try {
                        ARouter.getInstance().build(item.mRouterPath).navigation()
                    } catch (e: HandlerException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}

@Composable
fun LazyColumnItemExample(routerItem: RouterItem? = null, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(6.dp)
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 6.dp,
            pressedElevation = 6.dp
        ),
        border = BorderStroke(
            width = 1.dp,
            color = Color.Black,
        )
    ) {
        Text(text = routerItem?.mRouterName ?: "")
    }
}