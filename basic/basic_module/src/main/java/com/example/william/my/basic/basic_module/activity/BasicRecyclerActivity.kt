package com.example.william.my.basic.basic_module.activity

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder

import com.example.william.my.basic.basic_module.R
import com.example.william.my.basic.basic_module.databinding.BasicsLayoutRecyclerBinding
import com.example.william.my.basic.basic_module.dialog.BasicDialogFragment
import com.example.william.my.lib.activity.BaseActivity

abstract class BasicRecyclerActivity : BaseActivity(),
    BaseQuickAdapter.OnItemClickListener<String> {

    private lateinit var mAdapter: RecyclerAdapter
    private lateinit var mLayoutManager: LayoutManager

    protected lateinit var mBinding: BasicsLayoutRecyclerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        mBinding = BasicsLayoutRecyclerBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        super.initView()

        initRecycler()
    }

    private fun initRecycler() {
        mAdapter = RecyclerAdapter(buildList())
        mAdapter.setOnItemClickListener(this)
        mLayoutManager = LinearLayoutManager(this)
        mBinding.basicsRecycler.adapter = mAdapter
        mBinding.basicsRecycler.layoutManager = mLayoutManager
    }

    protected open fun buildList(): ArrayList<String> {
        return arrayListOf()
    }

    override fun onClick(adapter: BaseQuickAdapter<String, *>, view: View, position: Int) {
        onRecyclerClick(position, adapter.items[position])
    }

    protected open fun onRecyclerClick(position: Int, string: String) {

    }

    protected fun showMessage(msg: String?) {
        runOnUiThread {
            val dialog = BasicDialogFragment()
            dialog.show(supportFragmentManager, dialog.tag)
            dialog.showMessage(msg)
        }
    }

    class RecyclerAdapter(data: ArrayList<String>) :
        BaseQuickAdapter<String, QuickViewHolder>(data) {

        override fun onBindViewHolder(holder: QuickViewHolder, position: Int, item: String?) {
            holder.setText(R.id.item_textView, item)
        }

        override fun onCreateViewHolder(
            context: Context,
            parent: ViewGroup,
            viewType: Int
        ): QuickViewHolder {
            return QuickViewHolder(R.layout.basics_item_recycler, parent)
        }
    }
}