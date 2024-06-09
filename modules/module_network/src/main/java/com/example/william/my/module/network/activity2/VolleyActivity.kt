package com.example.william.my.module.network.activity2

import com.alibaba.android.arouter.facade.annotation.Route
import com.android.volley.Cache
import com.android.volley.Network
import com.android.volley.Request.Method
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.JsonObjectRequest
import com.example.william.my.basic.basic_module.activity.BasicRecyclerActivity
import com.example.william.my.basic.basic_module.base.Constants
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.core.volley.StringRequest2
import org.json.JSONObject

/**
 * https://github.com/google/volley
 * https://developer.android.google.cn/training/volley
 */
@Route(path = RouterPath.Network.Volley)
class VolleyActivity : BasicRecyclerActivity() {

    private var stringRequest: StringRequest2? = null // Assume this exists.
    private var jsonObjectRequest: JsonObjectRequest? = null // Assume this exists.

    private var requestQueue: RequestQueue? = null // Assume this exists.

    override fun buildList(): ArrayList<String> {
        return arrayListOf(
            "Volley PostString",
            "Volley PostJsonObject1",
            "Volley PostJsonObject2",
        )
    }

    override fun initView() {
        super.initView()

        initRequest()
    }

    override fun onRecyclerClick(position: Int, string: String) {
        super.onRecyclerClick(position, string)
        when (position) {
            0 -> {
                postString()
            }

            1 -> {
                postJsonObject1()
            }

            2 -> {
                postJsonObject2()
            }
        }
    }

    private fun initRequest() {
        // Instantiate the cache
        val cache: Cache = DiskBasedCache(cacheDir, 1024 * 1024) // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        val network: Network = BasicNetwork(HurlStack())

        // Instantiate the RequestQueue.
        // requestQueue = Volley.newRequestQueue(this);

        // Instantiate the RequestQueue with the cache and network.
        requestQueue = RequestQueue(cache, network)

        // Start the queue
        requestQueue?.start()
    }

    private fun postString() {
        // Request a string response from the provided URL.
        stringRequest =
            StringRequest2(Method.POST, Constants.Url_Login, Constants.LoginString, { response ->
                // Display the first 500 characters of the response string.
                showResponse(response.toString())
            }, {
                showFailure(it.message)
            })

        // Set the tag on the request.
        stringRequest?.tag = tag

        // Add the request to the RequestQueue.
        requestQueue?.add(stringRequest)
    }

    private fun postJsonObject1() {
        val jsonObject = JSONObject(Constants.LoginJsonString)

        // Request a string response from the provided URL.
        jsonObjectRequest =
            JsonObjectRequest(Method.POST, Constants.Url_Login, jsonObject, { response ->
                // Display the first 500 characters of the response string.
                showResponse(response.toString())
            }, {
                showFailure(it.message)
            })

        // Set the tag on the request.
        jsonObjectRequest?.tag = tag

        // Add the request to the RequestQueue.
        requestQueue?.add(jsonObjectRequest)
    }

    private fun postJsonObject2() {
        val jsonObject = JSONObject()
            .put(Constants.Key_Username, Constants.Value_Username)
            .put(Constants.Key_Password, Constants.Value_Password)

        // Request a string response from the provided URL.
        jsonObjectRequest =
            JsonObjectRequest(Method.POST, Constants.Url_Login, jsonObject, { response ->
                // Display the first 500 characters of the response string.
                showResponse(response.toString())
            }, {
                showFailure(it.message)
            })

        // Set the tag on the request.
        jsonObjectRequest?.tag = tag

        // Add the request to the RequestQueue.
        requestQueue?.add(jsonObjectRequest)
    }

    override fun onStop() {
        super.onStop()
        requestQueue?.cancelAll(tag)
    }
}