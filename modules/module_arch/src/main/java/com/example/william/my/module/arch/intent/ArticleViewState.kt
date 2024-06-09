package com.example.william.my.module.arch.intent

import com.example.william.my.basic.basic_repo.bean.ArticleDetailData

sealed class ArticleIntent {
    class LoadArticleIntent(val page: Int) : ArticleIntent()
}

sealed class ArticleViewState {
    object Loading : ArticleViewState()
    data class Success(val articles: List<ArticleDetailData>) : ArticleViewState()
    data class Error(val error: String?) : ArticleViewState()
}
