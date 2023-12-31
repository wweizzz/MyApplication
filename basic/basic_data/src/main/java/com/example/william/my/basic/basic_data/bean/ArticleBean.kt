package com.example.william.my.basic.basic_data.bean

import com.example.william.my.core.okhttp.data.BaseData

data class ArticleListBean(
    var curPage: Int = 0, var datas: List<ArticleBean> = arrayListOf()
) : BaseData()

data class ArticleBean(
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