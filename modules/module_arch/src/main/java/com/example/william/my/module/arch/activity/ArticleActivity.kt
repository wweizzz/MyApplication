package com.example.william.my.module.arch.activity

import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.example.william.my.lib.activity.BaseActivity
import com.example.william.my.lib.fragment.BaseFragment
import com.example.william.my.module.arch.utils.obtainViewModel
import com.example.william.my.module.arch.viewmodel.ArticleViewModel

/**
 * viewModels
 */
class ArticleActivity : BaseActivity() {

    private val viewModels: ArticleViewModel by viewModels()

    private val mViewModel by viewModels<ArticleViewModel> {
        ArticleViewModel.Factory
    }

    private val mViewModel2 by viewModels<ArticleViewModel> {
        ArticleViewModel.Factory2
    }

    private val viewModel: ArticleViewModel =
        obtainViewModel(ArticleViewModel::class.java)

    class ArticleFragment : BaseFragment() {

        private val viewModel: ArticleViewModel =
            obtainViewModel(ArticleViewModel::class.java)

        private val viewModels: ArticleViewModel by viewModels()

        class ArticleFragment : BaseFragment() {

            private val viewModels: ArticleViewModel by viewModels({ requireParentFragment() })

        }
    }
}