package com.example.william.my.basic.basic_data.bean

import android.os.Parcelable
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class Login(
    var data: LoginUser
) : Parcelable {
    fun string(): String? {
        return Gson().toJson(this)
    }
}

@Parcelize
class LoginUser(
    var id: String = "",
    var email: String = "",
    var nickname: String = "",
) : Parcelable {
    fun string(): String? {
        return Gson().toJson(this)
    }
}