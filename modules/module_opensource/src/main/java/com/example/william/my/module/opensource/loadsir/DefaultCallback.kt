package com.example.william.my.module.opensource.loadsir

import android.content.Context
import android.view.View
import android.widget.TextView
import com.example.william.my.basic.basic_module.R
import com.kingja.loadsir.callback.Callback

class DefaultCallback : Callback() {

    //填充布局
    override fun onCreateView(): Int {
        return R.layout.basics_layout_response
    }

    //当前Callback的点击事件，如果返回true则覆盖注册时的onReload()，如果返回false则两者都执行，先执行onReloadEvent()。
    override fun onReloadEvent(context: Context, view: View): Boolean {
        val response: TextView = view.findViewById(R.id.basics_response)
        response.text = "DefaultCallback"
        return true
    }

    //将Callback添加到当前视图时的回调，View为当前Callback的布局View
    override fun onAttach(context: Context?, view: View?) {
        super.onAttach(context, view)
    }

    //将Callback从当前视图删除时的回调，View为当前Callback的布局View
    override fun onDetach() {
        super.onDetach()
    }
}