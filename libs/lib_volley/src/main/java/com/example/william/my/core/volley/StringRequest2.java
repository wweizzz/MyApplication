package com.example.william.my.core.volley;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;

public class StringRequest2 extends StringRequest {

    protected static final String PROTOCOL_CHARSET = "utf-8";

    private final String mRequestString;

    public StringRequest2(int method, String url, String requestBody, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        mRequestString = requestBody;
    }

    @Override
    public byte[] getBody() {
        try {
            return mRequestString == null ? null : mRequestString.getBytes(PROTOCOL_CHARSET);
        } catch (UnsupportedEncodingException uee) {
            VolleyLog.wtf(
                    "Unsupported Encoding while trying to get the bytes of %s using %s",
                    mRequestString, PROTOCOL_CHARSET);
            return null;
        }
    }
}
