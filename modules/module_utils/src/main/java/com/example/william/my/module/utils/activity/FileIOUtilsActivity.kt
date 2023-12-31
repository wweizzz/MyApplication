package com.example.william.my.module.utils.activity

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.basic.basic_module.router.service.FileIOUtilsService
import java.io.File

@Route(path = RouterPath.Utils.FileIOUtils)
class FileIOUtilsActivity : BasicResponseActivity() {

    override fun onResponseClick(view: View) {
        super.onResponseClick(view)

        writeFileFromString()
    }

    private fun writeFileFromString() {
        val fileIOUtils =
            ARouter.getInstance().build(RouterPath.Service.FileIOUtilsService)
                .navigation() as FileIOUtilsService
        val file =
            File(getExternalFilesDir("FileIOUtils")?.absolutePath + File.separator + "FileIOUtils.txt")
        val successful: Boolean = fileIOUtils.writeFileFromString(file, "FileIOUtils")
        showResponse(successful.toString())
    }
}