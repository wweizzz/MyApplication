package com.example.william.my.module.compose.activity.smartrefresh

data class SmartRefreshStateData(
    val data: MutableList<TopicModel> = arrayListOf(),
    val isLoadMore: Boolean = false,
    val flag: Boolean = true
)