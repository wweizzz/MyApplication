package com.example.william.my.module.opensource.activity4

import android.annotation.SuppressLint
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity
import com.example.william.my.basic.basic_module.router.path.RouterPath
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.TimeUnit


/**
 * 创建操作符：
 *
 * 转换操作符：
 *
 * 过滤操作符：
 *
 * 组合操作符：
 *
 * 辅助操作符：
 *
 * 错误处理操作符：
 */
@SuppressLint("CheckResult")
@Route(path = RouterPath.Opensource.RxJava)
class RxJavaActivity : BasicResponseActivity() {

    /**
     * 创建操作符
     */
    inner class CreateOperator {

        /**
         * 创建一个 Observable，自定义发射逻辑
         */
        fun create() {
            val observable = Observable.create { emitter ->
                emitter.onNext("1") //手动调用发射逻辑
                emitter.onNext("2")
                emitter.onNext("3")
                emitter.onComplete() //手动调用发射完成的逻辑
            }

            observable.subscribe(object : Observer<String> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {

                }

                override fun onNext(t: String) {

                }
            })
        }

        /**
         * 创建一个 Observable，自动发射逻辑
         */
        fun just() {
            Observable.just("1", "2", "3")
                .subscribe {

                }
        }

        /**
         *  从一个 Array 对象中创建一个 Observable
         */
        fun fromArray() {
            Observable.fromArray(arrayOf("1", "2", "3"))
                .subscribe {

                }
        }

        /**
         * 从一个 Iterable 对象中创建一个 Observable。
         */
        fun fromIterable() {
            Observable.fromIterable(listOf("1", "2", "3"))
                .subscribe {

                }
        }

        /**
         * 创建一个 Observable，发射一个指定范围内的整数序列。
         */
        fun range() {
            Observable.range(1, 5)
                .subscribe {

                }
        }

        /**
         * 创建一个 Observable，定期发射一个递增的长整型数值。
         */
        fun interval() {
            Observable.interval(1, TimeUnit.SECONDS)
                .subscribe {

                }
        }

        /**
         * 创建一个 Observable，在指定延迟后发射一个数据项。
         */
        fun timer() {
            Observable.timer(1, TimeUnit.SECONDS)
                .subscribe {

                }
        }
    }

    /**
     * 转换操作符
     */
    inner class TransformOperator {

        /**
         * map 用于将 Observable 发射的每个数据项转换为另一种数据类型。
         */
        fun map() {
            Observable.just("1", "2", "3")
                .map { number ->
                    number.toString()
                }.subscribe {

                }
        }

        /**
         * 将 Observable 发出的每个数据项转换为 Observable，然后将这些 Observable 合并成一个。
         */
        fun flatMap() {
            Observable.just("1", "2", "3")
                .flatMap {
                    Observable.just(it)
                }.subscribe {

                }
        }

        /**
         * 类似于 flatMap，但保持原始数据项的顺序
         */
        fun concatMap() {
            Observable.just("1", "2", "3")
                .concatMap {
                    Observable.just(it)
                }.subscribe {

                }
        }

        fun switchMap() {

        }

        fun debounce() {

        }

        /**
         * 将数据项分组为列表，并以列表形式发射
         */
        fun buffer() {
            Observable.just("1", "2", "3")
                .buffer(2)
                .subscribe {

                }
        }
    }

    /**
     * 过滤操作符
     */
    @SuppressLint("CheckResult")
    inner class FilterOperator {

        /**
         * 过滤掉不满足条件的数据项。
         */
        fun filter() {
            Observable.just(1, 2, 3)
                .filter {
                    it < 3
                }.subscribe {

                }
        }

        /**
         * 过滤掉重复的数据项。
         */
        fun distinct() {
            Observable.just("1", "2", "2", "3", "3")
                .distinct()
                .subscribe {

                }
        }

        /**
         * 仅发射前 N 个数据项
         */
        fun take() {
            Observable.just("1", "2", "3")
                .take(2)
                .subscribe {

                }
        }

        /**
         * 跳过前 N 个数据项
         */
        fun skip() {
            Observable.just("1", "2", "3")
                .skip(2)
                .subscribe {

                }
        }

        /**
         * 检查本地缓存逻辑判断
         */
        fun switchIfEmpty() {
            Observable.create { emitter: ObservableEmitter<String>? ->

            }.switchIfEmpty(
                Observable.just("1", "2", "3")
            ).subscribe {

            }
        }
    }

    /**
     * 合并操作符
     */
    inner class MergeOperator {

        /**
         * 合并多个 Observables 的数据流。
         */
        fun concat() {
            val obs1 = Observable.just("1", "2", "3")
            val obs2 = Observable.just("4", "5", "6")

            Observable.concat(obs1, obs2)
                .subscribe {

                }
        }

        /**
         * 在发射原来的 Observable 的数据序列之前，先发射一个指定的数据序列或数据项。
         */
        fun startWith() {
            val obs1 = Observable.just("1", "2", "3")
            val obs2 = Observable.just("4", "5", "6")

            obs1.startWith(obs2)
                .subscribe {

                }
        }

        /**
         * 合并多个 Observables 的数据流。
         */
        fun marge() {
            val obs1 = Observable.just("1", "2", "3")
            val obs2 = Observable.just("4", "5", "6")

            Observable.merge(obs1, obs2)
                .subscribe {

                }
        }

        /**
         * 将多个 Observables 的数据项按顺序一对一地合并。
         */
        fun zip() {
            val obs1 = Observable.just("1", "2", "3")
            val obs2 = Observable.just("4", "5", "6")

            Observable.zip(obs1, obs2) { t1, t2 ->
                t1 + t2
            }
        }

        /**
         * 将多个 Observables 最近的数据项合并成一个。
         */
        fun combineLatest() {
            val obs1 = Observable.intervalRange(1, 5, 0, 100, TimeUnit.MILLISECONDS)
            val obs2 = Observable.intervalRange(11, 3, 50, 100, TimeUnit.MILLISECONDS)

            Observable.combineLatest(obs1, obs2) { t1, t2 ->
                t1 + t2
            }
        }
    }

    inner class ErrorOperator {

        /**
         * 在Observable遇到错误时，不将错误传递给观察者的onError方法，而是发射一个特定的项，然后正常终止序列
         */
        fun onErrorReturn() {
            val obs1 = Observable.just("1")
            val obs2 = Observable.error<String>(Exception("Error"))

            Observable.concat(obs1, obs2)
                .onErrorReturn {
                    "!"
                }
                .subscribe {

                }
        }

        /**
         * 检查服务器间token过期判断
         * Observable在遇到错误时，不将错误传递给观察者的onError方法，而是发射另一个备用的Observable，从而允许序列继续而不是终止
         */
        fun onErrorResumeNext() {
            val obs1 = Observable.just("1")
            val obs2 = Observable.error<String>(Exception("Error"))

            Observable.concat(obs1, obs2)
                .onErrorResumeNext {
                    Observable.just("1")
                }
                .subscribe {

                }
        }
    }
}
