package com.example.william.my.module.arch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.william.my.basic.basic_repo.bean.ArticleData
import com.example.william.my.basic.basic_repo.bean.ArticleDetailData
import com.example.william.my.basic.basic_repo.data.ServiceLocator
import com.example.william.my.basic.basic_repo.data.source.ArticleRepository
import com.example.william.my.core.retrofit.response.RetrofitResponse
import com.example.william.my.lib.app.BaseApp

/**
 * 如果需要Context则使用AndroidViewModel
 *
 * MutableLiveData 可变的，私有，对内访问
 * LiveData 不可变的，对外访问
 *
 * 1.MutableLiveData的父类是LiveData
 * 2.LiveData在实体类里可以通知指定某个字段的数据更新.
 * 3.MutableLiveData则是完全是整个实体类或者数据类型变化后才通知.不会细节到某个字段
 */
class ArticleViewModel(
    private val repository: ArticleRepository<ArticleData, ArticleDetailData>,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
//class ArticleViewModel(val application: Application) : AndroidViewModel(application) {

    private val _articleData = MutableLiveData<Int>()

    // 转换 LiveData
    val articleData: LiveData<RetrofitResponse<ArticleData>> = _articleData.switchMap { page ->
        repository.getArticleLiveData(page)
    }

    fun loadArticle(page: Int) {
        _articleData.postValue(page)
    }

    // Define ViewModel factory in a companion object

    /**
     * object : ViewModelProvider.Factory {}
     */
    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application =
                    checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                // Create a SavedStateHandle for this ViewModel from extras
                val savedStateHandle = extras.createSavedStateHandle()

                val repository = ServiceLocator.provideArticleRepository(application)
                return ArticleViewModel(
                    repository = repository,
                    savedStateHandle = savedStateHandle
                ) as T
            }
        }

        /**
         * viewModelFactory {}
         */
        val Factory2: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BaseApp
                val savedStateHandle = createSavedStateHandle()

                val repository = ServiceLocator.provideArticleRepository(application)
                ArticleViewModel(
                    repository = repository,
                    savedStateHandle = savedStateHandle
                )
            }
        }
    }

    /**
     * 销毁时调用
     */
    override fun onCleared() {
        super.onCleared()
    }
}