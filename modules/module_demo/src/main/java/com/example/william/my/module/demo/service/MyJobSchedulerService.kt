/*
 * Copyright 2014 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.william.my.module.demo.service

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.os.Messenger
import com.example.william.my.basic.basic_module.utils.Utils
import com.example.william.my.module.demo.activity4.JobSchedulerActivity

class MyJobSchedulerService : JobService() {

    private var mJobSchedulerMessenger: Messenger? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        mJobSchedulerMessenger = intent.getParcelableExtra(JobSchedulerActivity.KEY_MESSENGER)
        return START_NOT_STICKY
    }

    override fun onStartJob(params: JobParameters): Boolean {
        sendMessage(JobSchedulerActivity.MSG_COLOR_START, params.jobId)

        val duration = params.extras.getLong(JobSchedulerActivity.KEY_WORK_DURATION)

        Handler(Looper.getMainLooper()).postDelayed({

            sendMessage(JobSchedulerActivity.MSG_COLOR_STOP, params.jobId)
            jobFinished(params, false)

        }, duration)

        Utils.toast("on start job: " + params.jobId)

        // Return true as there's more work to be done with this job.
        return true
    }

    override fun onStopJob(params: JobParameters): Boolean {
        sendMessage(JobSchedulerActivity.MSG_COLOR_STOP, params.jobId)

        Utils.toast("on stop job: " + params.jobId)

        // Return false to drop the job.
        return false
    }

    private fun sendMessage(id: Int, params: Any) {
        mJobSchedulerMessenger?.let { messenger ->

            val message = Message.obtain()

            message.what = id
            message.obj = params

            messenger.send(message)
        }
    }
}