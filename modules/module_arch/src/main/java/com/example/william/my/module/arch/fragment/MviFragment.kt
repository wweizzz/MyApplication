package com.example.william.my.module.arch.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.example.william.my.basic.basic_repo.bean.ArticleDetailData
import com.example.william.my.lib.fragment.BaseRecyclerFragment
import com.example.william.my.module.arch.adapter.ArticleAdapter
import com.example.william.my.module.arch.intent.ArticleIntent
import com.example.william.my.module.arch.intent.ArticleViewState
import com.example.william.my.module.arch.viewmodel.ArticleStateFlowViewModel
import kotlinx.coroutines.launch

/**
 * MVI：Model-View-Intent
 * 1. 将 LiveData 组件改成了 StateFlow
 * 2. ViewModel 传递给 View 的数据限制为 View 的 UIState
 */
class MviFragment : BaseRecyclerFragment<ArticleDetailData>() {

    private val mViewModel: ArticleStateFlowViewModel by viewModels()

    override fun initRecyclerAdapter(): BaseQuickAdapter<ArticleDetailData, QuickViewHolder> {
        return ArticleAdapter(arrayListOf())
    }

    override fun observeViewModel() {
        // 启动一个新的协程
        lifecycleScope.launch {
            // 限定生命周期
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.state.collect {
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
            mViewModel.intent.send(ArticleIntent.LoadArticleIntent(mPage))
        }
    }
}