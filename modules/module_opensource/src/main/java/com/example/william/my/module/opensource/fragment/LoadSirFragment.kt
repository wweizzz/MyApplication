package com.example.william.my.module.opensource.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kingja.loadsir.core.LoadSir


class LoadSirFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //第一步：获取布局View
        val rootView = View.inflate(
            activity,
            com.example.william.my.basic.basic_module.R.layout.basics_layout_response,
            null
        )
        //第二步：注册布局View
        val loadService = LoadSir.getDefault().register(this) {

        }
        //第三步：返回LoadSir生成的LoadLayout
        return loadService.loadLayout
    }
}