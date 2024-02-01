package com.example.william.my.basic.basic_data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArticleListData(
    var curPage: Int = 0,
    var datas: List<ArticleBase> = arrayListOf()
) : Parcelable
