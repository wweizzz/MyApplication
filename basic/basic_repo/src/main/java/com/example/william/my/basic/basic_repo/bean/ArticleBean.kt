package com.example.william.my.basic.basic_repo.bean

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArticleListBean(
    var curPage: Int = 0,
    var datas: List<ArticleDetailBean> = arrayListOf()
) : Parcelable

@Parcelize
data class ArticleDetailBean(
    var id: String = "",
    var title: String = "",
    var link: String = "",
    var page: Int = -1,
) : Parcelable {

    val idForList: String
        get() = id.ifEmpty { "" }

    val titleForList: String
        get() = title.ifEmpty { "" }

}