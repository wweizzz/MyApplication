package com.example.william.my.module.compose.activity.smartrefresh

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.loren.component.view.composesmartrefresh.MyRefreshFooter
import com.loren.component.view.composesmartrefresh.MyRefreshHeader
import com.loren.component.view.composesmartrefresh.SmartSwipeRefresh
import com.loren.component.view.composesmartrefresh.SmartSwipeStateFlag
import com.loren.component.view.composesmartrefresh.ThresholdScrollStrategy
import com.loren.component.view.composesmartrefresh.rememberSmartSwipeRefreshState

@ExperimentalFoundationApi
@Route(path = RouterPath.Compose.SmartRefresh)
class SmartRefreshActivity : ComponentActivity() {

    private val mViewModel by viewModels<SmartRefreshViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {

            val refreshState = rememberSmartSwipeRefreshState().apply {
                this.needFirstRefresh = true
            }
            val scrollState = rememberLazyListState()

            val mainUiState = mViewModel.smartRefreshState.observeAsState()

            // 快速滚动头尾允许的阈值
            with(LocalDensity.current) {
                refreshState.dragHeaderIndicatorStrategy = ThresholdScrollStrategy.UnLimited
                refreshState.dragFooterIndicatorStrategy =
                    ThresholdScrollStrategy.Fixed(160.dp.toPx())
                refreshState.flingHeaderIndicatorStrategy = ThresholdScrollStrategy.None
                refreshState.flingFooterIndicatorStrategy =
                    ThresholdScrollStrategy.Fixed(80.dp.toPx())
            }



            Column {
                SmartSwipeRefresh(
                    modifier = Modifier.fillMaxSize(),
                    onRefresh = {
                        mViewModel.queryData(true)
                    },
                    onLoadMore = {
                        mViewModel.queryData(false)
                    },
                    state = refreshState,
                    headerIndicator = {
                        MyRefreshHeader(refreshState.refreshFlag, true)
                    },
                    footerIndicator = {
                        MyRefreshFooter(refreshState.loadMoreFlag, true)
                    },
                    contentScrollState = scrollState
                ) {
                    LaunchedEffect(mainUiState.value) {
                        mainUiState.value?.let {
                            if (it.isLoadMore) {
                                refreshState.loadMoreFlag = when (it.flag) {
                                    true -> SmartSwipeStateFlag.SUCCESS
                                    false -> SmartSwipeStateFlag.ERROR
                                }
                            } else {
                                refreshState.refreshFlag = when (it.flag) {
                                    true -> SmartSwipeStateFlag.SUCCESS
                                    false -> SmartSwipeStateFlag.ERROR
                                }
                            }
                        }
                    }

                    CompositionLocalProvider {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            state = scrollState
                        ) {
                            mainUiState.value?.data?.let {
                                items(it) { item ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .wrapContentHeight()
                                            .background(Color.LightGray)
                                            .padding(16.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Image(
                                            modifier = Modifier
                                                .width(32.dp)
                                                .height(32.dp),
                                            painter = painterResource(id = item.icon),
                                            contentDescription = null
                                        )
                                        Spacer(modifier = Modifier.width(16.dp))
                                        Text(text = item.title)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}