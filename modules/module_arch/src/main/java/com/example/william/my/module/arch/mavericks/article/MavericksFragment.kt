package com.example.william.my.module.arch.mavericks.article

import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.fragmentViewModel
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.QuickViewHolder
import com.example.william.my.basic.basic_repository.bean.Article
import com.example.william.my.library.fragment.BaseRecyclerFragment
import com.example.william.my.module.arch.adapter.ArticleAdapter

/**
 * Mavericks
 * https://airbnb.io/mavericks/
 */
class MavericksFragment : BaseRecyclerFragment<Article>(), MavericksView {

    private val viewModel: ArticleViewModel by fragmentViewModel()

    override fun initRecyclerAdapter(): BaseQuickAdapter<Article, QuickViewHolder> {
        return ArticleAdapter(arrayListOf())
    }

    override fun observeViewModel() {
        viewModel.onAsync(ArticleState::articleData, deliveryMode = uniqueOnly("error"),
            onFail = {
                onDataFail()
            },
            onSuccess = {
                onDataSuccess(it.data?.datas ?: arrayListOf())
            }
        )

        queryData()
    }

    override fun queryData() {
        super.queryData()
        viewModel.loadArticle(page)
    }

    /**
     * 不推荐，需要判断状态，是Loading，还是Success，还是Fail
     */
    override fun invalidate() {
        //withState(viewModel) {
        //    onDataSuccess(it.articleData.invoke()?.data?.datas ?: arrayListOf())
        //}
    }
}