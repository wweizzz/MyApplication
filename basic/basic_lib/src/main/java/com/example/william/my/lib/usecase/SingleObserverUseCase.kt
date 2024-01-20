package com.example.william.my.lib.usecase

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * 为什么要增加UserCase？
 * 随着业务的不断扩张，ViewModel的内容可能会不断膨胀，那么独立出ViewModel的业务逻辑，划分到不同的领域(Use Cases)当中是有必要的，符合单一职责的指导思想，也有利于case的复用。
 * 在ViewModel和Repository添加一个层级，里面包含了不同的case，ViewModel通过组合、依赖注入的方式获取Cases的能力。
 */
abstract class SingleObserverUseCase<ReturnType : Any> {

    private var disposable: Disposable? = null
    private val mBackgroundExecutor: Scheduler
    private val mScheduledExecutor: Scheduler

    constructor() {
        mBackgroundExecutor = Schedulers.io()
        mScheduledExecutor = AndroidSchedulers.mainThread()
    }

    constructor(backgroundExecutor: Scheduler, scheduledExecutor: Scheduler) {
        mBackgroundExecutor = backgroundExecutor
        mScheduledExecutor = scheduledExecutor
    }

    abstract fun buildUseCaseObservable(): Single<ReturnType>

    fun execute(observer: DisposableSingleObserver<ReturnType>) {
        disposable = buildUseCaseObservable()
            .subscribeOn(mBackgroundExecutor)
            .observeOn(mScheduledExecutor)
            .subscribeWith(observer)
    }

    fun clear() {
        if (disposable != null && !disposable!!.isDisposed) {
            disposable!!.dispose()
            disposable = null
        }
    }
}