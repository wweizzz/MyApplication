package com.example.william.my.lib.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter4.BaseMultiItemAdapter
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.example.william.my.lib.databinding.BaseFragmentRecyclerViewBinding
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
abstract class BaseRecyclerFragment<T : Any> : BaseVBFragment<BaseFragmentRecyclerViewBinding>(),
    BaseQuickAdapter.OnItemClickListener<T>, BaseQuickAdapter.OnItemChildClickListener<T>,
    OnRefreshLoadMoreListener {

    protected var page: Int = 0

    protected var adapter: BaseQuickAdapter<T, QuickViewHolder>? = null
    protected var multiAdapter: BaseMultiItemAdapter<T>? = null
    protected var manager: RecyclerView.LayoutManager? = null

    override fun getViewBinding(): BaseFragmentRecyclerViewBinding {
        return BaseFragmentRecyclerViewBinding.inflate(layoutInflater)
    }

    override fun initView(view: View?, state: Bundle?) {
        super.initView(view, state)

        initRecyclerView()
        initRecyclerData(state)
    }

    private fun initRecyclerView() {
        mBinding.smartRefresh.setEnableRefresh(canRefresh())
        mBinding.smartRefresh.setEnableLoadMore(canLoadMore())
        mBinding.smartRefresh.setOnRefreshLoadMoreListener(this)

        scrollListener().forEach {
            mBinding.recyclerView.addOnScrollListener(it)
        }
        itemDecorations().forEach {
            mBinding.recyclerView.addItemDecoration(it)
        }
        adapter = initRecyclerAdapter()
        adapter?.let {
            it.setOnItemClickListener(this)
            mBinding.recyclerView.adapter = it
        }
        multiAdapter = initRecyclerMultiAdapter()
        multiAdapter?.let {
            it.setOnItemClickListener(this)
            mBinding.recyclerView.adapter = it
        }
        manager = initRecyclerManager()
        manager.let {
            mBinding.recyclerView.layoutManager = it
        }
    }

    open fun canRefresh(): Boolean {
        return true
    }

    open fun canLoadMore(): Boolean {
        return true
    }

    open fun scrollListener(): List<RecyclerView.OnScrollListener> {
        return arrayListOf()
    }

    open fun itemDecorations(): List<RecyclerView.ItemDecoration> {
        return arrayListOf()
    }

    open fun initRecyclerAdapter(): BaseQuickAdapter<T, QuickViewHolder>? {
        return null
    }

    open fun initRecyclerMultiAdapter(): BaseMultiItemAdapter<T>? {
        return null
    }

    open fun initRecyclerManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(activity)
    }

    open fun initRecyclerData(savedInstanceState: Bundle?) {}

    open fun queryData() {}

    fun scrollToTop() {
        mBinding.recyclerView.scrollToPosition(0)
    }

    fun showToast(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun onDataFail() {
        mBinding.smartRefresh.setEnableLoadMore(false)
    }

    fun onDataSuccess(list: List<T>?) {
        if (list.isNullOrEmpty()) {
            mBinding.smartRefresh.setEnableLoadMore(false)
        } else {
            if (page == 0) {
                adapter?.submitList(list)
                multiAdapter?.submitList(list)
            } else {
                adapter?.addAll(list)
                multiAdapter?.addAll(list)
            }
            mBinding.smartRefresh.setEnableLoadMore(canLoadMore())
        }
    }

    override fun onClick(adapter: BaseQuickAdapter<T, *>, view: View, position: Int) {

    }

    override fun onItemClick(adapter: BaseQuickAdapter<T, *>, view: View, position: Int) {

    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        page = 0
        queryData()
        mBinding.smartRefresh.finishRefresh(1000)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        page++
        queryData()
        mBinding.smartRefresh.finishLoadMore(1000)
    }
}