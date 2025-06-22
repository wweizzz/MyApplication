package com.example.william.my.module.opensource.method

import android.os.Parcelable
import android.text.Spannable
import android.view.KeyEvent
import android.widget.EditText
import com.iyao.eastat.KeyCodeDeleteHelper
import com.iyao.eastat.NoCopySpanEditableFactory
import com.iyao.eastat.SpanFactory
import com.iyao.eastat.span.DataBindingSpan
import com.iyao.eastat.watcher.SelectionSpanWatcher
import kotlinx.parcelize.Parcelize

object Weibo {
    fun init(editText: EditText) {
        editText.text = null
        editText.setEditableFactory(
            NoCopySpanEditableFactory(
                SelectionSpanWatcher(DataBindingSpan::class)
            )
        )
        editText.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN) {
                return@setOnKeyListener KeyCodeDeleteHelper.onDelDown((v as EditText).text)
            }
            return@setOnKeyListener false
        }
    }

    fun newSpannable(user: User): Spannable {
        return SpanFactory.newSpannable(user.nickname, user)
    }
}


@Parcelize
data class User(
    var id: String = "",
    var email: String = "",
    var nickname: String = "",
) : Parcelable