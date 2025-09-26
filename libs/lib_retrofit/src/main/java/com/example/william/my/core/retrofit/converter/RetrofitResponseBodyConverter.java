package com.example.william.my.core.retrofit.converter;

import com.example.william.my.core.retrofit.response.RetrofitResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 符合RetrofitResponse格式的ResponseBody
 * <p>
 *
 * @see retrofit2.converter.gson.GsonResponseBodyConverter
 */
@SuppressWarnings("ALL")
final class RetrofitResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final String TAG = getClass().getSimpleName();

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    private final String code, msg;

    RetrofitResponseBodyConverter(Gson gson, TypeAdapter<T> adapter, String code, String msg) {
        this.gson = gson;
        this.adapter = adapter;
        this.code = code;
        this.msg = msg;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String result = value.string();
        try {
            JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
            if (jsonObject.has(code)) {
                //如果符合RetrofitResponse格式则直接返回当前数据
                return adapter.fromJson(result);
            } else {
                // 将当前数据包装成如果符合RetrofitResponse返回
                return adapter.fromJson(gson.toJson(RetrofitResponse.success(jsonObject)));
            }
        } finally {
            value.close();
        }
    }
}