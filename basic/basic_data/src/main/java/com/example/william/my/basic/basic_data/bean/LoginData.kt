package com.example.william.my.basic.basic_data.bean

import android.os.Parcelable
import com.example.william.my.core.okhttp.data.BaseData
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginData(
    var data: UserData
) : Parcelable, BaseData()