package com.example.william.my.module.opensource.greendao

import android.content.Context
import com.example.william.my.module.opensource.greendao.dao.DaoMaster
import com.example.william.my.module.opensource.greendao.dao.DaoSession


object Greendao {

    private const val mDaoName = "note.db"

    lateinit var daoSession: DaoSession
        private set

    fun init(context: Context) {
        // do this once, for example in your Application class
        //val mHelper = DaoMaster.DevOpenHelper(context.applicationContext, mDaoName)
        val mHelper = ExampleOpenHelper(context.applicationContext, mDaoName)
        val mDatabase = mHelper.writableDb
        val mDaoMaster = DaoMaster(mDatabase)
        daoSession = mDaoMaster.newSession()
    }

    class ExampleOpenHelper(context: Context, name: String) :
        DaoMaster.OpenHelper(context, name)
}