/*
 * Copyright (C) 2019 The Android Open Source Project
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
package com.example.william.my.module.sample.data

import com.example.william.my.module.sample.data.NetworkResult.Success
import com.google.gson.Gson

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class NetworkResult<out R> {

    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Error(val exception: Exception) : NetworkResult<Nothing>()
    data object Loading : NetworkResult<Nothing>()

    fun string(): String {
        return when (this) {
            is Success<*> -> "onResponse: " + Gson().toJson(this.data)
            is Error -> "onFailure: " + this.exception.message
            Loading -> "onLoading: " + "加载中……"
        }
    }
}

/**
 * `true` if [NetworkResult] is of type [Success] & holds non-null [Success.data].
 */
val NetworkResult<*>.succeeded
    get() = this is Success && data != null
