package com.example.william.my.module.room.activity

import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import autodispose2.AutoDispose
import autodispose2.androidx.lifecycle.AndroidLifecycleScopeProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.path.ARouterPath
import com.example.william.my.basic.basic_repository.data.ServiceLocator
import com.example.william.my.lib.activity.BaseVBActivity
import com.example.william.my.lib.utils.Utils
import com.example.william.my.module.sample.databinding.SampleActivityPagingBinding
import com.example.william.my.module.room.paging.adapter.PagingAdapter
import com.example.william.my.module.room.paging.adapter.PagingStateAdapter
import com.example.william.my.module.room.paging.viewmodel.PagingViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


/**
 * Paging
 * https://developer.android.google.cn/topic/libraries/architecture/paging/v3-overview
 */
@Route(path = ARouterPath.Sample.Paging)
class PagingActivity : BaseVBActivity<SampleActivityPagingBinding>() {

    private val mViewModel: com.example.william.my.module.room.paging.viewmodel.PagingViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val database =
                    ServiceLocator.provideArticleDatabase(application)
                val networkApi =
                    ServiceLocator.provideArticleApi()
                val repository =
                    ServiceLocator.provideArticleRepository(application)
                @Suppress("UNCHECKED_CAST")
                return com.example.william.my.module.room.paging.viewmodel.PagingViewModel(
                    database,
                    networkApi,
                    repository
                ) as T
            }
        }
    }

    private lateinit var mAdapter: com.example.william.my.module.room.paging.adapter.PagingAdapter

    override fun getViewBinding(): SampleActivityPagingBinding {
        return SampleActivityPagingBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()

        initPaging()
    }

    private fun initPaging() {
        mAdapter =
            com.example.william.my.module.room.paging.adapter.PagingAdapter(com.example.william.my.module.room.paging.adapter.PagingAdapter.PagingComparator())

        initArticles(mViewModel, mAdapter)

        //获取加载状态
        mAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.NotLoading -> Utils.d("Paging", "is NotLoading")
                is LoadState.Loading -> Utils.d("Paging", "is Loading")
                is LoadState.Error -> Utils.d("Paging", "is Error")
            }
        }

        //呈现加载状态
        mAdapter.withLoadStateHeaderAndFooter(
            header = com.example.william.my.module.room.paging.adapter.PagingStateAdapter(mAdapter::retry),
            footer = com.example.william.my.module.room.paging.adapter.PagingStateAdapter(mAdapter::retry)
        )

        mBinding.pagingRecycleView.adapter = mAdapter
    }

    private fun initArticles(
        viewModel: com.example.william.my.module.room.paging.viewmodel.PagingViewModel,
        adapter: com.example.william.my.module.room.paging.adapter.PagingAdapter
    ) {
        lifecycleScope.launch {
            viewModel.articles.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun initArticleFlow(
        viewModel: com.example.william.my.module.room.paging.viewmodel.PagingViewModel,
        adapter: com.example.william.my.module.room.paging.adapter.PagingAdapter
    ) {
        lifecycleScope.launch {
            viewModel.articleFlow.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun initArticleFlowable(
        viewModel: com.example.william.my.module.room.paging.viewmodel.PagingViewModel,
        adapter: com.example.william.my.module.room.paging.adapter.PagingAdapter
    ) {
        viewModel.articleFlowable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .to(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
            .subscribe {
                adapter.submitData(lifecycle, it)
            }
    }

    private fun initArticleLiveData(
        viewModel: com.example.william.my.module.room.paging.viewmodel.PagingViewModel,
        adapter: com.example.william.my.module.room.paging.adapter.PagingAdapter
    ) {
        viewModel.articleLiveData.observe(this@PagingActivity) { pagingData ->
            lifecycleScope.launch {
                adapter.submitData(pagingData)
            }
        }
    }
}