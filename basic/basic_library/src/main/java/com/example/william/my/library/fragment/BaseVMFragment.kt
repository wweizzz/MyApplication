package com.example.william.my.library.fragment

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.william.my.library.viewmodel.BaseViewModel
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.ParameterizedType

abstract class BaseVMFragment<VB : ViewBinding?, VM : BaseViewModel>(layout: Int = 0) :
    BaseVBFragment<VB>(layout) {

    private var _viewModel: VM? = null
    protected val viewModel get() = _viewModel!!

    override fun initViewModel() {
        _viewModel = viewModel()
        lifecycle.addObserver(_viewModel!!)
    }

    override fun observeViewModel() {
        _viewModel!!.errorLiveData.observe(requireActivity()) { throwable ->
            onError(throwable)
        }
    }

    private fun viewModel(): VM? {
        try {
            val aClass =
                (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<VM>
            return ViewModelProvider(this)[aClass]
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
        return null
    }

    override fun onDestroy() {
        _viewModel = null
        super.onDestroy()
    }

    protected fun onError(throwable: Throwable) {
        Log.e(tag, throwable.message.toString())
    }
}