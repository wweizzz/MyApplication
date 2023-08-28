package com.example.william.my.library.viewmodel

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel(), DefaultLifecycleObserver {

    var errorLiveData: MutableLiveData<Throwable> = MutableLiveData()

}
