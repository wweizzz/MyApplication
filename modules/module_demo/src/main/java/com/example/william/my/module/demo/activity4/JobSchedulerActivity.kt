package com.example.william.my.module.demo.activity4

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.os.Messenger
import android.os.PersistableBundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.basic.basic_module.utils.Utils
import com.example.william.my.module.demo.service.MyJobSchedulerService
import java.lang.ref.WeakReference

@Route(path = RouterPath.Demo.JobScheduler)
class JobSchedulerActivity : BasicResponseActivity() {

    private var mServiceComponent: ComponentName? = null

    private var mJobId = 0

    private class JobSchedulerHandler(activity: JobSchedulerActivity) :
        Handler(Looper.getMainLooper()) {

        private val weakReference: WeakReference<JobSchedulerActivity?> = WeakReference(activity)

        override fun handleMessage(msg: Message) {
            val mActivity = weakReference.get() ?: return
            when (msg.what) {
                MSG_COLOR_START -> {
                    // Start received, turn on the indicator and show text.
                    mActivity.showResponse(String.format("Job ID %s %s", msg.obj, "started"))
                }

                MSG_COLOR_STOP -> {
                    // Stop received, turn on the indicator and show text.
                    mActivity.showResponse(String.format("Job ID %s %s", msg.obj, "stopped"))
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        startService()
    }

    private fun startService() {
        val jobHandler = JobSchedulerHandler(this)
        val jobMessenger = Messenger(jobHandler)

        val intent = Intent(this, MyJobSchedulerService::class.java)
        intent.putExtra(KEY_MESSENGER, jobMessenger)
        startService(intent)
    }

    override fun onStop() {
        super.onStop()

        stopService()
    }

    private fun stopService() {
        stopService(Intent(this, MyJobSchedulerService::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()

        cancelAllJobs()
    }

    override fun onResponseClick(view: View) {
        super.onResponseClick(view)

        scheduleJob()
    }

    /**
     * 执行 JobScheduler
     */
    private fun scheduleJob() {
        mServiceComponent = ComponentName(this, MyJobSchedulerService::class.java)

        mServiceComponent?.let { jobService ->

            /*
             * Builder构造方法接收两个参数
             * 第一个参数是jobId，每个app或者说uid下不同的Job,它的jobId必须是不同的
             * 第二个参数是我们自定义的JobService,系统会回调我们自定义的JobService中的onStartJob和onStopJob方法
             */
            val builder = JobInfo.Builder(mJobId++, jobService)

            //设置至少延迟多久后执行，单位毫秒.
            builder.setMinimumLatency((3 * 1000).toLong())

            //设置最多延迟多久后执行，单位毫秒。
            builder.setOverrideDeadline((5 * 1000).toLong())

            //设置需要的网络条件，有三个取值：
            //JobInfo.NETWORK_TYPE_NONE（无网络时执行，默认）、
            //JobInfo.NETWORK_TYPE_ANY（有网络时执行）、
            //JobInfo.NETWORK_TYPE_UNMETERED（网络无需付费时执行）
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)

            //是否在空闲时执行
            builder.setRequiresDeviceIdle(true)
            //是否在充电时执行
            builder.setRequiresCharging(true)

            // Extras, work duration.
            val extras = PersistableBundle()
            extras.putLong(KEY_WORK_DURATION, 1000)
            builder.setExtras(extras)

            // Schedule job
            showResponse("Scheduling job")

            // 1.获取jobScheduler对象
            val jobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
            jobScheduler.schedule(builder.build())
        }
    }

    /**
     * 取消 JobScheduler
     */
    private fun cancelAllJobs() {
        val jobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
        jobScheduler.cancelAll()
        Utils.toast("All jobs cancelled")
    }

    /**
     * 完成 JobScheduler
     */
    fun finishJob() {
        val jobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
        val jobs = jobScheduler.allPendingJobs
        if (jobs.size > 0) {
            val jobId = jobs[0].id
            jobScheduler.cancel(jobId)
            Utils.toast("取消 : ")
        } else {
            Utils.toast("No jobs to cancel")
        }
    }

    companion object {
        const val MSG_COLOR_START = 0
        const val MSG_COLOR_STOP = 1
        const val KEY_MESSENGER = ".MESSENGER_INTENT_KEY"
        const val KEY_WORK_DURATION = ".WORK_DURATION_KEY"
    }
}