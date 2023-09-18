package com.example.william.my.module.sample.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.example.william.my.module.sample.R
import com.example.william.my.module.sample.work.utils.createNotification

class ExpeditedWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return Result.success()
    }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return ForegroundInfo(
            NOTIFICATION_ID, createNotification(
                applicationContext, id,
                applicationContext.getString(R.string.notification_title_saving_image)
            )
        )
    }

    companion object {
        private const val NOTIFICATION_ID = 1
    }
}