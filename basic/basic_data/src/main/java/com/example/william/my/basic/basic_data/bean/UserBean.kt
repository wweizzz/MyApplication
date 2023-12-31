package com.example.william.my.basic.basic_data.bean

import com.example.william.my.core.okhttp.data.BaseData

data class LoginBean(
    var data: UserBean = UserBean(),
) : BaseData()

data class UserBean(
    var id: String = "",
    var email: String = "",
    var nickname: String = "",
) : BaseData()