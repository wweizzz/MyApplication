package com.example.william.my.basic.basic_module.router.service

import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import com.alibaba.android.arouter.facade.template.IProvider
import java.io.File

interface ImageUtilsService : IProvider {
    fun save(bitmap: Bitmap, filePath: String, format: CompressFormat): Boolean
    fun save(bitmap: Bitmap, file: File, format: CompressFormat): Boolean
}