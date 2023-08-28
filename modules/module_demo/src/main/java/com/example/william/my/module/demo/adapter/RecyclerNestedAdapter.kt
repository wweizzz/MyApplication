package com.example.william.my.module.demo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.william.my.module.demo.databinding.DemoItemRecyclerViewNestedBinding

/**
 * RecyclerView 嵌套 RecyclerView
 */
class RecyclerNestedAdapter(private var mData: List<String>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding =
            DemoItemRecyclerViewNestedBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as ViewHolder).binding
        val manager = LinearLayoutManager(holder.itemView.context)
        manager.orientation = RecyclerView.VERTICAL
        binding.itemRecycleView.layoutManager = manager
        binding.itemRecycleView.adapter = RecyclerAdapter(mData)
    }

    override fun getItemCount(): Int {
        return mData?.size ?: 0
    }

    class ViewHolder(val binding: DemoItemRecyclerViewNestedBinding) :
        RecyclerView.ViewHolder(binding.root)

}