package com.example.william.my.module.demo.activity4

import android.os.AsyncTask
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity
import com.example.william.my.basic.basic_module.router.path.RouterPath
import java.lang.ref.WeakReference

/**
 * AsyncTask
 */
@Route(path = RouterPath.Demo.AsyncTask)
class AsyncTaskActivity : BasicResponseActivity() {

    private var mAsyncTask: MyAsyncTask? = null

    override fun initView() {
        super.initView()

        initAsyncTask()
    }

    private fun initAsyncTask() {
        mAsyncTask = MyAsyncTask(this@AsyncTaskActivity)
    }

    public override fun onResponseClick(view: View) {
        super.onResponseClick(view)

        executeAsyncTask()
    }

    private fun executeAsyncTask() {
        mAsyncTask?.execute()
    }

    override fun onDestroy() {
        super.onDestroy()

        cancelAsyncTask()
    }

    private fun cancelAsyncTask() {
        mAsyncTask?.let { task ->
            if (task.isCancelled && task.status == AsyncTask.Status.RUNNING) {
                mAsyncTask?.cancel(true)
            }
        }
    }

    /**
     * 第一个参数是doInBackground回调中传入的参数
     * 第二个参数是进度，onProgressUpdate的参数类型
     * 第三个参数是doInBackground返回值类型，onPostExecute传入的参数类型
     */
    @Suppress("deprecation")
    private open class MyAsyncTask(activity: AsyncTaskActivity?) : AsyncTask<Int?, Int?, Void?>() {

        private val weakReference: WeakReference<AsyncTaskActivity?> = WeakReference(activity)

        /**
         * 在execute被调用后立即执行
         * 一般用来执行后台操作前对UI做一些标记
         */
        override fun onPreExecute() {
            weakReference.get()?.showResponse("onPreExecute")
        }

        /**
         * 必须重写
         * AsyncTask的关键，用于执行耗时操作
         * 执行过程中可以调用publishProgress来更新进度信息
         */
        override fun doInBackground(vararg params: Int?): Void? {
            var i = 10
            while (i <= 100) {
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                publishProgress(i)
                i += 10
            }
            return null
        }

        /**
         * 调用publishProgress时，此方法被执行
         * 将进度信息更新到UI组件
         */
        override fun onProgressUpdate(vararg values: Int?) {
            weakReference.get()?.showResponse("Progress : " + values[0])
        }

        /**
         * 当后台操作结束时，此方法会被调用，
         * 将计算结果传递到此方法中，直接将结果显示到UI组件。
         */
        override fun onPostExecute(aVoid: Void?) {
            weakReference.get()?.showResponse("onPostExecute")
        }
    }
}