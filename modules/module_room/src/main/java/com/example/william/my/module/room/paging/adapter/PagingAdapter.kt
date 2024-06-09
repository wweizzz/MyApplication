package com.example.william.my.module.room.paging.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.william.my.basic.basic_repo.bean.ArticleDetailData
import com.example.william.my.module.room.databinding.SampleItemRecyclerBinding

/**
 * Paging RecyclerView 适配器
 */
class PagingAdapter(diffCallback: DiffUtil.ItemCallback<ArticleDetailData>) :
    PagingDataAdapter<ArticleDetailData, PagingAdapter.ViewHolder>(diffCallback) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = position.toString() + ". " + getItem(position)?.title
        holder.binding.itemTextView.text = item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val bind =
            SampleItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(bind)
    }

    class ViewHolder(bind: SampleItemRecyclerBinding) : RecyclerView.ViewHolder(bind.root) {
        var binding: SampleItemRecyclerBinding = bind
    }

    class PagingComparator : DiffUtil.ItemCallback<ArticleDetailData>() {

        override fun areItemsTheSame(
            oldItem: ArticleDetailData,
            newItem: ArticleDetailData
        ): Boolean {
            // Id is unique.
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ArticleDetailData,
            newItem: ArticleDetailData
        ): Boolean {
            return newItem.id == oldItem.id
        }
    }
}