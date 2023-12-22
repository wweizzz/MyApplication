package com.example.william.my.basic.basic_module.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.R
import com.example.william.my.basic.basic_module.router.path.ARouterPath
import com.example.william.my.lib.fragment.BaseFragment

@Route(path = ARouterPath.Fragment.FragmentPrimary)
class PrimaryFragment : BaseFragment(R.layout.basics_fragment_primary)