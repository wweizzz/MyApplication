package com.example.william.my.module.sample.activity.topic

import android.view.View
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.lib.utils.Utils
import com.example.william.my.module.sample.work.ExpeditedWorker
import com.example.william.my.module.sample.work.UploadWorker
import java.util.concurrent.TimeUnit

/**
 * WorkManager
 * https://developer.android.google.cn/topic/libraries/architecture/workmanager
 */
@Route(path = RouterPath.Sample.WorkManager)
class WorkManagerActivity : BasicResponseActivity() {

    private lateinit var constraints: Constraints

    private lateinit var oneTimeWorkRequest: OneTimeWorkRequest

    private lateinit var periodicWorkRequest: PeriodicWorkRequest

    override fun initView() {
        super.initView()

        initConstraints()
        initWorkRequest()
    }

    private fun initConstraints() {
        constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED) // 约束运行工作所需的网络类型。例如 Wi-Fi (UNMETERED)。
            .setRequiresBatteryNotLow(true) // 如果设置为 true，那么当设备处于“电量不足模式”时，工作不会运行。
            .setRequiresCharging(true) // 如果设置为 true，那么工作只能在设备充电时运行。
            //.setRequiresDeviceIdle(true) // 如果设置为 true，则要求用户的设备必须处于空闲状态，才能运行工作。
            .setRequiresStorageNotLow(true) // 如果设置为 true，那么当用户设备上的存储空间不足时，工作不会运行。
            .build();
    }

    private fun initWorkRequest() {
        oneTimeWorkRequest = OneTimeWorkRequestBuilder<UploadWorker>() // 一次性工作
            // Additional configuration
            .build()

        periodicWorkRequest =
            PeriodicWorkRequestBuilder<UploadWorker>(1, TimeUnit.HOURS) // 定期工作，可以定义的最短重复间隔是 15 分钟
                // Additional configuration
                .build()

        val expeditedRequest = OneTimeWorkRequestBuilder<ExpeditedWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST) // 执行加急工作
            .build()

        val myWorkRequest = OneTimeWorkRequestBuilder<UploadWorker>()
            // 工作约束
            .setConstraints(constraints)
            // 延迟工作
            .setInitialDelay(3, TimeUnit.SECONDS)
            // 重试和退避政策
            .setBackoffCriteria(
                BackoffPolicy.LINEAR, WorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS
            )
            // 标记工作
            .addTag("upload")
            // 分配输入数据
            .setInputData(
                Data.Builder().putString("key", "inputData").build()
            ).build()
    }

    override fun onResponseClick(view: View) {
        super.onResponseClick(view)

        enqueueWork()
    }

    private fun enqueueWork() {
        //任务提交给系统
        WorkManager.getInstance(this).enqueue(oneTimeWorkRequest)

        // 唯一工作
        WorkManager.getInstance(this)
            .beginUniqueWork("upload", ExistingWorkPolicy.REPLACE, oneTimeWorkRequest).enqueue()

        //链式执行
        WorkManager.getInstance(this)
            .beginWith(listOf(oneTimeWorkRequest, oneTimeWorkRequest)) // 并列运行
            .then(oneTimeWorkRequest) // 链式运行
            .enqueue()

        //观察工作
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(oneTimeWorkRequest.id)
            .observe(this) { value ->
                if (value != null && value.state == WorkInfo.State.SUCCEEDED) {
                    val progress = value.progress
                    Utils.e(tag, progress.toString())
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()

        cancelWork()
    }

    private fun cancelWork() {
        WorkManager.getInstance(this).cancelWorkById(oneTimeWorkRequest.id)
        WorkManager.getInstance(this).cancelWorkById(periodicWorkRequest.id)
    }
}