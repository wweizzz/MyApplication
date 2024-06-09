package com.example.william.my.module.room.paging.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava3.RxRemoteMediator
import com.example.william.my.basic.basic_repo.api.ArticleApi
import com.example.william.my.basic.basic_repo.bean.ArticleData
import com.example.william.my.basic.basic_repo.bean.ArticleDetailData
import com.example.william.my.basic.basic_repo.data.source.ArticleRepository
import com.example.william.my.basic.basic_repo.database.ArticleDatabase
import com.example.william.my.module.room.paging.remotekey.RemoteKeyDatabase
import com.example.william.my.module.room.paging.remotekey.data.RemoteKeyData
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.Function
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class RxRemoteMediator(
    private val articleDatabase: ArticleDatabase,
    private val remoteKeyDatabase: RemoteKeyDatabase,
    private val networkApi: ArticleApi,
    private val repository: ArticleRepository<ArticleData, ArticleDetailData>
) : RxRemoteMediator<Int, ArticleDetailData>() {

    private val articleDao = articleDatabase.articleDao()
    private val remoteKeyDao = remoteKeyDatabase.remoteKeyDao()

    override fun loadSingle(
        loadType: LoadType,
        state: PagingState<Int, ArticleDetailData>
    ): Single<MediatorResult> {
        // The network load method takes an optional String parameter. For every page
        // after the first, pass the String token returned from the previous page to
        // let it continue from where it left off. For REFRESH, pass null to load the
        // first page.
        var remoteKeySingle: Single<RemoteKeyData>? = null
        remoteKeySingle = when (loadType) {
            LoadType.REFRESH -> {
                // Initial load should use null as the page key, so you can return null
                // directly.
                Single.just(RemoteKeyData(tag, null))
            }

            LoadType.PREPEND -> {
                // In this example, you never need to prepend, since REFRESH will always
                // load the first page in the list. Immediately return, reporting end of
                // pagination.
                return Single.just(MediatorResult.Success(true))
            }

            LoadType.APPEND -> {
                // Query remoteKeyDao for the next RemoteKey.
                remoteKeyDatabase.remoteKeyDao().remoteKeyByTagSingle("mQuery")
            }
        }
        return remoteKeySingle
            .subscribeOn(Schedulers.io())
            .flatMap(Function<RemoteKeyData, Single<MediatorResult>> { (_, nextPageKey): RemoteKeyData ->
                // You must explicitly check if the page key is null when appending,
                // since null is only valid for initial load. If you receive null
                // for APPEND, that means you have reached the end of pagination and
                // there are no more items to load.
                if (loadType != LoadType.REFRESH && nextPageKey == null) {
                    return@Function Single.just<MediatorResult>(MediatorResult.Success(true))
                }
                return@Function networkApi.getArticleSingle(nextPageKey!!)
                    .map { response ->
                        articleDatabase.runInTransaction {
                            if (loadType === LoadType.REFRESH) {
                                //remoteKeyDao.deleteByTag(tag)
                                //articleDao.deleteAllArticles()
                            }

                            // Update RemoteKey for this query.

                            // Insert new users into database, which invalidates the current
                            // PagingData, allowing Paging to present the updates in the DB.
                        }
                        MediatorResult.Success(response.data!!.datas.isEmpty())
                    }
            })
            .onErrorResumeNext { e: Throwable ->
                if (e is IOException || e is HttpException) {
                    return@onErrorResumeNext Single.just(MediatorResult.Error(e))
                }
                Single.error(e)
            }
    }

    companion object {
        private const val tag = "article"
    }
}
