package com.example.william.my.module.opensource.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.swipe.SwipeLayout
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter
import com.example.william.my.module.opensource.R
import com.example.william.my.module.opensource.databinding.OpenItemSwipeBinding

class SwipeRecyclerAdapter(private var data: List<String>?) :
    RecyclerSwipeAdapter<SwipeRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            OpenItemSwipeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        if (viewHolder.itemView is SwipeLayout) {
            mItemManger.bindView(viewHolder.itemView, i)
        }

        viewHolder.binding.itemSwipeButton.setOnClickListener {
            mItemManger.closeItem(i)
            mItemManger.closeAllItems()
        }
        data?.let {
            viewHolder.binding.itemSwipeTextView.text = it[i]
        }
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun getSwipeLayoutResourceId(position: Int): Int {
        return R.id.item_swipe_swipeLayout
    }

    class ViewHolder(val binding: OpenItemSwipeBinding) :
        RecyclerView.ViewHolder(binding.root)
}
