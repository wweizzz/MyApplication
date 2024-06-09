package com.example.william.my.module.opensource.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.william.my.module.opensource.R
import com.example.william.my.module.opensource.databinding.OpenItemPictureSelectorBinding
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia

class PictureSelectorAdapter(
    val data: ArrayList<LocalMedia?> = arrayListOf(),
    var maxSelect: Int = 9
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            OpenItemPictureSelectorBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as ViewHolder).binding
        if (getItemViewType(position) == TYPE_CAMERA) {
            binding.itemIvDel.visibility = View.INVISIBLE
            binding.itemIvPic.setImageResource(R.drawable.ic_launcher)
            binding.itemIvPic.setOnClickListener {
                mItemClickListener?.openPicture()
            }
        } else {
            if (mItemClickListener != null) {
                binding.root.setOnClickListener { v: View ->
                    mItemClickListener?.onItemClick(v, holder.absoluteAdapterPosition)
                }
            }

            binding.itemIvDel.visibility = View.VISIBLE
            binding.itemIvDel.setOnClickListener {
                delete(holder.absoluteAdapterPosition)
            }

            data[position]?.let {
                Glide.with(holder.itemView.context)
                    .load(
                        if (PictureMimeType.isContent(it.availablePath) && !it.isCut && !it.isCompressed) {
                            Uri.parse(it.availablePath)
                        } else {
                            it.availablePath
                        }
                    )
                    .centerCrop()
                    .into(binding.itemIvPic)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (data.size < maxSelect) {
            data.size + 1
        } else {
            data.size
        }
    }

    class ViewHolder(val binding: OpenItemPictureSelectorBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemViewType(position: Int): Int {
        return if (isShowAddItem(position)) {
            TYPE_CAMERA
        } else {
            TYPE_PICTURE
        }
    }

    private fun isShowAddItem(position: Int): Boolean {
        val size = data.size
        return position == size
    }

    private fun delete(position: Int) {
        try {
            if (position != RecyclerView.NO_POSITION && data.size > position) {
                data.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, data.size)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun remove(position: Int) {
        if (position < data.size) {
            data.removeAt(position)
        }
    }

    private var mItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        mItemClickListener = listener
    }

    interface OnItemClickListener {
        fun openPicture()
        fun onItemClick(v: View, position: Int)
    }

    companion object {
        const val TYPE_CAMERA = 1
        const val TYPE_PICTURE = 2
    }
}
