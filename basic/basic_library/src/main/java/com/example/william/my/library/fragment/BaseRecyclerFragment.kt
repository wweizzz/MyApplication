package com.example.william.my.library.fragment

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.example.william.my.library.databinding.BaseFragmentRecyclerViewBinding
import com.example.william.my.library.view.IEmptyView
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
abstract class BaseRecyclerFragment<T : Any> : BaseVBFragment<BaseFragmentRecyclerViewBinding>(),
    BaseQuickAdapter.OnItemClickListener<T>, BaseQuickAdapter.OnItemChildClickListener<T>,
    OnRefreshLoadMoreListener {

    protected var page: Int = 0
    protected var canRefresh = true

    protected var onScrollListener: RecyclerView.OnScrollListener? = null
    protected var itemDecoration: RecyclerView.ItemDecoration? = null

    protected lateinit var adapter: BaseQuickAdapter<T, QuickViewHolder>
    protected lateinit var manager: RecyclerView.LayoutManager

    override fun getViewBinding(): BaseFragmentRecyclerViewBinding {
        return BaseFragmentRecyclerViewBinding.inflate(layoutInflater)
    }

    override fun initView(view: View?, state: Bundle?) {
        super.initView(view, state)

        initRecyclerView()
        initRecyclerData(state)
    }

    private fun initRecyclerView() {
        mBinding.smartRefresh.setEnableLoadMore(false)
        mBinding.smartRefresh.setEnableRefresh(canRefresh)
        mBinding.smartRefresh.setOnRefreshLoadMoreListener(this)

        mBinding.recyclerView.isNestedScrollingEnabled = true

        onScrollListener = initOnScrollListener()
        onScrollListener?.let {
            mBinding.recyclerView.addOnScrollListener(it)
        }
        itemDecoration = initItemDecoration()
        itemDecoration?.let {
            mBinding.recyclerView.addItemDecoration(it)
        }
        adapter = initRecyclerAdapter()
        adapter.let {
            mBinding.recyclerView.adapter = adapter
            adapter.setOnItemClickListener(this)
        }
        manager = initRecyclerManager()
        manager.let {
            mBinding.recyclerView.layoutManager = manager
        }

        // 是否使用空布局（默认 false）
        adapter.isStateViewEnable = true
    }

    open fun initOnScrollListener(): RecyclerView.OnScrollListener? {
        return null
    }

    open fun initItemDecoration(): RecyclerView.ItemDecoration? {
        return null
    }

    protected abstract fun initRecyclerAdapter(): BaseQuickAdapter<T, QuickViewHolder>

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
        if (adapter.items.isEmpty()) {
            onEmptyView()
        }
        mBinding.smartRefresh.setEnableLoadMore(false)
    }

    fun onDataSuccess(list: List<T>?) {
        if (list.isNullOrEmpty()) {
            mBinding.smartRefresh.setEnableLoadMore(false)
        } else {
            if (page == 0) {
                adapter.submitList(list)
            } else {
                adapter.addAll(list)
            }
            mBinding.smartRefresh.setEnableLoadMore(true)
        }
    }

    private fun onEmptyView() {
        if (adapter.items.isEmpty()) {
            val textView = TextView(activity)
            textView.gravity = Gravity.CENTER
            textView.text = "无数据"
            adapter.stateView = textView
        }
        mBinding.smartRefresh.setEnableLoadMore(false)
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