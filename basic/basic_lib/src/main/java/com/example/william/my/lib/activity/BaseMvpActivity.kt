package com.example.william.my.lib.activity

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import com.example.william.my.lib.presenter.IBasePresenter
import com.example.william.my.lib.view.IBaseView
import com.trello.lifecycle4.android.lifecycle.AndroidLifecycle
import com.trello.rxlifecycle4.LifecycleProvider
import java.lang.reflect.InvocationTargetException

abstract class BaseMvpActivity<T : IBasePresenter?, V : IBaseView<T>?> : BaseActivity() {

    protected var mPresenter: T? = null

    private lateinit var provider: LifecycleProvider<Lifecycle.Event>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        provider = AndroidLifecycle.createLifecycleProvider(this)
        initPresenter()
    }

    /**
     * 返回逻辑处理的具体类型.
     */
    protected abstract val presenterClass: Class<T>

    /**
     * 返回View层的接口类.
     */
    protected abstract val viewClass: Class<V>?

    /**
     * 初始化Presenter
     */
    private fun initPresenter() {
        try {
            val constructor = presenterClass.getConstructor(viewClass)
            mPresenter = constructor.newInstance(this)
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        clearPresenter()
        super.onDestroy()
    }

    private fun clearPresenter() {
        mPresenter?.clear()
        mPresenter = null
    }
}