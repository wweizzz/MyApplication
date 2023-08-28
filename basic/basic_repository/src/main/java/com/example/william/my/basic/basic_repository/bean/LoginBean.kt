package com.example.william.my.basic.basic_repository.bean

import com.example.william.my.core.okhttp.data.BaseData

data class LoginBean(
    var data: UserData = UserData(),
) : BaseData()

data class UserData(
    var id: String = "",
    var email: String = "",
    var nickname: String = "",
) : BaseData()