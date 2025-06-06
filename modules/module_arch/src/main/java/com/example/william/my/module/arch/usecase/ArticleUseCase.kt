package com.example.william.my.module.arch.usecase

import com.example.william.my.basic.basic_repo.bean.ArticleData
import com.example.william.my.basic.basic_repo.bean.ArticleDetailData
import com.example.william.my.basic.basic_repo.data.source.ArticleRepository
import com.example.william.my.core.retrofit.response.RetrofitResponse
import com.example.william.my.lib.usecase.SingleObserverUseCase
import io.reactivex.rxjava3.core.Single

class ArticleUseCase(private val repository: ArticleRepository<ArticleData, ArticleDetailData>) :
    SingleObserverUseCase<RetrofitResponse<ArticleData>>() {

    private var page = 0
    fun setPage(page: Int) {
        this.page = page
    }

    override fun buildUseCaseObservable(): Single<RetrofitResponse<ArticleData>> {
        return repository.getArticleSingle(page)
    }
}