package com.example.william.my.module.arch.fragment

import androidx.fragment.app.viewModels
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.example.william.my.basic.basic_repo.bean.ArticleDetailData
import com.example.william.my.lib.recycler.BaseRecyclerFragment
import com.example.william.my.module.arch.adapter.ArticleAdapter
import com.example.william.my.module.arch.viewmodel.ArticleLiveDataViewModel

/**
 * Model-View-ViewModel
 * 通过 ViewModel 将数据（Model）和 UI（View）隔离，再通过 LiveData 将数据和 UI 的绑定，实现数据驱动 UI，只要 LiveData 的数据修改 UI 能自动响应更新。
 */
class MvvmFragment : BaseRecyclerFragment<ArticleDetailData>() {

    private val mViewModel: ArticleLiveDataViewModel by viewModels()

    override fun initRecyclerAdapter(): BaseQuickAdapter<ArticleDetailData, QuickViewHolder> {
        return ArticleAdapter(arrayListOf())
    }

    override fun observeViewModel() {
        mViewModel.articleResponse.observe(viewLifecycleOwner) { article ->
            article.data?.let {
                onDataSuccess(it.datas)
            }
        }

        queryData()
    }

    override fun queryData() {
        super.queryData()
        mViewModel.loadArticle(mPage)
    }
}