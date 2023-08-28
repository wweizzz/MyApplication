package com.example.william.my.core.download.state

enum class DownloadState(val value: Int) {
    /**
     * 无状态
     */
    NONE(0),

    /**
     * 等待
     */
    WAITING(1),

    /**
     * 下载中
     */
    LOADING(2),

    /**
     * 暂停
     */
    STOP(3),

    /**
     * 错误
     */
    ERROR(4),

    /**
     * 完成
     */
    FINISH(5);

    companion object {
        fun getActionType(v: Int): DownloadState? {
            val values = values()
            for (state in values) {
                if (state.value == v) {
                    return state
                }
            }
            return null
        }
    }
}