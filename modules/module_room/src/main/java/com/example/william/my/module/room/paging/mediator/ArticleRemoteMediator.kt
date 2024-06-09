package com.example.william.my.module.room.paging.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.william.my.basic.basic_repo.api.ArticleApi
import com.example.william.my.basic.basic_repo.bean.ArticleData
import com.example.william.my.basic.basic_repo.bean.ArticleDetailData
import com.example.william.my.basic.basic_repo.data.source.ArticleRepository
import com.example.william.my.basic.basic_repo.database.ArticleDatabase
import com.example.william.my.lib.utils.Utils
import com.example.william.my.module.room.paging.remotekey.RemoteKeyDatabase
import com.example.william.my.module.room.paging.remotekey.data.RemoteKeyData
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class ArticleRemoteMediator(
    private val articleDatabase: ArticleDatabase,
    private val remoteKeyDatabase: RemoteKeyDatabase,
    private val networkApi: ArticleApi,
    private val repository: ArticleRepository<ArticleData, ArticleDetailData>
) : RemoteMediator<Int, ArticleDetailData>() {

    private val articleDao = articleDatabase.articleDao()
    private val remoteKeyDao = remoteKeyDatabase.remoteKeyDao()

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)
        return if (System.currentTimeMillis() - (remoteKeyDao.lastUpdated() ?: 0L) <
            cacheTimeout
        ) {
            // Cached data is up-to-date, so there is no need to re-fetch
            // from the network.
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            // Need to refresh cached data from network; returning
            // LAUNCH_INITIAL_REFRESH here will also block RemoteMediator's
            // APPEND and PREPEND from running until REFRESH succeeds.
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleDetailData>
    ): MediatorResult {
        return try {
            // 网络负载方法采用可选的String参数。
            // 对于第一个页面之后的每个页面，传递上一个页面返回的 String 令牌，使其从停止的地方继续。
            // 对于REFRESH，传递 null 以加载第一个页面。
            // The network load method takes an optional String
            // parameter. For every page after the first, pass the String
            // token returned from the previous page to let it continue
            // from where it left off. For REFRESH, pass null to load the
            // first page.
            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    Utils.d("RemoteMediator", "LoadType REFRESH")

                    null
                }
                // 在本例中，您永远不需要预先准备
                // 因为 REFRESH 将始终加载列表中的第一页。立即返回，报告分页结束。
                // In this example, you never need to prepend, since REFRESH
                // will always load the first page in the list. Immediately
                // return, reporting end of pagination.
                LoadType.PREPEND -> {

                    Utils.d("RemoteMediator", "LoadType PREPEND")

                    return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                }

                LoadType.APPEND -> {

                    Utils.d("RemoteMediator", "LoadType APPEND")

//                    // 获取下一个 RemoteKey 的最后一个 Article 对象 id。
//                    // Get the last Article object id for the next RemoteKey.
//                    val lastItem = state.lastItemOrNull()
//                        ?: return MediatorResult.Success(
//                            endOfPaginationReached = true
//                        )
//                    // 追加时必须明确检查最后一项是否为 null，因为将null传递给networkService仅对初始加载有效。
//                    // 如果 lastItem 为 null，则表示在初始 REFRESH 之后没有加载任何项，并且没有更多的项要加载。
//                    // You must explicitly check if the last item is null when
//                    // appending, since passing null to networkService is only
//                    // valid for initial load. If lastItem is null it means no
//                    // items were loaded after the initial REFRESH and there are
//                    // no more items to load.
//
//                    lastItem.id

                    // 查询 remoteKeyDao 以获取下一个 RemoteKey。
                    // Query remoteKeyDao for the next RemoteKey.
                    //val remoteKey = getRemoteKeyForLastItem(state)
                    val remoteKey = articleDatabase.withTransaction {
                        remoteKeyDao.remoteKeyByTag(tag)
                    }

                    // 追加时必须明确检查最后一项是否为 null，因为将 null 传递给 networkService 仅对初始加载有效
                    // 如果 lastItem 为 null，则表示在初始 REFRESH 之后没有加载任何项，并且没有更多的项要加载。
                    // You must explicitly check if the page key is null when
                    // appending, since null is only valid for initial load.
                    // If you receive null for APPEND, that means you have
                    // reached the end of pagination and there are no more
                    // items to load.
                    val nextKey = remoteKey?.nextPageKey
                    nextKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKey != null)
                }
            }

            // 通过改装挂起网络负载。
            // 这不需要封装在withContext（Dispatcher.IO）｛…｝块中，
            // 因为Reform的Coroutine CallAdapter在工作线程上进行调度。
            // Suspending network load via Retrofit. This doesn't need to
            // be wrapped in a withContext(Dispatcher.IO) { ... } block
            // since Retrofit's Coroutine CallAdapter dispatches on a
            // worker thread.
            val page = loadKey ?: 0
            val response = networkApi.getArticleSuspend(page)

            val articles = response.data!!.datas

            // 将加载的数据和下一个密钥存储在事务中，以便它们始终保持一致。
            // Store loaded data, and next key in transaction, so that
            // they're always consistent.
            articleDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeyDao.deleteByTag(tag)
                    articleDao.deleteAllArticles()
                }

                val curPage = response.data!!.curPage
                val nextPage = if (articles.isEmpty()) null else curPage

                // 更新查询的 RemoteKey。
                // Update RemoteKey for this query.
                remoteKeyDao.insertKey(RemoteKeyData(tag, nextPage))

                // 将新用户插入数据库，这将使当前的 PagingData 无效，从而允许 Paging 在数据库中显示更新。
                // Insert new users into database, which invalidates the
                // current PagingData, allowing Paging to present the updates
                // in the DB.
                articleDao.insertArticles(articles.onEachIndexed { _, article ->
                    article.page = curPage
                })
            }

            // 如果没有从服务返回用户，则到达分页结束
            // End of pagination has been reached if no users are returned from the
            // service
            MediatorResult.Success(
                endOfPaginationReached = articles.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    companion object {
        private const val tag = "article"
    }
}
