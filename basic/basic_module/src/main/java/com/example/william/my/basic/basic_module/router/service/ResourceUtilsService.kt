package com.example.william.my.basic.basic_module.router.service

import com.alibaba.android.arouter.facade.template.IProvider

interface ResourceUtilsService : IProvider {
    fun getAssets(assetsFilePath: String): String
}