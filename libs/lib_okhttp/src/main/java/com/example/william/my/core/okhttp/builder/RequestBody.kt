package com.example.william.my.core.okhttp.builder

import com.example.william.my.core.okhttp.body.RequestProgressBody
import com.example.william.my.core.okhttp.listener.RequestProgressListener
import com.example.william.my.core.okhttp.media.MediaType
import okhttp3.FormBody
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.File

class RequestBody {

    open class Builder {

        private var mProgressListener: RequestProgressListener? = null

        fun addListener(listener: RequestProgressListener) {
            mProgressListener = listener
        }

        private val mFormBuilder: FormBody.Builder =
            FormBody.Builder()

        fun addForm(key: String, value: String): Builder {
            mFormBuilder.add(key, value)
            return this
        }

        fun buildForm(): RequestBody {
            return RequestProgressBody(mFormBuilder.build(), mProgressListener)
        }

        private val mMultipartBuilder: MultipartBody.Builder =
            MultipartBody.Builder().setType(MultipartBody.FORM)

        fun addMultipart(key: String, value: String): Builder {
            mMultipartBuilder.addFormDataPart(key, value)
            return this
        }

        fun addFile(name: String, file: File, fileName: String = file.name): Builder {
            mMultipartBuilder.addFormDataPart(
                name, fileName, file.asRequestBody(MediaType.MEDIA_TYPE_MULTIPART)
            )
            return this
        }

        fun buildMultipart(): RequestBody {
            return RequestProgressBody(mMultipartBuilder.build(), mProgressListener)
        }

        private val mJsonBuilder: JSONObject = JSONObject()

        fun addJson(key: String, value: String): Builder {
            mJsonBuilder.put(key, value)
            return this
        }

        fun buildJson(): RequestBody {
            val body = mJsonBuilder.toString().toRequestBody(MediaType.MEDIA_TYPE_JSON)
            return RequestProgressBody(body, mProgressListener)
        }
    }
}