package com.example.william.my.module.database.activity

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.module.database.objectbox.ObjectBox
import com.example.william.my.module.database.objectbox.ObjectBoxNote
import com.google.gson.Gson
import io.objectbox.Box

/**
 * https://objectbox.io/
 * https://github.com/objectbox/objectbox-java
 */
@Route(path = RouterPath.Database.ObjectBox)
class ObjectBoxActivity : BasicResponseActivity() {

    private lateinit var notesBox: Box<ObjectBoxNote>

    override fun initView() {
        super.initView()

        initBox()

        showNote()
    }

    override fun onResponseClick(view: View) {
        super.onResponseClick(view)

        addNote()

        showNote()
    }

    private fun initBox() {
        // Using ObjectBox Kotlin extension functions (https://docs.objectbox.io/kotlin-support)
        notesBox = ObjectBox.boxStore.boxFor(ObjectBoxNote::class.java)
    }


    private fun addNote() {
        val note = ObjectBoxNote(text = "ObjectBox")
        notesBox.put(note)
    }

    private fun showNote() {
        val notes = notesBox.all
        showResponse(listToString(notes))
    }

    private fun listToString(list: List<ObjectBoxNote>): String {
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
}