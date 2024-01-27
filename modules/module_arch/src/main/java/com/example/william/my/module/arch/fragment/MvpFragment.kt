package com.example.william.my.module.arch.fragment

import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.example.william.my.basic.basic_repository.bean.ArticleDetailData
import com.example.william.my.basic.basic_repository.data.ServiceLocator
import com.example.william.my.lib.fragment.BaseRecyclerFragment
import com.example.william.my.module.arch.adapter.ArticleAdapter
import com.example.william.my.module.arch.contract.ArticleContract
import com.example.william.my.module.arch.presenter.ArticlePresenter

/**
 * MVP：Model-View-Presenter
 */
class MvpFragment : BaseRecyclerFragment<ArticleDetailData>(), ArticleContract.View {

    private lateinit var presenter: ArticlePresenter

    override fun initRecyclerAdapter(): BaseQuickAdapter<ArticleDetailData, QuickViewHolder> {
        return ArticleAdapter(arrayListOf())
    }

    override fun observeViewModel() {
        super.observeViewModel()

        // Create the presenter
        presenter =
            ArticlePresenter(
                ServiceLocator.provideArticleRepository(requireActivity().applicationContext), this
            )

        queryData()
    }

    override fun queryData() {
        super.queryData()
        presenter.loadArticle(mPage)
    }

    override fun showArticle(articles: List<ArticleDetailData>) {
        onDataSuccess(articles)
    }

    override fun showNoArticle() {
        onDataFail()
    }
}