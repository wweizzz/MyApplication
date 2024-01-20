package com.example.william.my.module.arch.activity

import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.example.william.my.lib.activity.BaseActivity
import com.example.william.my.lib.fragment.BaseFragment
import com.example.william.my.module.arch.viewmodel.ViewModelViewModel

/**
 * viewModels
 */
class ArticleActivity : BaseActivity() {

    val mArticleViewModel: ViewModelViewModel by viewModels()

    class ArticleFragment : BaseFragment() {

        val mArticleViewModel: ViewModelViewModel by viewModels()

        class ArticleFragment : BaseFragment() {

            val mArticleViewModel: ViewModelViewModel by viewModels({ requireParentFragment() })

        }

    }

}