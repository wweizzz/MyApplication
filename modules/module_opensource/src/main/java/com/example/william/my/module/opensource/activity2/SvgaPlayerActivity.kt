package com.example.william.my.module.opensource.activity2

import android.net.http.HttpResponseCache
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.base.Constants
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.lib.activity.BaseVBActivity
import com.example.william.my.module.opensource.databinding.OpenActivitySvgaBinding
import com.opensource.svgaplayer.SVGADrawable
import com.opensource.svgaplayer.SVGAParser
import com.opensource.svgaplayer.SVGAVideoEntity
import java.io.File

/**
 * https://github.com/svga/SVGAPlayer-Android
 */
@Route(path = RouterPath.Opensource.SVGAPlayer)
class SvgaPlayerActivity : BaseVBActivity<OpenActivitySvgaBinding>() {

    override fun getViewBinding(): OpenActivitySvgaBinding {
        return OpenActivitySvgaBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        initSVGAPlayer()
    }

    private fun initSVGAPlayer() {
        HttpResponseCache.install(
            File(applicationContext.cacheDir, "svg"),
            (1024 * 1024 * 128).toLong()
        )
        SVGAParser.shareParser().init(this)
        SVGAParser.shareParser()
            .decodeFromAssets(Constants.Url_SVGA, object : SVGAParser.ParseCompletion {
                override fun onError() {}
                override fun onComplete(videoItem: SVGAVideoEntity) {
                    val drawable = SVGADrawable(videoItem)
                    mBinding.svgaImageView.setImageDrawable(drawable)
                    mBinding.svgaImageView.startAnimation()
                }
            })
    }
}