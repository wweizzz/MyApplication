package com.example.william.my.module.utils.router

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ResourceUtils
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.basic.basic_module.router.service.ResourceUtilsService

@Route(path = RouterPath.Service.ResourceUtilsService)

class ResourceUtilsServiceImpl : ResourceUtilsService {

    override fun getAssets(assetsFilePath: String): String {
        return ResourceUtils.readAssets2String(assetsFilePath)
    }

    override fun init(context: Context) {}
}