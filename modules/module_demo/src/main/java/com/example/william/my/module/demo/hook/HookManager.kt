package com.example.william.my.module.demo.hook

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import com.example.william.my.lib.utils.Utils
import java.lang.reflect.Proxy

object HookManager {

    private val TAG = this.javaClass.simpleName

    fun setViewTag(view: View, key: String, value: String) {
        val map: MutableMap<String, String> = HashMap()
        map[key] = value
        view.setTag(view.id, map)
    }

    /**
     * 通过java的反射机制进行hook
     *
     * @param context
     * @param view
     */
    @SuppressLint("PrivateApi,DiscouragedPrivateApi")
    fun hookOnClick(context: Context, view: View?) {
        try {
            // 1. 得到 View 的 ListenerInfo 对象
            val getListenerInfo = View::class.java.getDeclaredMethod("getListenerInfo")
            //修改getListenerInfo为可访问(View中的getListenerInfo不是public)
            getListenerInfo.isAccessible = true
            val listenerInfo = getListenerInfo.invoke(view)

            // 2. 得到 原始的 OnClickListener 对象
            val listenerInfoClz = Class.forName("android.view.View\$ListenerInfo")
            val field = listenerInfoClz.getDeclaredField("mOnClickListener")
            val clickListener = field[listenerInfo] as View.OnClickListener

            // 3. 创建我们自己的点击事件代理类
            val proxyOnClickListener = buildProxy2(context, clickListener)

            // 4. 用我们自己的点击事件代理类，设置到"持有者"中
            field[listenerInfo] = proxyOnClickListener
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // 方式1：自己创建代理类
    private fun buildProxy1(context: Context, clickListener: View.OnClickListener): Any {
        return OnClickListenerProxy(clickListener)
    }

    // 方式2：由于View.OnClickListener是一个接口，所以可以直接用动态代理模式
    // Proxy.newProxyInstance的3个参数依次分别是：
    // 本地的类加载器;
    // 代理类的对象所继承的接口（用Class数组表示，支持多个接口）
    // 代理类的实际逻辑，封装在new出来的InvocationHandler内
    private fun buildProxy2(context: Context, clickListener: View.OnClickListener): Any {
        return Proxy.newProxyInstance(
            context.javaClass.classLoader, arrayOf<Class<*>>(
                View.OnClickListener::class.java
            )
        ) { proxy, method, args ->
            Utils.d(TAG, "点击事件被hook到了") //加入自己的逻辑
            method.invoke(clickListener, *args) //执行被代理的对象的逻辑
        }
    }

    class OnClickListenerProxy    //直接在构造函数中传进来原来的OnClickListener
        (private val mOriginalListener: View.OnClickListener?) : View.OnClickListener {
        override fun onClick(v: View) {
            mOriginalListener?.onClick(v)
            Utils.d(TAG, "点击事件被hook到了!")
            Utils.d(TAG, "hooked" + " : " + v.getTag(v.id).toString())
        }
    }
}