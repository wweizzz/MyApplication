package com.example.william.my.core.okhttp.builder

import com.example.william.my.core.okhttp.body.RequestProgressBody
import com.example.william.my.core.okhttp.config.MediaType
import com.example.william.my.core.okhttp.listener.RequestProgressListener
import okhttp3.FormBody
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class RequestBody {

    open class Builder {

        private var mProgressListener: RequestProgressListener? = null

        fun addListener(listener: RequestProgressListener) {
            mProgressListener = listener
        }

        private val mFormBuilder: FormBody.Builder =
            FormBody.Builder()

        fun add(key: String, value: String): Builder {
            mFormBuilder.add(key, value)
            return this
        }

        fun buildForm(): RequestBody {
            return RequestProgressBody(mFormBuilder.build(), mProgressListener)
        }

        private val mMultipartBuilder: MultipartBody.Builder =
            MultipartBody.Builder().setType(MultipartBody.FORM)

        fun addParam(key: String, value: String): Builder {
            mMultipartBuilder.addFormDataPart(
                key,
                value
            )
            return this
        }

        fun addFile(name: String, file: File, fileName: String = file.name): Builder {
            mMultipartBuilder.addFormDataPart(
                name,
                fileName,
                file.asRequestBody(MediaType.MEDIA_TYPE_MULTIPART)
            )
            return this
        }

        fun buildMultipart(): RequestBody {
            return RequestProgressBody(mMultipartBuilder.build(), mProgressListener)
        }
    }
}