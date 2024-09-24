package com.example.william.my.core.imageloader.glide.module;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

@GlideModule
public final class MyAppGlideModule extends AppGlideModule {

    //可以配置Glide
    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        //builder.setDefaultRequestOptions(
        //        new RequestOptions()
        //                .diskCacheStrategy(DiskCacheStrategy.NONE)
        //                .skipMemoryCache(true)
        //);
    }
}