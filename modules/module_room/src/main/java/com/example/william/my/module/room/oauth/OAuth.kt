package com.example.william.my.module.room.oauth

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * 授权认证
 */
@Entity(tableName = "oauth")
data class OAuth(
    /**
     * 唯一标识
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,

    /**
     * Token
     */
    @ColumnInfo(name = "RefreshToken")
    var refreshToken: String = System.currentTimeMillis().toString(),

    /**
     * 过期时长
     */
    @ColumnInfo(name = "Expires")
    var expires: Long = 60 * 60 * 24
) {

    /**
     * 不需要添加到数据表中的属性
     */
    @Ignore
    private val mRemark: String? = null
}