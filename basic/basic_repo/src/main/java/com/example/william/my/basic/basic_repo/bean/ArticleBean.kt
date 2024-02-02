package com.example.william.my.basic.basic_repo.bean

import android.os.Parcelable
import com.example.william.my.basic.basic_data.bean.ArticleBase
import com.example.william.my.basic.basic_data.bean.ArticleDetailBase
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArticleBean(
    var curPage: Int = 0,
    var datas: List<ArticleDetailBean> = arrayListOf()
) : ArticleBase()

data class ArticleDetailBean(
    var id: String = "",
    var title: String = "",
    var link: String = "",
    var page: Int = -1,
) : ArticleDetailBase() {

    val idForList: String
        get() = id.ifEmpty { "" }

    val titleForList: String
        get() = title.ifEmpty { "" }

}