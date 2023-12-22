package com.example.william.my.lib.view

import android.view.View

interface IEmptyView {
    val rootView: View
    fun showEmptyView()
    fun showErrorView()
    fun hide()
    fun setOnClickListener(listener: OnEmptyClickListener?)
    interface OnEmptyClickListener {
        fun onRefresh()
    }
}