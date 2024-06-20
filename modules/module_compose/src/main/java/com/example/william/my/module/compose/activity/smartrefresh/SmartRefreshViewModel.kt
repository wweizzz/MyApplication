package com.example.william.my.module.compose.activity.smartrefresh

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SmartRefreshViewModel : ViewModel() {

    private val _smartRefreshState: MutableLiveData<SmartRefreshStateData> = MutableLiveData()

    val smartRefreshState: LiveData<SmartRefreshStateData>
        get() = _smartRefreshState

    private val topics = listOf(
        TopicModel("Arts & Crafts", RandomIcon.icon()),
        TopicModel("Beauty", RandomIcon.icon()),
        TopicModel("Books", RandomIcon.icon()),
        TopicModel("Business", RandomIcon.icon()),
        TopicModel("Comics", RandomIcon.icon()),
        TopicModel("Culinary", RandomIcon.icon()),
        TopicModel("Design", RandomIcon.icon()),
        TopicModel("Writing", RandomIcon.icon()),
        TopicModel("Religion", RandomIcon.icon()),
        TopicModel("Technology", RandomIcon.icon()),
        TopicModel("Social sciences", RandomIcon.icon()),
        TopicModel("Arts & Crafts", RandomIcon.icon()),
        TopicModel("Beauty", RandomIcon.icon()),
        TopicModel("Books", RandomIcon.icon()),
        TopicModel("Business", RandomIcon.icon()),
        TopicModel("Comics", RandomIcon.icon()),
        TopicModel("Culinary", RandomIcon.icon()),
        TopicModel("Design", RandomIcon.icon()),
        TopicModel("Writing", RandomIcon.icon()),
        TopicModel("Religion", RandomIcon.icon()),
        TopicModel("Technology", RandomIcon.icon()),
        TopicModel("Social sciences", RandomIcon.icon())
    )

    private var flag = true // 模拟成功失败

    fun queryData(isRefresh: Boolean) {
        viewModelScope.launch {
            runCatching {
                delay(2000)
                if (!flag) {
                    throw Exception("error")
                }
                if (isRefresh) {
                    SmartRefreshStateData(
                        isLoadMore = false,
                        data = topics.toMutableList().apply {
                            this[0] = this[0].copy(title = System.currentTimeMillis().toString())
                        },
                        flag = true
                    )
                } else {
                    SmartRefreshStateData(
                        isLoadMore = true,
                        data = (_smartRefreshState.value?.data ?: mutableListOf()).apply {
                            addAll(topics)
                        },
                        flag = true
                    )
                }
            }.onSuccess {
                _smartRefreshState.value = it
            }.onFailure {
                _smartRefreshState.value = _smartRefreshState.value?.copy(isLoadMore = !isRefresh, flag = false)
            }
            flag = !flag
        }
    }
}
