package com.example.william.my.module.arch.fragment

import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.example.william.my.basic.basic_repository.bean.ArticleDetailData
import com.example.william.my.lib.fragment.BaseRecyclerFragment
import com.example.william.my.module.arch.adapter.ArticleAdapter
import com.example.william.my.module.arch.utils.obtainViewModel
import com.example.william.my.module.arch.viewmodel.ArticleLiveDataViewModel

/**
 * Model-View-ViewModel
 * 通过 ViewModel 将数据（Model）和 UI（View）隔离，再通过 LiveData 将数据和 UI 的绑定，实现数据驱动 UI，只要 LiveData 的数据修改 UI 能自动响应更新。
 */
class MvvmFragment : BaseRecyclerFragment<ArticleDetailData>() {

    private lateinit var viewModel: ArticleLiveDataViewModel

    override fun initRecyclerAdapter(): BaseQuickAdapter<ArticleDetailData, QuickViewHolder> {
        return ArticleAdapter(arrayListOf())
    }

    override fun observeViewModel() {
        viewModel = obtainViewModel()

        viewModel.articleResponse.observe(viewLifecycleOwner) { article ->
            article.data?.let {
                onDataSuccess(it.datas)
            }
        }

        queryData()
    }

    override fun queryData() {
        super.queryData()
        viewModel.loadArticle(page)
    }

    private fun obtainViewModel(): ArticleLiveDataViewModel =
        obtainViewModel(ArticleLiveDataViewModel::class.java)
}