package com.example.william.my.lib.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter4.BaseMultiItemAdapter
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.example.william.my.lib.databinding.BaseFragmentRecyclerViewBinding
import com.example.william.my.lib.recycler.BaseRecyclerHandler
import com.example.william.my.lib.recycler.RecyclerViewHost

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 * LayoutManager -> Adapter -> ItemDecoration -> OnScrollListener
 */
abstract class BaseRecyclerFragment<T : Any> :
    BaseVBFragment<BaseFragmentRecyclerViewBinding>(),
    RecyclerViewHost<T> {

    private lateinit var recyclerHandler: BaseRecyclerHandler<T>

    override fun getViewBinding(): BaseFragmentRecyclerViewBinding {
        return BaseFragmentRecyclerViewBinding.inflate(layoutInflater)
    }

    override fun initView(view: View?, state: Bundle?) {
        super.initView(view, state)

        recyclerHandler = BaseRecyclerHandler(this)
        recyclerHandler.initRecyclerView()
        initRecyclerData(state)
    }

    // ===== RecyclerViewHost接口实现 =====

    override fun getHostBinding(): BaseFragmentRecyclerViewBinding = mBinding

    override fun getHostContext(): Context = requireContext()

    // ===== 委托方法 =====

    /**
     * 滚动到顶部
     */
    fun scrollToTop() = recyclerHandler.scrollToTop()

    /**
     * 数据加载成功处理
     */
    fun onDataSuccess(list: List<T>?) = recyclerHandler.onDataSuccess(list)

    /**
     * 数据加载失败处理
     */
    fun onDataFail() = recyclerHandler.onDataFail()

    /**
     * 设置RecyclerView状态视图
     */
    fun setRecyclerViewStateView() = recyclerHandler.setRecyclerViewStateView()

    /**
     * 显示提示信息
     */
    fun showToast(message: String?) = recyclerHandler.showToast(message)

    // ===== 属性访问器 =====

    /**
     * 获取当前页码
     */
    val mPage: Int get() = recyclerHandler.mPage

    /**
     * 获取页面大小
     */
    val mPageSize: Int get() = recyclerHandler.mPageSize

    /**
     * 获取LayoutManager
     */
    val mLayoutManager: RecyclerView.LayoutManager? get() = recyclerHandler.mLayoutManager

    /**
     * 获取Adapter
     */
    val mAdapter: BaseQuickAdapter<T, QuickViewHolder>? get() = recyclerHandler.mAdapter

    /**
     * 获取多类型Adapter
     */
    val mMultiItemAdapter: BaseMultiItemAdapter<T>? get() = recyclerHandler.mMultiItemAdapter
}