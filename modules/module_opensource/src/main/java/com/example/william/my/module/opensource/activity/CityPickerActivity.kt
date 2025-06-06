package com.example.william.my.module.opensource.activity

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.lib.utils.Utils
import com.zaaach.citypicker.CityPicker
import com.zaaach.citypicker.R
import com.zaaach.citypicker.adapter.OnPickListener
import com.zaaach.citypicker.model.City
import com.zaaach.citypicker.model.LocateState
import com.zaaach.citypicker.model.LocatedCity

/**
 * https://github.com/zaaach/CityPicker
 *
 * 添加 setTheme(R.style.DefaultCityPickerTheme)
 * 或 android:theme="@style/DefaultCityPickerTheme"
 */
@Route(path = RouterPath.Opensource.CityPicker)
class CityPickerActivity : BasicResponseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.DefaultCityPickerTheme)
        super.onCreate(savedInstanceState)
    }

    override fun onResponseClick(view: View) {
        super.onResponseClick(view)

        showCityPicker()
    }

    private fun showCityPicker() {
        CityPicker.from(this@CityPickerActivity)
            .enableAnimation(true) //启用动画
            //.setAnimationStyle(anim)//自定义动画
            .setLocatedCity(null) //定位城市
            //.setHotCities(hotCities)//指定热门城市
            .setOnPickListener(object : OnPickListener {
                override fun onPick(position: Int, data: City) {
                    showResponse(String.format("%s，%s", data.name, data.code))
                }

                override fun onCancel() {
                    Utils.show("取消选择")
                }

                override fun onLocate() {
                    //开始定位，这里模拟一下定位
                    runOnUiThread(Runnable { //定位完成之后更新数据
                        CityPicker.from(this@CityPickerActivity).locateComplete(
                            LocatedCity("深圳", "广东", "101280601"),
                            LocateState.SUCCESS
                        )
                    })
                }
            })
            .show()
    }
}