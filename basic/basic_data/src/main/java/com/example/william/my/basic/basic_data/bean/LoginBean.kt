package com.example.william.my.basic.basic_data.bean

import com.example.william.my.core.okhttp.data.BaseData

data class LoginBean(
    var data: LoginUserBean
) : BaseData()

class LoginUserBean(
    var id: String = "",
    var email: String = "",
    var nickname: String = "",
) : BaseData()