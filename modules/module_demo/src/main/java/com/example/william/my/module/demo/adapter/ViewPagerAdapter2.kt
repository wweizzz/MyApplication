package com.example.william.my.module.demo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.william.my.module.demo.databinding.DemoPageViewPagerBinding

class ViewPagerAdapter2(private val mData: List<String>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            DemoPageViewPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as ViewHolder).binding
        mData?.let { data ->
            binding.response.text = data[position]
        }
    }

    override fun getItemCount(): Int {
        return mData?.size ?: 0
    }

    class ViewHolder(val binding: DemoPageViewPagerBinding) :
        RecyclerView.ViewHolder(binding.root)

}