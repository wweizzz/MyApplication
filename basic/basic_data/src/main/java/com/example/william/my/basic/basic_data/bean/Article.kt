package com.example.william.my.basic.basic_data.bean

import com.example.william.my.core.okhttp.data.BaseData

data class ArticleBean(
    var curPage: Int = 0,
    var datas: List<ArticleDetail> = arrayListOf(),
) : BaseData()

data class ArticleDetail(
    var id: String = "",
    var title: String = "",
    var link: String = "",
    var page: Int = -1,
) : BaseData() {

    val idForList: String
        get() = id.ifEmpty { "" }

    val titleForList: String
        get() = title.ifEmpty { "" }

}