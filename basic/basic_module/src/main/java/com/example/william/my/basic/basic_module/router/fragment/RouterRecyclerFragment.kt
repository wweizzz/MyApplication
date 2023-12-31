package com.example.william.my.basic.basic_module.router.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.exception.HandlerException
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.example.william.my.basic.basic_module.R
import com.example.william.my.basic.basic_module.router.item.RouterItem
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.lib.fragment.BaseRecyclerFragment

@Route(path = RouterPath.Fragment.FragmentBasicRecycler)
class RouterRecyclerFragment : BaseRecyclerFragment<RouterItem>() {

    override fun initRecyclerAdapter(): BaseQuickAdapter<RouterItem, QuickViewHolder> {
        return RouterRecyclerAdapter(arrayListOf())
    }

    override fun initView(view: View?, state: Bundle?) {
        super.initView(view, state)

        onDataSuccess(arguments?.getParcelableArrayList("router"))
        mBinding.smartRefresh.setEnableRefresh(false)
        mBinding.smartRefresh.setEnableLoadMore(false)
    }

    override fun onClick(adapter: BaseQuickAdapter<RouterItem, *>, view: View, position: Int) {
        super.onClick(adapter, view, position)
        val item = adapter.items[position]
        try {
            ARouter.getInstance().build(item.mRouterPath).navigation()
        } catch (e: HandlerException) {
            e.printStackTrace()
        }
    }

    class RouterRecyclerAdapter(data: ArrayList<RouterItem>) :
        BaseQuickAdapter<RouterItem, QuickViewHolder>(data) {

        override fun onBindViewHolder(holder: QuickViewHolder, position: Int, item: RouterItem?) {
            holder.setText(R.id.item_textView, item?.mRouterName)
        }

        override fun onCreateViewHolder(
            context: Context,
            parent: ViewGroup,
            viewType: Int
        ): QuickViewHolder {
            return QuickViewHolder(R.layout.basics_item_recycler, parent)
        }
    }
}