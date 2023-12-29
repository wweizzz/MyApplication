package com.example.william.my.module.sample.activity.topic

import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity
import com.example.william.my.basic.basic_module.router.path.ARouterPath
import com.example.william.my.basic.basic_repository.bean.ArticleListData
import com.example.william.my.core.retrofit.loading.LoadingTip
import com.example.william.my.core.retrofit.loading.LoadingTip.LoadingTipListener
import com.example.william.my.core.retrofit.observer.WithLoadingTipObserver
import com.example.william.my.module.sample.viewmodel.ArticleViewModel

/**
 * https://developer.android.google.cn/topic/libraries/architecture/livedata
 * https://developer.android.google.cn/topic/libraries/architecture/viewmodel
 */
@Route(path = ARouterPath.Sample.ViewModel)
class ViewModelActivity : BasicResponseActivity(), LoadingTipListener {

    private var loadingTip: LoadingTip? = null

    private val viewModel by viewModels<ArticleViewModel> {
        ArticleViewModel.Factory
    }

    override fun initView() {
        super.initView()

        initLoadingTip()
    }

    private fun initLoadingTip() {
        loadingTip = LoadingTip.addLoadingTipWithTopBar(this)
        loadingTip?.setOnReloadListener(this)
    }

    override fun observeViewModel() {
        viewModel.articleData.observe(
            this,
            object : WithLoadingTipObserver<ArticleListData>(loadingTip, "article") {
                override fun onResponse(response: ArticleListData) {
                    if (response.datas.isNotEmpty()) {
                        showResponse(response.datas[0].string())
                    } else {
                        showResponse("onDataNotAvailable")
                    }
                }
            })

        reload()
    }

    override fun reload() {
        viewModel.loadArticle(0)
    }
}
