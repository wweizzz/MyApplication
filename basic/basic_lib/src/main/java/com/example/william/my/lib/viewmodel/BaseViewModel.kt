package com.example.william.my.lib.viewmodel

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel(), DefaultLifecycleObserver {

    var error: MutableLiveData<Throwable> = MutableLiveData()

}
