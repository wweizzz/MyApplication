package com.example.william.my.library.activity

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.william.my.library.viewmodel.BaseViewModel
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.ParameterizedType

abstract class BaseVMActivity<VB : ViewBinding?, VM : BaseViewModel> : BaseVBActivity<VB>() {

    private var _model: VM? = null
    protected val mViewModel get() = _model!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _model = initViewModel()
        lifecycle.addObserver(_model!!)
    }

    private fun initViewModel(): VM? {
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
        _model = null
        super.onDestroy()
    }

    fun onError(throwable: Throwable) {
        Log.e(tag, throwable.message.toString())
    }


    override fun observeViewModel() {
        super.observeViewModel()

        _model!!.errorLiveData.observe(this) { throwable ->
            onError(throwable)
        }
    }
}