package com.example.william.my.module.arch.adapter

import android.content.Context
import android.view.ViewGroup
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.example.william.my.basic.basic_repo.bean.ArticleDetailData
import com.example.william.my.module.arch.R

class ArticleAdapter(data: ArrayList<ArticleDetailData>) :
    BaseQuickAdapter<ArticleDetailData, QuickViewHolder>(data) {

    override fun onBindViewHolder(
        holder: QuickViewHolder,
        position: Int,
        item: ArticleDetailData?
    ) {
        holder.setText(R.id.item_textView, item?.title)
    }

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): QuickViewHolder {
        return QuickViewHolder(R.layout.arch_item_recycler, parent)
    }
}