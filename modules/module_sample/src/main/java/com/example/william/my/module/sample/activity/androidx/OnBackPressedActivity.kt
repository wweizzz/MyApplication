package com.example.william.my.module.sample.activity.androidx

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.databinding.BasicsLayoutResponseBinding
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.lib.activity.BaseFragmentActivity
import com.example.william.my.lib.fragment.BaseVBFragment
import com.example.william.my.lib.utils.Utils

/**
 * onBackPressedDispatcher
 */
@Route(path = RouterPath.Sample.OnBackPressedDispatcher)
class OnBackPressedActivity : BaseFragmentActivity() {

    override fun setFragment(): Fragment {
        return BackPressedFragment()
    }

    class BackPressedFragment : BaseVBFragment<BasicsLayoutResponseBinding>() {

        override fun getViewBinding(): BasicsLayoutResponseBinding {
            return BasicsLayoutResponseBinding.inflate(layoutInflater)
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            requireActivity().onBackPressedDispatcher.addCallback(object :
                OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Utils.show("handleOnBackPressed")
                    requireActivity().finish()
                }
            })
        }
    }
}