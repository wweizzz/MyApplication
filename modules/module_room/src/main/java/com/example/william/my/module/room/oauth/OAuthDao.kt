package com.example.william.my.module.room.oauth

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow

@Dao
interface OAuthDao {

    /**
     * 获取用户信息
     *
     * @param userId
     * @return
     */
    @Query("SELECT * FROM oauth WHERE id IN (:userId)")
    fun getUserById(userId: String): OAuth

    /**
     * 更新用户信息
     *
     * @param oAuth
     */
    @Update
    fun updateOAuth(oAuth: OAuth)

    /**
     * 冲突策略为取代旧数据同时继续事务
     * 插入用户信息
     *
     * @param oAuth
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOAuth(oAuth: OAuth)

    /**
     * 删除全部用户信息
     */
    @Query("DELETE FROM OAuth")
    fun deleteAllOAuth()

    /**
     * 删除用户信息
     *
     * @param oAuth
     */
    @Delete
    fun deleteOAuth(oAuth: OAuth)

    @Query("SELECT * FROM oauth WHERE id = :userId")
    fun getUserIdFlow(userId: String): Flow<OAuth>

    /**
     * 1.若数据库中没有用户，那么Maybe就会被complete（RxJava中概念）
     * 2.若数据库中有一个用户，那么Maybe就会触发onSuccess方法并且被complete
     * 3.若数据库中用户信息在Maybe被complete之后被更新了，啥都不会发生
     *
     * @param
     * @return
     */
    @Query("SELECT * FROM oauth WHERE id = :userId")
    fun getUserMaybe(userId: String): Maybe<OAuth>

    /**
     * 1.若数据库中没有用户，那么Single就会触发onError(EmptyResultSetException.class)
     * 2.若数据库中有一个用户，那么Single就会触发onSuccess
     * 3.若数据库中用户信息在Single.onComplete调用之后被更新了，啥都不会发生，因为数据流已经完成了
     *
     * @param userId
     * @return
     */
    @Query("SELECT * FROM oauth WHERE id = :userId")
    fun getUserSingle(userId: String): Single<OAuth>

    /**
     * 1.若数据库中没有用户，那么Flowable就不会发射事件，既不运行onNext,也不运行onError
     * 2.若数据库中有一个用户，那么Flowable就会触发onNext
     * 3.若数据库中用户信息被更新了，Flowable就会自动发射事件，允许你根据更新的数据来更新UI界面
     *
     * @param userId
     * @return
     */
    @Query("SELECT * FROM oauth WHERE id = :userId")
    fun getUserFlowable(userId: String): Flowable<OAuth>

    @Query("SELECT * FROM OAuth ORDER BY Expires DESC")
    fun getAllOAuth(): List<OAuth>

    @Query("SELECT * FROM OAuth ORDER BY Expires DESC")
    fun getAllOAuthFlow(): Flow<List<OAuth>>
}