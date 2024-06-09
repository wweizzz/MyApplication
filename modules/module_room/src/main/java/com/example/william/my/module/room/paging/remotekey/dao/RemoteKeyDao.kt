package com.example.william.my.module.room.paging.remotekey.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.william.my.module.room.paging.remotekey.data.RemoteKeyData
import io.reactivex.rxjava3.core.Single

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKey(key: RemoteKeyData)

    @Query("SELECT * FROM RemoteKey WHERE tag = :tag")
    suspend fun remoteKeyByTag(tag: String): RemoteKeyData?

    @Query("DELETE FROM RemoteKey WHERE tag = :tag")
    suspend fun deleteByTag(tag: String)

    @Query("Select createdAt From RemoteKey Order By createdAt DESC LIMIT 1")
    suspend fun lastUpdated(): Long?

    @Query("SELECT * FROM RemoteKey WHERE tag = :tag")
    fun remoteKeyByTagSingle(tag: String): Single<RemoteKeyData>
}
