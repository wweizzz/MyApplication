package com.example.william.my.core.volley.request

import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.HttpHeaderParser
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

/**
 * Make a GET request and return a parsed object from JSON.
 *
 * @param url URL of the request to make
 * @param clazz Relevant class object, for Gson's reflection
 * @param headers Map of request headers
 * @param params Map of request parameters
 */
class JsonRequest<T>(
    method: Int,
    url: String,
    private val clazz: Class<T>,
    private val headers: MutableMap<String, String>?,
    private val jsonObject: JSONObject?,
    private val listener: Response.Listener<T>,
    errorListener: Response.ErrorListener
) : Request<T>(method, url, errorListener) {
    private val gson = Gson()

    override fun getHeaders(): MutableMap<String, String> = headers ?: super.getHeaders()

    override fun getBody(): ByteArray? {
        try {
            return jsonObject.toString().toByteArray(charset("utf-8"))
        } catch (uee: UnsupportedEncodingException) {
            VolleyLog.wtf(
                "Unsupported Encoding while trying to get the bytes of %s using %s",
                jsonObject.toString(), "utf-8"
            )
            return null
        }
    }

    override fun deliverResponse(response: T) = listener.onResponse(response)

    override fun parseNetworkResponse(response: NetworkResponse?): Response<T> {
        return try {
            val json = String(
                response?.data ?: ByteArray(0),
                Charset.forName(HttpHeaderParser.parseCharset(response?.headers))
            )
            Response.success(
                gson.fromJson(json, clazz),
                HttpHeaderParser.parseCacheHeaders(response)
            )
        } catch (e: UnsupportedEncodingException) {
            Response.error(ParseError(e))
        } catch (e: JsonSyntaxException) {
            Response.error(ParseError(e))
        }
    }
}