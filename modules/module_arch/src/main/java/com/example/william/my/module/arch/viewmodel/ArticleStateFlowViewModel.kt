/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.william.my.module.arch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.william.my.basic.basic_repo.bean.ArticleData
import com.example.william.my.basic.basic_repo.bean.ArticleDetailData
import com.example.william.my.basic.basic_repo.data.source.ArticleRepository
import com.example.william.my.module.arch.intent.ArticleIntent
import com.example.william.my.module.arch.intent.ArticleViewState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class ArticleStateFlowViewModel(private val repository: ArticleRepository<ArticleData, ArticleDetailData>) : ViewModel() {

    val intent = Channel<ArticleIntent>(Channel.UNLIMITED)

    private val _state = MutableStateFlow<ArticleViewState>(ArticleViewState.Loading)
    val state: StateFlow<ArticleViewState>
        get() = _state

    init {
        // 启动一个新的协程
        viewModelScope.launch {
            // 将 Channel 转换为 flow
            intent.consumeAsFlow().collect {
                when (it) {
                    is ArticleIntent.LoadArticleIntent -> loadArticle(it.page)
                }
            }
        }
    }

    private fun loadArticle(page: Int) {
        // 启动一个新的协程
        viewModelScope.launch {
            _state.value = ArticleViewState.Loading
            _state.value =
                try {
                    val response = repository.getArticleSuspend(page)
                    ArticleViewState.Success(response.data!!.datas)
                } catch (e: Exception) {
                    ArticleViewState.Error(e.message)
                }
        }
    }
}