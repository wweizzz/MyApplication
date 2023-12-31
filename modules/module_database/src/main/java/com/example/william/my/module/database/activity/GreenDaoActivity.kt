package com.example.william.my.module.database.activity

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.module.database.greendao.Greendao
import com.example.william.my.module.database.greendao.GreendaoNote
import com.example.william.my.module.database.greendao.dao.GreendaoNoteDao
import com.google.gson.Gson

/**
 * https://greenrobot.org/greendao/features
 * https://github.com/greenrobot/greenDAO
 */
@Route(path = RouterPath.Database.GreenDao)
class GreenDaoActivity : BasicResponseActivity() {

    private lateinit var noteDao: GreendaoNoteDao

    override fun initView() {
        super.initView()

        initDao()

        showNote()
    }

    private fun initDao() {
        noteDao = Greendao.daoSession.greendaoNoteDao
    }

    override fun onResponseClick(view: View) {
        super.onResponseClick(view)

        addNote()
        showNote()
    }

    private fun addNote() {
        val note = GreendaoNote("GreenDao")
        noteDao.insert(note)
    }

    private fun showNote() {
        val notes: List<GreendaoNote> = noteDao.queryBuilder().list()
        showResponse(listToString(notes))
    }

    private fun listToString(list: List<GreendaoNote>): String {
        return if (list.isNotEmpty()) {
            val stringBuilder = StringBuilder()
            for (i in list.indices) {
                stringBuilder.append(Gson().toJson(list[i])).append(",").append("\n")
            }
            stringBuilder.substring(0, stringBuilder.toString().length - 1)
        } else {
            ""
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        noteDao.deleteAll()
    }
}