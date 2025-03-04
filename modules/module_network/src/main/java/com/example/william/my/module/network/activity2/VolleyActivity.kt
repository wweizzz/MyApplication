package com.example.william.my.module.network.activity2

import com.alibaba.android.arouter.facade.annotation.Route
import com.android.volley.Cache
import com.android.volley.Network
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.example.william.my.basic.basic_module.activity.BasicRecyclerActivity
import com.example.william.my.basic.basic_module.base.Constants
import com.example.william.my.basic.basic_module.router.path.RouterPath
import org.json.JSONObject


/**
 * https://github.com/google/volley
 * https://developer.android.google.cn/training/volley
 */
@Route(path = RouterPath.Network.Volley)
class VolleyActivity : BasicRecyclerActivity() {

    private var stringRequest: StringRequest? = null // Assume this exists.
    private var jsonObjectRequest: JsonObjectRequest? = null // Assume this exists.

    private var requestQueue: RequestQueue? = null // Assume this exists.

    override fun buildList(): ArrayList<String> {
        return arrayListOf(
            "Volley postForm",
            "Volley PostJson",
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
                postForm(Constants.Value_Username, Constants.Value_Password)
            }

            1 -> {
                postJsonObject(Constants.Value_Username, Constants.Value_Password)
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

    private fun postForm(username: String, password: String) {
        val params = mapOf(
            Constants.Key_Username to username,
            Constants.Key_Password to password
        )

        // Request a string response from the provided URL.
        stringRequest =
            object : StringRequest(Method.POST, Constants.Url_Login,
                Response.Listener { response ->
                    showResponse(response.toString())
                },
                Response.ErrorListener {
                    showFailure(it.message)
                }
            ) {
                override fun getParams(): Map<String, String> {
                    return params
                }
            }

        // Set the tag on the request.
        stringRequest?.tag = tag

        // Add the request to the RequestQueue.
        requestQueue?.add(stringRequest)
    }

    private fun postJsonObject(username: String, password: String) {
        val jsonObject = JSONObject()
            .put(Constants.Key_Username, username)
            .put(Constants.Key_Password, password)

        // Request a string response from the provided URL.
        jsonObjectRequest =
            object : JsonObjectRequest(Method.POST, Constants.Url_Login, jsonObject,
                Response.Listener { response ->
                    // Display the first 500 characters of the response string.
                    showResponse(response.toString())
                },
                Response.ErrorListener {
                    showFailure(it.message)
                }
            ) {

            }

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