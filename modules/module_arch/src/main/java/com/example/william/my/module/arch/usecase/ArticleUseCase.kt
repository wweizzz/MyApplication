package com.example.william.my.module.arch.usecase

import com.example.william.my.basic.basic_repository.bean.ArticleListData
import com.example.william.my.basic.basic_repository.data.source.ArticleRepository
import com.example.william.my.core.retrofit.response.RetrofitResponse
import com.example.william.my.lib.usecase.SingleObserverUseCase
import io.reactivex.rxjava3.core.Single

class ArticleUseCase(private val repository: ArticleRepository) :
    SingleObserverUseCase<RetrofitResponse<ArticleListData>>() {

    private var page = 0
    fun setPage(page: Int) {
        this.page = page
    }

    override fun buildUseCaseObservable(): Single<RetrofitResponse<ArticleListData>> {
        return repository.getArticleSingle(page)
    }
}