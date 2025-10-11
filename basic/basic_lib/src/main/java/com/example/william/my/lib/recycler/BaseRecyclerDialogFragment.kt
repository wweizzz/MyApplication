package com.example.william.my.lib.recycler

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter4.BaseMultiItemAdapter
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.QuickAdapterHelper
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.example.william.my.lib.databinding.BaseFragmentRecyclerViewBinding
import com.example.william.my.lib.dialog.BaseVBDialogFragment
import com.example.william.my.lib.recycler.handler.BaseRecyclerHandler
import com.example.william.my.lib.recycler.host.RecyclerViewHost

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 * LayoutManager -> Adapter -> ItemDecoration -> OnScrollListener
 */
abstract class BaseRecyclerDialogFragment<T : Any> :
    BaseVBDialogFragment<BaseFragmentRecyclerViewBinding>(),
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

    // ===== 配置方法 =====

    /**
     * 初始化RecyclerView状态视图
     */
    override fun initRecyclerViewStateView() {
        recyclerHandler.initRecyclerViewStateView()
    }

    // ===== 委托方法 =====

    /**
     * 数据加载成功处理
     */
    override fun onDataSuccess(list: List<T>?) {
        recyclerHandler.onDataSuccess(list)
    }

    /**
     * 数据加载失败处理
     */
    override fun onDataFail() {
        recyclerHandler.onDataFail()
    }

    /**
     * 滚动到顶部
     */
    override fun scrollToTop() {
        recyclerHandler.scrollToTop()
    }

    /**
     * 显示提示信息
     */
    override fun showToast(message: String?) {
        recyclerHandler.showToast(message)
    }

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

    /**
     * 获取QuickAdapterHelper
     */
    val mAdapterHelper: QuickAdapterHelper? get() = recyclerHandler.mAdapterHelper
}