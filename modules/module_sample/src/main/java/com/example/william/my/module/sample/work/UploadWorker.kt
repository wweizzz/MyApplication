package com.example.william.my.module.sample.work

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.william.my.basic.basic_module.utils.Utils

class UploadWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    private val TAG = this.javaClass.simpleName

    override fun doWork(): Result {
        // Get the input
        val input = inputData.getString("key")

        // Do the work here--in this case, upload the images.
        uploadImages(input!!)

        // Create the output of the work
        // Data 对象的大小上限为 10KB。
        val outputData = Data.Builder()
            .putString("key", "outData")
            .build()

        //更新进度
        setProgressAsync(outputData)

        // Indicate whether the task finished successfully with the Result
        return Result.success(outputData)
    }

    private fun uploadImages(s: String) {
        Utils.logcat(TAG, "uploadImages: $s")
    }
}
