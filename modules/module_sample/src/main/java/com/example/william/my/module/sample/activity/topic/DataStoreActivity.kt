package com.example.william.my.module.sample.activity.topic

import android.view.View
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.module.sample.datastore.ExamplePreferenceDataStore
import com.example.william.my.module.sample.datastore.ExampleProtoDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * DataStore
 * https://developer.android.google.cn/topic/libraries/architecture/datastore
 * <p>
 * Preferences DataStore：不需要预先定义，但是不支持类型安全
 * Proto DataStore：需要预先使用protocol buffers定义数据，但是类型安全
 */
@Route(path = RouterPath.Sample.DataStore)
class DataStoreActivity : BasicResponseActivity() {

    private val preDataStore = ExamplePreferenceDataStore(this)

    private val protoDataStore = ExampleProtoDataStore(this)

    override fun initView() {
        super.initView()

        initCounter()
    }

    override fun onResponseClick(view: View) {
        super.onResponseClick(view)

        incrementCounter()
    }


    /**
     * 从 DataStore 读取内容
     */
    private fun initCounter() {
        lifecycleScope.launch(Dispatchers.Main) {
            preDataStore.getCounter()
                .collect {
                    showResponse("Count: $it")
                }
            protoDataStore.getCounter()
                .collect {
                    showResponse("Count: $it")
                }
        }
    }

    /**
     * 将内容写入 DataStore
     */
    private fun incrementCounter() {
        lifecycleScope.launch(Dispatchers.Main) {
            preDataStore.incrementCounter()
            protoDataStore.incrementCounter()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        runBlocking {
            preDataStore.clear()
            protoDataStore.clear()
        }
    }
}