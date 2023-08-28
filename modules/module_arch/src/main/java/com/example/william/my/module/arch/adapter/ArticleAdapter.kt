package com.example.william.my.module.arch.adapter

import android.content.Context
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.QuickViewHolder
import com.example.william.my.basic.basic_repository.bean.Article
import com.example.william.my.module.arch.R

class ArticleAdapter(data: ArrayList<Article>) :
    BaseQuickAdapter<Article, QuickViewHolder>(data) {

    override fun onBindViewHolder(holder: QuickViewHolder, position: Int, item: Article?) {
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