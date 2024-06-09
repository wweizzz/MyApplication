package com.example.william.my.module.sample.activity.kotlin

import com.example.william.my.basic.basic_module.activity.BasicResponseActivity
import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * kotlin 委托
 */
class MyDelegateActivity : BasicResponseActivity() {

    // 约束类
    interface Base {
        fun debug()
    }

    // 被委托对象，实现了约束类的接口
    class BaseImpl : Base {
        override fun debug() {

        }
    }

    // 委托对象，同时把被委托对象作为委托对象的属性，通过构造方法传入。
    class Derived(private val base: Base) : Base by base {
        fun error() {

        }
    }

    /**
     * 1. 类委托
     */
    fun classDelegate() {
        val base = BaseImpl()
        val derived = Derived(base)
        derived.debug()
        derived.error()
    }

    //==============================================================================================

    // 定义包含属性委托的类
    class Example {
        var delegate: String by Delegate()
    }

    // 委托的类
    class Delegate : ReadWriteProperty<Any?, String> {

        override fun getValue(thisRef: Any?, property: KProperty<*>): String {
            return "$thisRef, 这里委托了 ${property.name} 属性"
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
            println("$thisRef 的 ${property.name} 属性赋值为 $value")
        }
    }

    /**
     * 2. 属性委托
     */
    fun attrDelegate() {
        val e = Example()
        println(e.delegate)     // 调用 getValue() 函数

        e.delegate = "Delegate"   // 调用 setValue() 函数
        println(e.delegate)
    }

    //==============================================================================================

    private val lazyValue: String by lazy {
        println("Delegate!")     // 第一次调用输出，第二次调用不执行
        "Hello"
    }

    /**
     * 1. 延迟属性 Lazy
     */
    fun lazyDelegate() {
        println(lazyValue)   // 第一次执行，执行两次输出表达式
        println(lazyValue)   // 第二次执行，只输出返回值
    }

    //==============================================================================================

    class Bean {
        var notNullStr: String by Delegates.notNull()
    }

    /**
     * 2. Not Null
     */
    fun notNullDelegate() {
        Bean().notNullStr = "Str"
        println(Bean().notNullStr)
    }


    //==============================================================================================

    class User {
        var name: String by Delegates.observable("初始值") { property, old, new ->
            println(property.name + " 旧值：$old -> 新值：$new")
        }
    }

    /**
     * 2. 可观察属性 Observable
     */
    fun observableDelegate() {
        val user = User()
        user.name = "第一次赋值"
        user.name = "第二次赋值"
    }

    //==============================================================================================

    class User2 {
        var name: String by Delegates.vetoable("初始值") { property, old, new ->
            println(property.name + " 旧值：$old -> 新值：$new")
            // 新值不能为空，否则保持原值
            new.isNotEmpty()
        }
    }

    /**
     * 3. 可观察属性 vetoable
     */
    fun vetoableDelegate2() {
        val user = User2()
        user.name = ""
        user.name = "第二次赋值"
    }

    //==============================================================================================

    class Site(val map: Map<String, Any?>) {
        val key1: String by map
        val key2: String by map
    }

    /**
     * 5. 把属性储存在映射中
     */
    fun mapDelegate() {
        // 构造函数接受一个映射参数
        val site = Site(
            mapOf("key1" to "key", "key3" to "value")
        )

        // 读取映射值
        println(site.key1)
        println(site.key2)
    }
}