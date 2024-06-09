package com.example.william.my.module.utils.router

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ImageUtils
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.basic.basic_module.router.service.ImageUtilsService
import java.io.File

@Route(path = RouterPath.Service.ImageUtilsService)
class ImageUtilsServiceImpl : ImageUtilsService {

    override fun save(bitmap: Bitmap, filePath: String, format: CompressFormat): Boolean {
        return ImageUtils.save(bitmap, filePath, format)
    }

    override fun save(bitmap: Bitmap, file: File, format: CompressFormat): Boolean {
        return ImageUtils.save(bitmap, file, format)
    }

    override fun init(context: Context) {}
}