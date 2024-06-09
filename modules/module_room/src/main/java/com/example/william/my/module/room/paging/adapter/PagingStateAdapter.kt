package com.example.william.my.module.room.paging.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.william.my.module.room.R
import com.example.william.my.module.room.databinding.SampleItemRecyclerBinding

class PagingStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<PagingStateAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.mBinding.itemTextView.setOnClickListener {
            retry
        }
        when (loadState) {
            is LoadState.Loading ->
                holder.mBinding.itemTextView.setBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.colorPrimary
                    )
                )

            is LoadState.NotLoading ->
                holder.mBinding.itemTextView.setBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.colorPrimaryDark
                    )
                )

            is LoadState.Error -> {
                holder.mBinding.itemTextView.text = loadState.error.localizedMessage
                holder.mBinding.itemTextView.setBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.colorPrimaryDark
                    )
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        val bind =
            SampleItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(bind)
    }

    class ViewHolder(bind: SampleItemRecyclerBinding) : RecyclerView.ViewHolder(bind.root) {
        var mBinding: SampleItemRecyclerBinding = bind
    }
}