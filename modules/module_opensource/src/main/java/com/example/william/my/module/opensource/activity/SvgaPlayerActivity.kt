package com.example.william.my.module.opensource.activity

import android.net.http.HttpResponseCache
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.path.ARouterPath
import com.example.william.my.basic.basic_repository.base.Constants
import com.example.william.my.library.activity.BaseVBActivity
import com.example.william.my.module.opensource.databinding.OpenActivitySvgaBinding
import com.opensource.svgaplayer.SVGADrawable
import com.opensource.svgaplayer.SVGAParser
import com.opensource.svgaplayer.SVGAVideoEntity
import java.io.File
import java.net.URL

/**
 * https://github.com/svga/SVGAPlayer-Android
 */
@Route(path = ARouterPath.Opensource.SVGAPlayer)
class SvgaPlayerActivity : BaseVBActivity<OpenActivitySvgaBinding>() {

    override fun getViewBinding(): OpenActivitySvgaBinding {
        return OpenActivitySvgaBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()

        initSVGAPlayer()
    }

    private fun initSVGAPlayer() {
        SVGAParser.shareParser().init(this)
        val cacheFile = File(applicationContext.cacheDir, "svg")
        val cacheSize = (1024 * 1024 * 128).toLong()
        HttpResponseCache.install(cacheFile, cacheSize)
        val parser: SVGAParser = SVGAParser.shareParser()
        parser.decodeFromURL(URL(Constants.Url_SVGA), object : SVGAParser.ParseCompletion {
            override fun onError() {}
            override fun onComplete(videoItem: SVGAVideoEntity) {
                val drawable = SVGADrawable(videoItem)
                mBinding.svga.setImageDrawable(drawable)
                mBinding.svga.startAnimation()
            }
        })
    }
}