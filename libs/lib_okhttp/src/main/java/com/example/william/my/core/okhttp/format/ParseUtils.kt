package com.example.william.my.core.okhttp.format

import android.text.TextUtils
import okhttp3.Headers
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import okio.GzipSource
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.EOFException
import java.io.IOException
import java.io.StringReader
import java.io.StringWriter
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.util.Locale
import javax.xml.transform.OutputKeys
import javax.xml.transform.Source
import javax.xml.transform.TransformerException
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource

object ParseUtils {

    /**
     * 是否可以解析
     * text/plain
     * text/xml
     * text/html
     * application/json
     * application/x-www-form-urlencoded
     */
    fun isParseAble(mediaType: MediaType?): Boolean {
        return if (mediaType == null) {
            false
        } else (isText(mediaType) || isPlain(mediaType)
                || isXml(mediaType) || isHtml(mediaType)
                || isJson(mediaType) || isForm(mediaType))
    }

    private fun isText(mediaType: MediaType?): Boolean {
        return if (mediaType == null) {
            false
        } else "text" == mediaType.type
    }

    fun isPlain(mediaType: MediaType?): Boolean {
        return mediaType?.subtype?.lowercase(Locale.getDefault())?.contains("plain") ?: false
    }

    fun isXml(mediaType: MediaType?): Boolean {
        return mediaType?.subtype?.lowercase(Locale.getDefault())?.contains("xml") ?: false
    }

    fun isHtml(mediaType: MediaType?): Boolean {
        return mediaType?.subtype?.lowercase(Locale.getDefault())?.contains("html") ?: false
    }

    fun isJson(mediaType: MediaType?): Boolean {
        return mediaType?.subtype?.lowercase(Locale.getDefault())?.contains("json") ?: false
    }

    fun isForm(mediaType: MediaType?): Boolean {
        return mediaType?.subtype?.lowercase(Locale.getDefault())?.contains("x-www-form-urlencoded")
            ?: false
    }

    fun parseRequest(request: Request): String {
        return try {
            val requestBody = request.body!!
            val buffer = Buffer()
            requestBody.writeTo(buffer)
            val contentType = requestBody.contentType()
            val charset: Charset =
                contentType?.charset(StandardCharsets.UTF_8) ?: StandardCharsets.UTF_8
            buffer.readString(charset)
        } catch (e: IOException) {
            e.printStackTrace();
            "{\"error\": \"" + e.message + "\"}";
        }
    }


    fun parseResponse(response: Response): String {
        return try {
            val responseBody = response.body!!
            val headers = response.headers
            val source = responseBody.source()
            source.request(Long.MAX_VALUE) // Buffer the entire body.
            var buffer = source.buffer
            if ("gzip".equals(headers["Content-Encoding"], ignoreCase = true)) {
                GzipSource(buffer.clone()).use { gzippedResponseBody ->
                    buffer = Buffer()
                    buffer.writeAll(gzippedResponseBody)
                }
            }
            val contentType = responseBody.contentType()
            val charset: Charset =
                contentType?.charset(StandardCharsets.UTF_8) ?: StandardCharsets.UTF_8
            return buffer.clone().readString(charset)
        } catch (e: IOException) {
            e.printStackTrace()
            "{\"error\": \"" + e.message + "\"}"
        }
    }

    /**
     * json 格式化
     *
     * @param json
     * @return
     */
    fun jsonFormat(json: String): String {
        if (TextUtils.isEmpty(json)) {
            return "Empty/Null json content"
        }
        val message: String = try {
            if (json.startsWith("{")) {
                val jsonObject = JSONObject(json)
                jsonObject.toString(4)
            } else if (json.startsWith("[")) {
                val jsonArray = JSONArray(json)
                jsonArray.toString(4)
            } else {
                json
            }
        } catch (e: JSONException) {
            json
        } catch (error: OutOfMemoryError) {
            "Output omitted because of Object size"
        }
        return message
    }

    /**
     * xml 格式化
     *
     * @param xml
     * @return
     */
    fun xmlFormat(xml: String): String {
        if (TextUtils.isEmpty(xml)) {
            return "Empty/Null xml content"
        }
        val message: String = try {
            val xmlInput: Source = StreamSource(StringReader(xml))
            val xmlOutput = StreamResult(StringWriter())
            val transformer = TransformerFactory.newInstance().newTransformer()
            transformer.setOutputProperty(OutputKeys.INDENT, "yes")
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2")
            transformer.transform(xmlInput, xmlOutput)
            xmlOutput.writer.toString().replaceFirst(">".toRegex(), ">\n")
        } catch (e: TransformerException) {
            xml
        }
        return message
    }

    private fun bodyHasUnknownEncoding(headers: Headers): Boolean {
        val contentEncoding = headers["Content-Encoding"] ?: return false
        return !contentEncoding.equals(
            "identity",
            ignoreCase = true
        ) && !contentEncoding.equals("gzip", ignoreCase = true)
    }

    private fun Buffer.isProbablyUtf8(): Boolean {
        try {
            val prefix = Buffer()
            val byteCount = size.coerceAtMost(64)
            copyTo(prefix, 0, byteCount)
            for (i in 0 until 16) {
                if (prefix.exhausted()) {
                    break
                }
                val codePoint = prefix.readUtf8CodePoint()
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false
                }
            }
            return true
        } catch (_: EOFException) {
            return false // Truncated UTF-8 sequence.
        }
    }
}