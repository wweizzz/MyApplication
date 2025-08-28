package com.example.william.my.lib.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.MutableLiveData
import com.alibaba.android.arouter.launcher.ARouter

abstract class BaseAndroidViewModel(app: Application) :
    AndroidViewModel(app), DefaultLifecycleObserver {

    var error: MutableLiveData<Throwable> = MutableLiveData()

    init {
        ARouter.getInstance().inject(this)
    }
}
