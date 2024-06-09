package com.example.william.my.module.arch.mavericks.article

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.Uninitialized
import com.example.william.my.basic.basic_repo.bean.ArticleData
import com.example.william.my.core.retrofit.response.RetrofitResponse

/**
 * 必须是data class。因为data class默认实现了equals、hashCode等函数，方便框架计算两次更新是否是同一数据，如果两次State是完全一样的，也没有必要通知UI刷新一次。
 * 属性必须是val，且必须有初始值。一个瞬时就只有一份不可变的数据。不会出现不可预期的情况，不会有完全隐患。Flutter、Compose等都有这样的设计理念。
 * 字段类型必须是不可变。如ArrayList等导致可变更的都不行。
 * 必须实现MavericksState接口。MavericksState只是一个标识接口。
 */
data class ArticleMavericksState(
    val articleResponse: Async<RetrofitResponse<ArticleData>> = Uninitialized,
) : MavericksState
