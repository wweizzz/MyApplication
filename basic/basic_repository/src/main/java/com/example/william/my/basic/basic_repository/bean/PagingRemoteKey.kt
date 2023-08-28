package com.example.william.my.basic.basic_repository.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RemoteKey")
data class RemoteKey(
    @PrimaryKey
    @ColumnInfo(name = "tag", collate = ColumnInfo.NOCASE) val tag: String,
    @ColumnInfo(name = "nextPage") val nextPageKey: Int?,
    @ColumnInfo(name = "createdAt") val createdAt: Long? = System.currentTimeMillis()
)