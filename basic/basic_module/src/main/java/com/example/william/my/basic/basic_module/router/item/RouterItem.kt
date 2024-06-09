package com.example.william.my.basic.basic_module.router.item

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RouterItem(val mRouterName: String?, val mRouterPath: String?) : Parcelable