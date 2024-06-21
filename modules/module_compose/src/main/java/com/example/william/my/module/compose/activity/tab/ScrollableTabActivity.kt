package com.example.william.my.module.compose.activity.tab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.path.RouterPath
import kotlinx.coroutines.launch

@Route(path = RouterPath.Compose.ScrollableTab)
class ScrollableTabActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyScrollableTab()
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun MyScrollableTab() {

        val tabs = listOf("标签1", "标签2", "标签3", "标签4", "这是很长的标签5")
        val pagerState = rememberPagerState { tabs.size }
        val coroutineScope = rememberCoroutineScope()

        Column {
            ScrollableTabRow(
                selectedTabIndex = pagerState.currentPage,
                modifier = Modifier.wrapContentWidth(),
                edgePadding = 16.dp
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.scrollToPage(index)
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            HorizontalPager(
                state = pagerState,
            ) { page ->

                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "第${page + 1}个标签被选中了",
                )
            }
        }
    }
}