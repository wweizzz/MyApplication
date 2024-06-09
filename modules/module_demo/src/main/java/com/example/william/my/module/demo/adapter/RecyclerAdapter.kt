package com.example.william.my.module.demo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.william.my.module.demo.cache.RecyclerCacheExtension
import com.example.william.my.module.demo.databinding.DemoItemRecyclerViewBinding

class RecyclerAdapter(private val data: List<String>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mCaches: RecyclerCacheExtension = RecyclerCacheExtension()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            DemoItemRecyclerViewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        mCaches.addCache(position, holder.itemView)

        holder.itemView.setOnClickListener { v ->
            onItemClickListener?.onItemClick(
                this@RecyclerAdapter, v, holder.bindingAdapterPosition
            )
        }

        val binding = (holder as ViewHolder).binding
        data?.let { data ->
            binding.itemTextView.text = data[position]
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: List<Any>
    ) {
        if (payloads.isEmpty()) {
            //会执行不带payloads参数的onBindViewHolder
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val payload = payloads[0] as String
            (holder as ViewHolder).binding.itemTextView.text = payload
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    class ViewHolder(val binding: DemoItemRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnItemClickListener {
        fun onItemClick(adapter: RecyclerAdapter, view: View, position: Int)
    }

    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }
}