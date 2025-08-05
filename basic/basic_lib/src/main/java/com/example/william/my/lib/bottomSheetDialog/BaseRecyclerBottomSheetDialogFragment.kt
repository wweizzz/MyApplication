package com.example.william.my.lib.bottomSheetDialog

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.AdaptScreenUtils
import com.chad.library.adapter4.BaseMultiItemAdapter
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.QuickAdapterHelper
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.example.william.my.lib.databinding.BaseFragmentRecyclerViewBinding
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 * LayoutManager -> Adapter -> ItemDecoration -> OnScrollListener
 */
abstract class BaseRecyclerBottomSheetDialogFragment<T : Any> :
    BaseVBBottomSheetDialogFragment<BaseFragmentRecyclerViewBinding>(),
    BaseQuickAdapter.OnItemClickListener<T>, BaseQuickAdapter.OnItemChildClickListener<T>,
    OnRefreshLoadMoreListener {

    protected var mCursor = ""

    protected var mLayoutManager: RecyclerView.LayoutManager? = null

    protected var mAdapter: BaseQuickAdapter<T, QuickViewHolder>? = null
    protected var mMultiItemAdapter: BaseMultiItemAdapter<T>? = null

    protected lateinit var mAdapterHelper: QuickAdapterHelper

    override fun getViewBinding(): BaseFragmentRecyclerViewBinding {
        return BaseFragmentRecyclerViewBinding.inflate(layoutInflater)
    }

    override fun initView(view: View?, state: Bundle?) {
        super.initView(view, state)

        initRecyclerView()
        initRecyclerData(state)
    }

    private fun initRecyclerView() {
        //设置是否启用越界拖动（仿苹果效果）
        mBinding.smartRefresh.setEnableOverScrollDrag(true)
        //设置是会否启用嵌套滚动功能（默认关闭+智能开启）
        mBinding.smartRefresh.setNestedScrollingEnabled(true)

        mBinding.smartRefresh.setEnableRefresh(canRefresh())
        mBinding.smartRefresh.setEnableLoadMore(canLoadMore())
        mBinding.smartRefresh.setOnRefreshLoadMoreListener(this)

        //mBinding.recyclerView.isNestedScrollingEnabled = true

        mLayoutManager = initRecyclerManager()
        mLayoutManager.let {
            mBinding.recyclerView.layoutManager = it
        }

        mAdapter = initRecyclerAdapter()
        mAdapter?.let {
            it.setOnItemClickListener(this)
            mAdapterHelper = QuickAdapterHelper.Builder(it).build()
        }

        mMultiItemAdapter = initRecyclerMultiAdapter()
        mMultiItemAdapter?.let {
            it.setOnItemClickListener(this)
            mAdapterHelper = QuickAdapterHelper.Builder(it).build()
        }

        mBinding.recyclerView.adapter = mAdapterHelper.adapter

        itemDecorations().forEach {
            mBinding.recyclerView.addItemDecoration(it)
        }

        scrollListener().forEach {
            mBinding.recyclerView.addOnScrollListener(it)
        }
    }

    fun setRecyclerViewMargin(start: Float, end: Float) {
        mBinding.recyclerView.layoutParams.also {
            val params = it as SmartRefreshLayout.LayoutParams
            params.marginStart = AdaptScreenUtils.pt2Px(start)
            params.marginEnd = AdaptScreenUtils.pt2Px(end)
        }
    }

    protected fun setRecyclerViewStateView() {
        if (emptyView() != null) {
            mAdapter?.isStateViewEnable = true
            mMultiItemAdapter?.isStateViewEnable = true
            mAdapter?.stateView = emptyView()
            mMultiItemAdapter?.stateView = emptyView()
        }

        if (emptyResId() != 0) {
            mAdapter?.isStateViewEnable = true
            mMultiItemAdapter?.isStateViewEnable = true
            mAdapter?.setStateViewLayout(requireContext(), emptyResId())
            mMultiItemAdapter?.setStateViewLayout(requireContext(), emptyResId())
        }
    }

    open fun canRefresh(): Boolean {
        return true
    }

    open fun canLoadMore(): Boolean {
        return true
    }

    open fun initRecyclerManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(activity)
    }

    open fun initRecyclerAdapter(): BaseQuickAdapter<T, QuickViewHolder>? {
        return null
    }

    open fun initRecyclerMultiAdapter(): BaseMultiItemAdapter<T>? {
        return null
    }

    open fun scrollListener(): List<RecyclerView.OnScrollListener> {
        return arrayListOf()
    }

    open fun itemDecorations(): List<RecyclerView.ItemDecoration> {
        return arrayListOf()
    }

    open fun emptyView(): View? {
        return null
    }

    open fun emptyResId(): Int {
        return 0
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

    fun onDataSuccess(cursor: String?, list: List<T>?) {
        val newList = list ?: emptyList()

        if (mCursor.isEmpty()) {
            mAdapter?.submitList(newList)
            mMultiItemAdapter?.submitList(newList)
        } else {
            mAdapter?.addAll(newList)
            mMultiItemAdapter?.addAll(newList)
        }
        mCursor = cursor ?: ""

        setRecyclerViewStateView()

        if (newList.isEmpty()) {
            mBinding.smartRefresh.setEnableLoadMore(false)
        } else {
            mBinding.smartRefresh.setEnableLoadMore(canLoadMore())
        }
    }

    override fun onClick(adapter: BaseQuickAdapter<T, *>, view: View, position: Int) {

    }

    override fun onItemClick(adapter: BaseQuickAdapter<T, *>, view: View, position: Int) {

    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mCursor = ""
        queryData()
        mBinding.smartRefresh.finishRefresh(1000)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        queryData()
        mBinding.smartRefresh.finishLoadMore(1000)
    }
}