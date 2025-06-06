package com.example.william.my.core.okhttp.interceptor

import android.content.Context
import android.text.TextUtils
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import androidx.core.content.edit

/**
 * Cookie拦截器
 */
class InterceptorCookie(private val mContext: Context) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val builder: Request.Builder = request.newBuilder()
        val cookie = getCookie(request.url.toString(), request.url.host)
        if (!TextUtils.isEmpty(cookie)) {
            //添加cookie
            builder.addHeader("Cookie", cookie)
        }
        //========================================
        val response: Response = chain.proceed(builder.build())
        val headers = response.headers
        var i = 0
        val count = headers.size
        while (i < count) {
            if (response.headers("set-cookie").isNotEmpty()) {
                //存储cookie
                val cookies = response.headers("set-cookie")
                saveCookie(request.url.toString(), request.url.host, encodeCookie(cookies))
            }
            i++
        }
        return response
    }

    /**
     * 整合cookie为唯一字符串
     */
    private fun encodeCookie(cookies: List<String>): String {
        val sb = StringBuilder()
        val set: MutableSet<String> = HashSet()
        for (cookie in cookies) {
            val arr = cookie.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (s in arr) {
                if (set.contains(s)) continue
                set.add(s)
            }
        }
        for (cookie in set) {
            sb.append(cookie).append(";")
        }
        val last = sb.lastIndexOf(";")
        if (sb.length - 1 == last) {
            sb.deleteCharAt(last)
        }
        return sb.toString()
    }

    /**
     * 保存cookie到本地，这里我们分别为该url和host设置相同的cookie，其中host可选
     * 这样能使得该cookie的应用范围更广
     */
    private fun saveCookie(url: String, domain: String, cookies: String) {
        val sp = mContext.getSharedPreferences("cookie", Context.MODE_PRIVATE)
        sp.edit {
            if (TextUtils.isEmpty(url)) {
                throw NullPointerException("url is null.")
            } else {
                putString(url, cookies)
            }
            if (!TextUtils.isEmpty(domain)) {
                putString(domain, cookies)
            }
        }
    }

    private fun getCookie(url: String, domain: String): String {
        val sp = mContext.getSharedPreferences("cookie", Context.MODE_PRIVATE)
        if (!TextUtils.isEmpty(url) &&
            sp.contains(url) &&
            !TextUtils.isEmpty(sp.getString(url, ""))
        ) {
            return sp.getString(url, "") ?: ""
        }
        return if (!TextUtils.isEmpty(domain) &&
            sp.contains(domain) &&
            !TextUtils.isEmpty(sp.getString(domain, ""))
        ) {
            sp.getString(domain, "") ?: ""
        } else ""
    }
}