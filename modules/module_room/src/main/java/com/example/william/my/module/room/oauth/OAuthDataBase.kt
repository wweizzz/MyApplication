package com.example.william.my.module.room.oauth

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * exportSchema = false 或者
 * arg("room.schemaLocation", "$projectDir/schemas")
 */
@Database(
    entities = [OAuth::class],
    version = 1,
    exportSchema = false,
)
abstract class OAuthDataBase : RoomDatabase() {

    companion object {
        private const val DB_NAME = "personal-db"

        private var instance: OAuthDataBase? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: createDataBase(context).also {
                    instance = it
                }
            }

        private fun createDataBase(context: Context): OAuthDataBase {
            return Room.databaseBuilder(context, OAuthDataBase::class.java, DB_NAME)
                //是否允许在主线程进行查询
                .allowMainThreadQueries()
                //数据库创建和打开后的回调
                //.addCallback()
                //设置查询的线程池
                //.setQueryExecutor()
                //.openHelperFactory()
                //room的日志模式
                //.setJournalMode()
                //数据库升级异常之后的回滚
                //.fallbackToDestructiveMigration()
                //数据库升级异常后根据指定版本进行回滚
                //.fallbackToDestructiveMigrationFrom()
                // .addMigrations(CacheDatabase.sMigration)
                .build()
        }

        fun exit() {
            instance?.close()
            instance = null
        }
    }

    abstract fun getOAuthDao(): OAuthDao

}