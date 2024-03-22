package com.example.william.my.basic.basic_data.bean

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Login(
    var data: LoginUser
) : Parcelable

@Parcelize
class LoginUser(
    var id: String = "",
    var email: String = "",
    var nickname: String = "",
) : Parcelable