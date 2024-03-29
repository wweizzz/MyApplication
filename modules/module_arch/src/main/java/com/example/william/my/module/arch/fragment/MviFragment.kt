package com.example.william.my.module.arch.fragment

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.example.william.my.basic.basic_repository.bean.ArticleDetailData
import com.example.william.my.lib.fragment.BaseRecyclerFragment
import com.example.william.my.module.arch.adapter.ArticleAdapter
import com.example.william.my.module.arch.intent.ArticleIntent
import com.example.william.my.module.arch.intent.ArticleViewState
import com.example.william.my.module.arch.utils.obtainViewModel
import com.example.william.my.module.arch.viewmodel.ArticleStateFlowViewModel
import kotlinx.coroutines.launch

/**
 * MVI：Model-View-Intent
 * 1. 将 LiveData 组件改成了 StateFlow
 * 2. ViewModel 传递给 View 的数据限制为 View 的 UIState
 */
class MviFragment : BaseRecyclerFragment<ArticleDetailData>() {

    private lateinit var viewModel: ArticleStateFlowViewModel

    override fun initRecyclerAdapter(): BaseQuickAdapter<ArticleDetailData, QuickViewHolder> {
        return ArticleAdapter(arrayListOf())
    }

    override fun observeViewModel() {
        viewModel = obtainViewModel()

        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    when (it) {
                        is ArticleViewState.Loading -> {

                        }

                        is ArticleViewState.Success -> {
                            onDataSuccess(it.articles)
                        }

                        is ArticleViewState.Error -> {
                            showToast(it.error)
                        }
                    }
                }
            }
        }

        queryData()
    }

    override fun queryData() {
        super.queryData()
        lifecycleScope.launch {
            viewModel.intent.send(ArticleIntent.LoadArticleIntent(mPage))
        }
    }

    private fun obtainViewModel(): ArticleStateFlowViewModel =
        obtainViewModel(ArticleStateFlowViewModel::class.java)
}