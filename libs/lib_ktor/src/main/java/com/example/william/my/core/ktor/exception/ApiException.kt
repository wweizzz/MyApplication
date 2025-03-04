package com.example.william.my.core.ktor.exception

sealed class ApiException(throwable: Throwable?) : Exception(throwable) {
    class TimeoutError(cause: Throwable) : ApiException(cause)
    class ConnectError(cause: Throwable) : ApiException(cause)
    class UnknownHostError(cause: Throwable) : ApiException(cause)
    class ClientRequestError(cause: Throwable) : ApiException(cause)
    class ServerResponseError(cause: Throwable) : ApiException(cause)
    class SSLHandshakeError(cause: Throwable) : ApiException(cause)
    class UnknownException(cause: Throwable) : ApiException(cause)
}