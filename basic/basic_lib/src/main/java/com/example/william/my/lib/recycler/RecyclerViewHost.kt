package com.example.william.my.lib.recycler

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter4.BaseMultiItemAdapter
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.example.william.my.lib.databinding.BaseFragmentRecyclerViewBinding

/**
 * RecyclerView宿主接口
 * 为BaseRecyclerHandler提供必要的访问接口和配置方法
 */
interface RecyclerViewHost<T : Any> {

    /**
     * 获取ViewBinding实例
     */
    fun getHostBinding(): BaseFragmentRecyclerViewBinding

    /**
     * 获取Context实例
     */
    fun getHostContext(): Context

    // ===== 配置方法 =====

    /**
     * 是否支持下拉刷新
     */
    fun canRefresh(): Boolean = true

    /**
     * 是否支持上拉加载更多
     */
    fun canLoadMore(): Boolean = true

    /**
     * 初始化RecyclerView的LayoutManager
     */
    fun initRecyclerManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(getHostContext())
    }

    /**
     * 初始化单类型Adapter
     */
    fun initRecyclerAdapter(): BaseQuickAdapter<T, QuickViewHolder>? = null

    /**
     * 初始化多类型Adapter
     */
    fun initRecyclerMultiAdapter(): BaseMultiItemAdapter<T>? = null

    // ===== 扩展方法 =====

    /**
     * 滚动监听器列表
     */
    fun scrollListener(): List<RecyclerView.OnScrollListener> = arrayListOf()

    /**
     * 装饰器列表
     */
    fun itemDecorations(): List<RecyclerView.ItemDecoration> = arrayListOf()

    /**
     * 空视图
     */
    fun emptyView(): View? = null

    /**
     * 空视图资源ID
     */
    fun emptyResId(): Int = 0

    // ===== 生命周期和数据 =====

    /**
     * 初始化RecyclerView数据
     */
    fun initRecyclerData(savedInstanceState: Bundle?) {}

    /**
     * 查询数据
     */
    fun queryData() {}

    // ===== 点击事件委托 =====

    /**
     * item点击事件
     */
    fun onClick(adapter: BaseQuickAdapter<T, *>, view: View, position: Int) {}

    /**
     * item子view点击事件
     */
    fun onItemClick(adapter: BaseQuickAdapter<T, *>, view: View, position: Int) {}

    /**
     * item长按事件
     */
    fun onLongClick(adapter: BaseQuickAdapter<T, *>, view: View, position: Int): Boolean {
        return false
    }

    /**
     * item子view长按事件
     */
    fun onItemLongClick(adapter: BaseQuickAdapter<T, *>, view: View, position: Int): Boolean {
        return false
    }
}