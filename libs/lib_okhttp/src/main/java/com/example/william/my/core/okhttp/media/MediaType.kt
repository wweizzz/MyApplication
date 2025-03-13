package com.example.william.my.core.okhttp.media

import okhttp3.MediaType.Companion.toMediaType

object MediaType {
    val MEDIA_TYPE_JSON = "application/json; charset=utf-8".toMediaType()
    val MEDIA_TYPE_MULTIPART = "multipart/form-data; charset=utf-8".toMediaType()
}