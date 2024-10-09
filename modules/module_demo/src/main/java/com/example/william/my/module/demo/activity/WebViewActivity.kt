package com.example.william.my.module.demo.activity

import android.annotation.SuppressLint
import android.net.http.SslError
import android.webkit.JavascriptInterface
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.lib.activity.BaseVBActivity
import com.example.william.my.module.demo.databinding.DemoActivityWebviewBinding

@Route(path = RouterPath.Demo.WebView)
class WebViewActivity : BaseVBActivity<DemoActivityWebviewBinding>() {

    override fun getViewBinding(): DemoActivityWebviewBinding {
        return DemoActivityWebviewBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()

        initWebView()
    }

    @SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
    private fun initWebView() {
        //隐藏滚动条
        mBinding.webView.isVerticalScrollBarEnabled = true
        mBinding.webView.isHorizontalScrollBarEnabled = true

        //启用JavaScript
        mBinding.webView.settings.javaScriptEnabled = true
        //启用视图支持
        mBinding.webView.settings.useWideViewPort = true
        //适应屏幕宽度
        mBinding.webView.settings.loadWithOverviewMode = true
        //手势缩放
        mBinding.webView.settings.builtInZoomControls = true
        //隐藏缩放按钮
        mBinding.webView.settings.displayZoomControls = false
        //DOM Storage
        mBinding.webView.settings.domStorageEnabled = true
        // 关闭file域访问，禁止file域对http域进行访问
        // setAllowFileAccessFromFileURLs&setAllowUniversalAccessFromFileURLs
        // Android 4.1版本之前这两个API默认是true，需要显式设置为false
        val headers: Map<String, String> = HashMap()
        //添加HTTP头信息
        mBinding.webView.loadUrl("https://www.baidu.com/", headers)
        mBinding.webView.webViewClient = object : WebViewClient() {
            /**
             * 拦截资源请求
             */
            @Deprecated("Deprecated in Java")
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

            /**
             * 拦截资源请求
             */
            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                view.loadUrl(request.url.toString())
                return true
            }

            /**
             * 忽略SSL验证
             */
            @SuppressLint("WebViewClientOnReceivedSslError")
            override fun onReceivedSslError(
                view: WebView,
                handler: SslErrorHandler,
                error: SslError
            ) {
                handler.proceed()
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
            }
        }
        mBinding.webView.webChromeClient = object : WebChromeClient() {

        }
        mBinding.webView.addJavascriptInterface(object :
            WebViewInterface(object : WebViewJsCallback() {
                override fun closeWebViewPage() {

                }
            }) {

        }, "interfaceName")
    }

    open class WebViewInterface(private val jsCallback: WebViewJsCallback?) {

        @JavascriptInterface
        fun closeWebViewPage() {
            jsCallback?.closeWebViewPage()
        }
    }

    abstract class WebViewJsCallback {
        abstract fun closeWebViewPage()
    }

    override fun onBackPressed() {
        if (mBinding.webView.canGoBack()) {
            mBinding.webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}