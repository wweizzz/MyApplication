package com.example.william.my.module.room.paging.source

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.example.william.my.basic.basic_repo.api.ArticleApi
import com.example.william.my.basic.basic_repo.bean.ArticleData
import com.example.william.my.basic.basic_repo.bean.ArticleDetailData
import com.example.william.my.basic.basic_repo.data.source.ArticleRepository
import com.example.william.my.core.retrofit.response.RetrofitResponse
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class ArticleRxPagingSource(
    private val networkApi: ArticleApi,
    private val articleRepository: ArticleRepository<ArticleData, ArticleDetailData>
) :
    RxPagingSource<Int, ArticleDetailData>() {

    /**
     * getArticleSingle
     */
    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, ArticleDetailData>> {
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

    private fun toLoadResult(response: RetrofitResponse<ArticleData>): LoadResult<Int, ArticleDetailData> {
        return LoadResult.Page(
            response.data!!.datas,
            null,  // Only paging forward.
            response.data!!.curPage,
            LoadResult.Page.COUNT_UNDEFINED,
            LoadResult.Page.COUNT_UNDEFINED
        )
    }

    override fun getRefreshKey(state: PagingState<Int, ArticleDetailData>): Int? {
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