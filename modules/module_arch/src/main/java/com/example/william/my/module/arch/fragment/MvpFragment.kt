package com.example.william.my.module.arch.fragment

import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.example.william.my.basic.basic_repository.bean.Article
import com.example.william.my.basic.basic_repository.data.ServiceLocator
import com.example.william.my.lib.fragment.BaseRecyclerFragment
import com.example.william.my.module.arch.adapter.ArticleAdapter
import com.example.william.my.module.arch.contract.ArticleContract
import com.example.william.my.module.arch.presenter.ArticlePresenter

/**
 * MVP：Model-View-Presenter
 */
class MvpFragment : BaseRecyclerFragment<Article>(), ArticleContract.View {

    private lateinit var articlePresenter: ArticlePresenter

    override fun initRecyclerAdapter(): BaseQuickAdapter<Article, QuickViewHolder> {
        return ArticleAdapter(arrayListOf())
    }

    override fun observeViewModel() {
        super.observeViewModel()

        // Create the presenter
        articlePresenter =
            ArticlePresenter(
                ServiceLocator.provideArticleRepository(requireActivity().applicationContext), this
            )

        queryData()
    }

    override fun queryData() {
        super.queryData()
        articlePresenter.loadArticle(page)
    }

    override fun showArticle(article: List<Article>) {
        onDataSuccess(article)
    }

    override fun showNoArticle() {
        onDataFail()
    }
}