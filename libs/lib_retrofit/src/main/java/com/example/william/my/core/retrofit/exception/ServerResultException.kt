package com.example.william.my.core.retrofit.exception

/**
 * 服务器返回自定义异常
 */
class ServerResultException(val code: Int, override val message: String = "") : RuntimeException()