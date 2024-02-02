package com.example.william.my.basic.basic_repository.bean

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.william.my.basic.basic_data.bean.ArticleBase
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArticleData(
    var curPage: Int = 0,
    var datas: List<ArticleDetailData> = arrayListOf()
) : Parcelable

@Entity(tableName = "Articles")
data class ArticleDetailData(
    @PrimaryKey @ColumnInfo(name = "id") var id: String = "",
    @ColumnInfo(name = "title") var title: String = "",
    @ColumnInfo(name = "link") var link: String = "",
    @ColumnInfo(name = "page") var page: Int = -1,
) : ArticleBase() {

    val idForList: String
        get() = id.ifEmpty { "" }

    val titleForList: String
        get() = title.ifEmpty { "" }

}