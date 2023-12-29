package com.example.william.my.module.room.paging.source

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.example.william.my.basic.basic_repository.api.NetworkApi
import com.example.william.my.basic.basic_repository.bean.Article
import com.example.william.my.basic.basic_repository.bean.ArticleData
import com.example.william.my.basic.basic_repository.data.source.ArticleRepository
import com.example.william.my.core.retrofit.response.RetrofitResponse
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class ArticleRxPagingSource(
    private val networkApi: NetworkApi,
    private val articleRepository: ArticleRepository
) :
    RxPagingSource<Int, Article>() {

    /**
     * getArticleSingle
     */
    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Article>> {
        // Start refresh at page 1 if undefined.
        val nextPageNumber = params.key ?: 0

        return networkApi
            .getArticleSingle(nextPageNumber)
            .subscribeOn(Schedulers.io())
            .map { response ->
                toLoadResult(response)
            }
            .onErrorReturn { throwable ->
                LoadResult.Error(throwable)
            }
    }

    private fun toLoadResult(response: RetrofitResponse<ArticleData>): LoadResult<Int, Article> {
        return LoadResult.Page(
            response.data!!.datas,
            null,  // Only paging forward.
            response.data!!.curPage,
            LoadResult.Page.COUNT_UNDEFINED,
            LoadResult.Page.COUNT_UNDEFINED
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        val anchorPosition = state.anchorPosition
            ?: return null

        val (_, prevKey, nextKey) = state.closestPageToPosition(anchorPosition)
            ?: return null

        if (prevKey != null) {
            return prevKey + 1
        }

        if (nextKey != null) {
            return nextKey - 1
        }

        return null
    }
}