package com.example.william.my.module.opensource.app

import com.example.william.my.library.app.BaseAppInit
import com.example.william.my.module.opensource.greendao.Greendao
import com.example.william.my.module.opensource.objectbox.ObjectBox

class OpensourceApp : BaseAppInit() {

    override fun init() {
        super.init()

        Greendao.init(app)
        ObjectBox.init(app)
    }
}