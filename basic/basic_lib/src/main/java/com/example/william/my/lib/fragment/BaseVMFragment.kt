package com.example.william.my.lib.fragment

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.william.my.lib.viewmodel.BaseViewModel
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.ParameterizedType

abstract class BaseVMFragment<VB : ViewBinding?, VM : BaseViewModel>(layout: Int = 0) :
    BaseVBFragment<VB>(layout) {

    private var _viewModel: VM? = null
    protected val mViewModel get() = _viewModel!!

    override fun initViewModel() {
        _viewModel = viewModel()
        lifecycle.addObserver(mViewModel)
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

    override fun observeViewModel() {
        _viewModel!!.error.observe(requireActivity()) { throwable ->
            onError(throwable)
        }
    }

    protected fun onError(throwable: Throwable) {
        Log.e(tag, throwable.message.toString())
    }

    override fun onDestroy() {
        _viewModel = null
        super.onDestroy()
    }
}