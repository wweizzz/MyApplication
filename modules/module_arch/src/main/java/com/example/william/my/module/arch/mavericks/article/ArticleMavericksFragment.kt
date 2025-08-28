package com.example.william.my.module.arch.mavericks.article

import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.fragmentViewModel
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.example.william.my.basic.basic_repo.bean.ArticleDetailData
import com.example.william.my.lib.fragment.BaseRecyclerFragment
import com.example.william.my.module.arch.adapter.ArticleAdapter

/**
 * Mavericks
 * https://airbnb.io/mavericks/
 */
class ArticleMavericksFragment : BaseRecyclerFragment<ArticleDetailData>(), MavericksView {

    private val viewModel: ArticleMavericksViewModel by fragmentViewModel()

    override fun initRecyclerAdapter(): BaseQuickAdapter<ArticleDetailData, QuickViewHolder> {
        return ArticleAdapter(arrayListOf())
    }

    override fun observeViewModel() {
        viewModel.onAsync(
            ArticleMavericksState::articleResponse, deliveryMode = uniqueOnly("error"),
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
        viewModel.loadArticle(mPage)
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