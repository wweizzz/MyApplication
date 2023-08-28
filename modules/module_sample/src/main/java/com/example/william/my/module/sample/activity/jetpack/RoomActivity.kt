package com.example.william.my.module.sample.activity.jetpack

import android.view.View
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity
import com.example.william.my.basic.basic_module.router.path.ARouterPath
import com.example.william.my.module.sample.database.OAuth
import com.example.william.my.module.sample.database.OAuthDao
import com.example.william.my.module.sample.database.OAuthDataBase
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

/**
 * Room
 * https://developer.android.google.cn/jetpack/androidx/releases/room
 */
@Route(path = ARouterPath.Sample.Room)
class RoomActivity : BasicResponseActivity() {

    private val mOAuthDao: OAuthDao by lazy {
        OAuthDataBase.getInstance(this).getOAuthDao()
    }

    override fun initView() {
        super.initView()

        showOAuth()
    }

    override fun onResponseClick(view: View) {
        super.onResponseClick(view)

        addOAuth()
        //showOAuth()
    }

    private fun addOAuth() {
        /*
         * query ,Insert ,Update ,需要在后台线程执行
         */
        Executors.newSingleThreadExecutor().execute {
            val oAuth = OAuth()
            mOAuthDao.insertOAuth(oAuth)
        }
    }

    private fun showOAuth() {
        lifecycleScope.launch {
            mOAuthDao.getAllOAuthFlow().collect { list: List<OAuth> ->
                showResponse(listToString(list))
            }
        }
    }

    private fun listToString(list: List<OAuth>): String {
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
        mOAuthDao.deleteAllOAuth()
    }
}