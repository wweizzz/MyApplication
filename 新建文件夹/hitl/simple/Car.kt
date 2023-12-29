package com.example.william.my.module.sample.hitl.simple

import com.example.william.my.lib.utils.Utils
import javax.inject.Inject

//    class Engine {
//        fun start() {
//            println("Engine started")
//        }
//    }
//
//    class Car1 { // Car类自己创建Engine对象
//
//        private var engine: Engine = Engine()
//
//        fun drive() {
//            engine.start()
//            println("Car is driving")
//        }
//    }
//
//    class Car2(private val engine: Engine) {  // Engine对象通过构造函数注入
//
//        fun drive() {
//            engine.start()
//            println("Car is driving")
//        }
//    }

class Engine @Inject constructor() {
    fun start() {
        Utils.e("Hilt", "Engine started")
    }
}

class Car @Inject constructor(private val engine: Engine) {
    fun drive() {
        engine.start()
        Utils.e("Hilt", "Car is driving")
    }
}