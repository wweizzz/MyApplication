package com.example.william.my.lib.recycler

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter4.BaseMultiItemAdapter
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.QuickAdapterHelper
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener

/**
 * RecyclerView通用处理委托类
 * 抽离Fragment的公共业务逻辑，使用委托模式实现代码复用
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 * LayoutManager -> Adapter -> ItemDecoration -> OnScrollListener
 */
class BaseRecyclerHandler<T : Any>(
    private val host: RecyclerViewHost<T>
) : BaseQuickAdapter.OnItemClickListener<T>,
    BaseQuickAdapter.OnItemChildClickListener<T>,
    OnRefreshLoadMoreListener {

    // ===== 分页相关 =====
    var mPage: Int = 0
    var mPageSize: Int = 20

    // ===== RecyclerView相关组件 =====
    var mLayoutManager: RecyclerView.LayoutManager? = null
    var mAdapter: BaseQuickAdapter<T, QuickViewHolder>? = null
    var mMultiItemAdapter: BaseMultiItemAdapter<T>? = null
    lateinit var mAdapterHelper: QuickAdapterHelper

    /**
     * 初始化RecyclerView
     */
    fun initRecyclerView() {
        val binding = host.getHostBinding()

        // 设置SmartRefreshLayout
        binding.smartRefresh.setEnableOverScrollDrag(true)
        binding.smartRefresh.setNestedScrollingEnabled(false)
        binding.smartRefresh.setEnableRefresh(host.canRefresh())
        binding.smartRefresh.setEnableLoadMore(host.canLoadMore())
        binding.smartRefresh.setOnRefreshLoadMoreListener(this)

        // 设置LayoutManager
        mLayoutManager = host.initRecyclerManager()
        mLayoutManager?.let {
            binding.recyclerView.layoutManager = it
        }

        // 设置Adapter
        mAdapter = host.initRecyclerAdapter()
        mAdapter?.let {
            it.setOnItemClickListener(this)
            mAdapterHelper = QuickAdapterHelper.Builder(it).build()
        }

        mMultiItemAdapter = host.initRecyclerMultiAdapter()
        mMultiItemAdapter?.let {
            it.setOnItemClickListener(this)
            mAdapterHelper = QuickAdapterHelper.Builder(it).build()
        }

        binding.recyclerView.adapter = mAdapterHelper.adapter

        // 添加装饰器
        host.itemDecorations().forEach {
            binding.recyclerView.addItemDecoration(it)
        }

        // 添加滚动监听器
        host.scrollListener().forEach {
            binding.recyclerView.addOnScrollListener(it)
        }
    }

    /**
     * 设置RecyclerView状态视图
     */
    fun setRecyclerViewStateView() {
        if (host.emptyView() != null) {
            mAdapter?.isStateViewEnable = true
            mMultiItemAdapter?.isStateViewEnable = true
            mAdapter?.stateView = host.emptyView()
            mMultiItemAdapter?.stateView = host.emptyView()
        }

        if (host.emptyResId() != 0) {
            val context = host.getHostContext()
            mAdapter?.isStateViewEnable = true
            mMultiItemAdapter?.isStateViewEnable = true
            mAdapter?.setStateViewLayout(context, host.emptyResId())
            mMultiItemAdapter?.setStateViewLayout(context, host.emptyResId())
        }
    }

    /**
     * 滚动到顶部
     */
    fun scrollToTop() {
        host.getHostBinding().recyclerView.scrollToPosition(0)
    }

    /**
     * 数据加载成功处理（支持两种重载方式）
     */
    fun onDataSuccess(list: List<T>?) {
        val newList = list ?: emptyList()
        val binding = host.getHostBinding()

        if (mPage == 1) {
            mAdapter?.submitList(newList)
            mMultiItemAdapter?.submitList(newList)
        } else {
            mAdapter?.addAll(newList)
            mMultiItemAdapter?.addAll(newList)
        }

        setRecyclerViewStateView()

        if (newList.size < mPageSize) {
            binding.smartRefresh.setEnableLoadMore(false)
        } else {
            binding.smartRefresh.setEnableLoadMore(host.canLoadMore())
        }
    }

    /**
     * 数据加载失败处理
     */
    fun onDataFail() {
        host.getHostBinding().smartRefresh.setEnableLoadMore(false)
    }

    /**
     * 显示提示信息
     */
    fun showToast(message: String?) {
        val context = host.getHostContext()
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    // ===== OnRefreshLoadMoreListener实现 =====

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mPage = 0
        host.queryData()
        host.getHostBinding().smartRefresh.finishRefresh(1000)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mPage++
        host.queryData()
        host.getHostBinding().smartRefresh.finishLoadMore(1000)
    }

    // ===== 点击事件委托 =====

    override fun onClick(adapter: BaseQuickAdapter<T, *>, view: View, position: Int) {
        host.onClick(adapter, view, position)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<T, *>, view: View, position: Int) {
        host.onItemClick(adapter, view, position)
    }
}