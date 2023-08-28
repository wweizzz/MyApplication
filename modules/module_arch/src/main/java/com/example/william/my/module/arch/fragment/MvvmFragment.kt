package com.example.william.my.module.arch.fragment

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.QuickViewHolder
import com.example.william.my.basic.basic_repository.bean.Article
import com.example.william.my.library.fragment.BaseRecyclerFragment
import com.example.william.my.module.arch.adapter.ArticleAdapter
import com.example.william.my.module.arch.utils.obtainViewModel
import com.example.william.my.module.arch.viewmodel.ArticleLiveDataViewModel

/**
 * Model-View-ViewModel
 * 通过 ViewModel 将数据（Model）和 UI（View）隔离，再通过 LiveData 将数据和 UI 的绑定，实现数据驱动 UI，只要 LiveData 的数据修改 UI 能自动响应更新。
 */
class MvvmFragment : BaseRecyclerFragment<Article>() {

    private lateinit var viewModel: ArticleLiveDataViewModel

    override fun initRecyclerAdapter(): BaseQuickAdapter<Article, QuickViewHolder> {
        return ArticleAdapter(arrayListOf())
    }

    override fun observeViewModel() {
        viewModel = obtainViewModel()

        viewModel.article.observe(viewLifecycleOwner) { article ->
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