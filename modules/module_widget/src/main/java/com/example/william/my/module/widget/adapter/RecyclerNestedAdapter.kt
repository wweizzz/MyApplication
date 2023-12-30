package com.example.william.my.module.widget.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.william.my.module.widget.databinding.DemoItemRecyclerNestedBinding
import com.example.william.my.module.widget.databinding.DemoItemRecyclerViewBinding

/**
 * RecyclerView 嵌套 RecyclerView
 */
class RecyclerNestedAdapter(private var mData: List<String>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DemoItemRecyclerNestedBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as ViewHolder).binding

        binding.itemRecycleView.layoutManager = LinearLayoutManager(holder.itemView.context)
        binding.itemRecycleView.adapter = RecyclerAdapter(mData)
    }

    override fun getItemCount(): Int {
        return mData?.size ?: 0
    }

    class ViewHolder(val binding: DemoItemRecyclerNestedBinding) :
        RecyclerView.ViewHolder(binding.root)

    class RecyclerAdapter(private val mData: List<String>?) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = DemoItemRecyclerViewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val binding = (holder as ViewHolder).binding
            mData?.let { data ->
                binding.itemTextView.text = data[position]
            }
        }

        override fun getItemCount(): Int {
            return mData?.size ?: 0
        }

        class ViewHolder(val binding: DemoItemRecyclerViewBinding) :
            RecyclerView.ViewHolder(binding.root)
    }
}

