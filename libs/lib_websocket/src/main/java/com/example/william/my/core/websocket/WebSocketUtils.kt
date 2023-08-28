package com.example.william.my.core.websocket

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import java.io.EOFException
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeoutException

object WebSocketUtils {

    private val webSocketMap = ConcurrentHashMap<String, WebSocket>()
    private val webSocketObservableMap = ConcurrentHashMap<String, Observable<WebSocketInfo>>()

    fun setWebSocket(url: String, webSocket: WebSocket) {
        webSocketMap[url] = webSocket
    }

    private fun getWebSocket(url: String): WebSocket? {
        return webSocketMap[url]
    }

    fun createWebSocket(url: String): Observable<WebSocketInfo> {
        return createWebSocket(url, Request.Builder().get().url(url).build(), OkHttpClient())
    }

    fun createWebSocket(request: Request): Observable<WebSocketInfo> {
        return createWebSocket(request.url.toString(), request, OkHttpClient())
    }

    fun createWebSocket(url: String, okHttpClient: OkHttpClient): Observable<WebSocketInfo> {
        return createWebSocket(url, Request.Builder().get().url(url).build(), okHttpClient)
    }

    fun createWebSocket(request: Request, okHttpClient: OkHttpClient): Observable<WebSocketInfo> {
        return createWebSocket(request.url.toString(), request, okHttpClient)
    }

    private fun createWebSocket(
        url: String,
        request: Request,
        okHttpClient: OkHttpClient = OkHttpClient()
    ): Observable<WebSocketInfo> {
        return webSocketObservableMap[url]?.let { observable ->
            val webSocket = webSocketMap[request.url.toString()]
            webSocket?.let {
                observable.startWithItem(WebSocketInfo(webSocket, null, true))
            } ?: observable
        } ?: Observable.create(WebSocketOnSubscribe(url, request, okHttpClient))
            .retry { throwable ->
                throwable is EOFException || throwable is TimeoutException
            }
            .doOnDispose {
                webSocketMap.remove(url)
                webSocketObservableMap.remove(url)
            }
            .doOnNext { webSocketInfo ->
                if (webSocketInfo.isOnOpen) {
                    webSocketMap[url] = webSocketInfo.webSocket
                }
            }.share()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .also { observable ->
                webSocketObservableMap[request.url.toString()] = observable
            }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun send(url: String, message: String) {
        send(url, Request.Builder().get().url(url).build(), message)
    }

    fun send(request: Request, message: String) {
        send(request.url.toString(), request, message)
    }

    private fun send(url: String, request: Request, message: String) {
        createWebSocket(url, request).subscribe(object : WebSocketObserver() {
            override fun onMessage(webSocket: WebSocket?, text: String?) {
                super.onMessage(webSocket, text)
                webSocket!!.send(message)
            }
        })
    }

    fun cancel(url: String) {
        cancel(url, Request.Builder().get().url(url).build())
    }

    fun cancel(request: Request) {
        cancel(request.url.toString(), request)
    }

    private fun cancel(url: String, request: Request) {
        getWebSocket(url)?.cancel()
        getDisposable(url, request).dispose()
    }

    fun getDisposable(url: String): Disposable {
        return createWebSocket(url).subscribe()
    }

    fun getDisposable(request: Request): Disposable {
        return createWebSocket(request).subscribe()
    }

    private fun getDisposable(url: String, request: Request): Disposable {
        return createWebSocket(url, request).subscribe()
    }
}