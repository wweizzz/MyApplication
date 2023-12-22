package com.example.william.my.lib.scheme

import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import com.example.william.my.lib.activity.BaseActivity

/**
 * 私有协议Schema跳转帮助类
 */
object ProtocolHelper {

    /**
     * 处理协议跳转
     *
     * @param activity 上下文
     * @param url      跳转地址
     * @param extraMap 额外需要带的参数
     */
    fun handleProtocolEvent(
        activity: Activity?,
        url: String,
        extraMap: Map<String?, Any?>? = null
    ) {
        if (activity == null || TextUtils.isEmpty(url)) {
            return
        }
        if (!TextUtils.isEmpty(url) && url.startsWith(ProtocolConstants.HTTP_SCHEME_HEADER)) {
            // Http开头的网页
            val intent = Intent(activity, BaseActivity::class.java)
            activity.startActivity(intent)
        } else if (!TextUtils.isEmpty(url) && url.startsWith(ProtocolConstants.APP_SCHEME_HEADER)) {
            // App内部跳转
            val path = getProtocolAction(url)
            val paramsMap = getProtocolParams(url)
            val intent = getPageIntent(activity, path)
            if (intent != null) {
                if (paramsMap.isNotEmpty()) {
                    for ((key, value1) in paramsMap) {
                        val value = value1 as String
                        if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
                            intent.putExtra(key, value1)
                        }
                    }
                }
                if (!extraMap.isNullOrEmpty()) {
                    for ((key, value1) in extraMap) {
                        val value = value1 as String?
                        if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
                            intent.putExtra(key, value1)
                        }
                    }
                }
                activity.startActivity(intent)
            }
        }
    }

    /**
     * 私有协议分析,获取协议事件.
     */
    fun getProtocolAction(linkUrl: String?): String? {
        var result: String? = null
        try {
            if (linkUrl == null) {
                return null
            }
            var firstIndex = linkUrl.indexOf("//")
            var lastIndex = linkUrl.indexOf("?")
            if (lastIndex == -1) {
                lastIndex = linkUrl.length
            }
            if (firstIndex == -1) {
                firstIndex = 0
            }
            result = linkUrl.substring(firstIndex + 2, lastIndex)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }

    /**
     * 解析私有协议的参数,其中page字段是需要打开的页面.
     */
    fun getProtocolParams(urlString: String?): Map<String, Any> {
        val paramsMap: MutableMap<String, Any> = HashMap()
        try {
            if (urlString == null || urlString.isEmpty()) {
                return paramsMap
            }
            val questIndex = urlString.indexOf('?')
            if (questIndex == -1) {
                return paramsMap
            }
            val queryString = urlString.substring(questIndex + 1)
            if (queryString.isNotEmpty()) {
                var ampersandIndex: Int
                var lastAmpersandIndex = 0
                var subStr: String
                var param: String
                var value: String
                var paramPair: Array<String>
                do {
                    ampersandIndex = queryString.indexOf('&', lastAmpersandIndex) + 1
                    if (ampersandIndex > 0) {
                        subStr = queryString.substring(lastAmpersandIndex, ampersandIndex - 1)
                        lastAmpersandIndex = ampersandIndex
                    } else {
                        subStr = queryString.substring(lastAmpersandIndex)
                    }
                    paramPair = subStr.split("=").toTypedArray()
                    param = paramPair[0]
                    value = if (paramPair.size == 1) "" else paramPair[1]
                    paramsMap[param] = value
                } while (ampersandIndex > 0)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return paramsMap
    }

    fun getPageIntent(activity: Activity?, page: String?): Intent? {
        var intent: Intent? = null
        if (TextUtils.isEmpty(page)) {
            return null
        }
        if (ProtocolConstants.SCHEME_PAGE_MAIN_PAGE == page) {
            intent = Intent(activity, BaseActivity::class.java)
        }
        if (activity != null && !TextUtils.isEmpty(activity.javaClass.simpleName) && intent != null) {
            intent.putExtra(ProtocolConstants.SCHEME_FROM, activity.javaClass.simpleName)
        }
        return intent
    }
}