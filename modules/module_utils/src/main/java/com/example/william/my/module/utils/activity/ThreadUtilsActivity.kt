package com.example.william.my.module.utils.activity

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ThreadUtils
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity
import com.example.william.my.basic.basic_module.router.path.RouterPath


/**
 * CPU 密集型任务：线程池中线程个数应尽量少，推荐配置为 (CPU 核心数 + 1)；
 *
 * IO 密集型任务：由于 IO 操作速度远低于 CPU 速度，那么在运行这类任务时，CPU 绝大多数时间处于空闲状态，那么线程池可以配置尽量多些的线程，以提高 CPU 利用率，推荐配置为 (2 * CPU 核心数 + 1)；
 *
 * 混合型任务：可以拆分为 CPU 密集型任务和 IO 密集型任务，当这两类任务执行时间相差无几时，通过拆分再执行的吞吐率高于串行执行的吞吐率，但若这两类任务执行时间有数据级的差距，那么没有拆分的意义。
 */
@Route(path = RouterPath.Utils.ThreadUtilsActivity)
class ThreadUtilsActivity : BasicResponseActivity() {

    override fun onResponseClick(view: View) {
        super.onResponseClick(view)

        LogUtils.e("isMainThread : " + isMainThread())

    }

    private fun isMainThread(): Boolean {
        return ThreadUtils.isMainThread()
    }

    private fun runOnUiThread() {
        ThreadUtils.runOnUiThread {

        }
    }

    private fun executeByIo() {
        ThreadUtils.executeByIo(object : ThreadUtils.SimpleTask<Unit>() {
            override fun doInBackground() {
                // 在这里执行 IO 操作
                return
            }

            override fun onSuccess(result: Unit) {
                // IO 操作成功完成后执行的代码
            }
        })
    }
}