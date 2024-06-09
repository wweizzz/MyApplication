package com.example.william.my.basic.basic_module.router.service

import com.alibaba.android.arouter.facade.template.IProvider
import java.io.File
import java.io.InputStream

interface FileIOUtilsService : IProvider {

    fun writeFileFromIS(file: File, inputStream: InputStream): Boolean

    fun writeFileFromString(file: File, str: String): Boolean
}