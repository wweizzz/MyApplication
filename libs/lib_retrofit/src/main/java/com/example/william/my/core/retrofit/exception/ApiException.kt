package com.example.william.my.core.retrofit.exception

/**
 * 统一异常管理
 */
class ApiException(throwable: Throwable, var code: Int) : Exception(throwable) {

    override var message: String = ""

    object Error {
        /**
         * 未知错误
         */
        const val UNKNOWN = 1000

        /**
         * 网络错误
         */
        const val HTTP_ERROR = 1001

        /**
         * 链接错误
         */
        const val CONNECT_ERROR = 1003

        /**
         * 连接超时
         */
        const val TIMEOUT_ERROR = 1004

        /**
         * 证书验证失败
         */
        const val SSL_ERROR = 1005

        /**
         * 解析错误
         */
        const val PARSE_ERROR = 1002
    }
}