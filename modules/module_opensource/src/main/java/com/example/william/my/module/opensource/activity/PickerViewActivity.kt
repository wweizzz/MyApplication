package com.example.william.my.module.opensource.activity

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout.LayoutParams
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.view.OptionsPickerView
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.basic.basic_module.router.service.ResourceUtilsService
import com.example.william.my.basic.basic_module.utils.Utils
import com.example.william.my.module.opensource.R
import com.example.william.my.module.opensource.data.ProvinceData
import com.google.gson.Gson
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * plist转json
 * http://json2plist.sinaapp.com/
 * 格式化
 * http://www.bejson.com/
 *
 *
 * https://github.com/Bigkoo/Android-PickerView
 */
@Route(path = RouterPath.Opensource.PickerView)
class PickerViewActivity : BasicResponseActivity() {

    private var isLoaded = false
    private var isSwitch = false
    private val options1Items: MutableList<ProvinceData> = arrayListOf() //所有省份数组
    private val options2Items: MutableList<List<String>> = arrayListOf() //所有城市数组
    private val options3Items: MutableList<List<List<String>>> = arrayListOf() //所有地区数组

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        initPickView()
    }

    private fun initPickView() {
        Thread {
            // 子线程中解析省市区数据
            initJsonData()
        }.start()
    }

    override fun onResponseClick(view: View) {
        super.onResponseClick(view)

        showPickView()
    }

    private fun showPickView() {
        if (isSwitch) {
            showTimePickerView()
        } else {
            if (isLoaded) {
                showOptionsPickerView()
            } else {
                Utils.toast("Please waiting until the data is parsed")
            }
        }
        isSwitch = !isSwitch
    }

    private fun initJsonData() { //解析数据

        /*
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         */
        val service = ARouter.getInstance()
            .build(RouterPath.Service.ResourceUtilsService)
            .navigation() as ResourceUtilsService
        val jsonData = service.getAssets("province.json") //获取assets目录下的json文件数据

        val provinceData = parseData(jsonData)

        /*
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items.addAll(provinceData)

        for (i in provinceData.indices) { //遍历省份
            val cityList: MutableList<String> = arrayListOf() //该省的城市列表（第二级）
            val areaList: MutableList<List<String>> = arrayListOf() //该省的所有地区列表（第三极）

            for (j in provinceData[i].cityList.indices) { //遍历该省份的所有城市
                val cityAreaList: MutableList<String> = arrayListOf() //该城市的所有地区列表（第三极）

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                provinceData[i].cityList[j].areaList?.let { area ->
                    if (area.isEmpty()) {
                        cityAreaList.add("")
                    } else {
                        for (n in area.indices) {
                            cityAreaList.add(area[n].name)
                        }
                    }
                }

                cityList.add(provinceData[i].cityList[j].name) //添加该省份的所有城市
                areaList.add(cityAreaList) //添加该省份的所有地区
            }

            /*
             * 添加城市数据
             */
            options2Items.add(cityList)

            /*
             * 添加地区数据
             */
            options3Items.add(areaList)
        }
        isLoaded = true
    }

    private fun parseData(result: String): ArrayList<ProvinceData> {
        val provinceData = arrayListOf<ProvinceData>()
        try {
            val data = JSONArray(result)
            val gson = Gson()
            for (i in 0 until data.length()) {
                val entity =
                    gson.fromJson(data.optJSONObject(i).toString(), ProvinceData::class.java)
                provinceData.add(entity)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return provinceData
    }

    private fun showOptionsPickerView() {
        val pvOptions: OptionsPickerView<*> =
            OptionsPickerBuilder(this) { options1, options2, options3, v -> //返回的分别是三个级别的选中位置
                val options = options1Items[options1].pickerViewText +
                        options2Items[options1][options2] +
                        options3Items[options1][options2][options3]
                showResponse(options)
            }
                .setDecorView(window.decorView.findViewById(R.id.content)) //防止被虚拟按键遮挡
                .setSubmitText("确定") //确定按钮文字
                .setCancelText("取消") //取消按钮文字
                .setTitleText("城市选择") //标题
                .setSubmitColor(Color.BLUE) //确定按钮文字颜色
                .setCancelColor(Color.BLUE) //取消按钮文字颜色
                .setTitleColor(Color.BLACK) //标题文字颜色
                .setBgColor(-0x1) //滚轮背景颜色 Night mode
                .setTitleBgColor(-0x1) //标题背景颜色 Night mode
                .setSubCalSize(18) //确定和取消文字大小
                .setTitleSize(20) //标题文字大小
                .setContentTextSize(18) //滚轮文字大小
                .setOutSideCancelable(true) //点击外部dismiss default true
                .build<Any>()

        @Suppress("UNCHECKED_CAST")
        pvOptions.setPicker(
            options1Items as List<Nothing>?,
            options2Items as List<Nothing>?,
            options3Items as List<Nothing>?
        ) //三级选择器
        pvOptions.show()
    }

    private fun showTimePickerView() {
        val pvTime = TimePickerBuilder(this) { date, v ->
            showResponse(
                SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss",
                    Locale.CHINA
                ).format(date)
            )
        }
            .setType(booleanArrayOf(true, true, true, false, false, false))
            .setCancelText("Cancel") //取消按钮文字
            .setSubmitText("Sure") //确认按钮文字
            .setTitleText("Title") //标题文字
            .setSubmitColor(Color.BLUE) //确定按钮文字颜色
            .setCancelColor(Color.BLUE) //取消按钮文字颜色
            .setTitleColor(Color.BLACK) //标题文字颜色
            .setBgColor(-0x1) //滚轮背景颜色 Night mode
            .setTitleBgColor(-0x1) //标题背景颜色 Night mode
            .setSubCalSize(18) //确定和取消文字大小
            .setTitleSize(20) //标题文字大小
            .setContentTextSize(18) //滚轮文字大小
            .setOutSideCancelable(true) //点击屏幕，点在控件外部范围时，是否取消显示
            .isCyclic(true) //是否循环滚动
            .setLunarCalendar(true) //是否显示农历
            .setLabel("年", "月", "日", "时", "分", "秒") //默认设置为年月日时分秒
            .isDialog(true) //是否显示为对话框样式
            .build()
        //dialog底部显示
        pvTime.dialog?.let {
            val layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            layoutParams.gravity = Gravity.BOTTOM
            layoutParams.leftMargin = 0
            layoutParams.rightMargin = 0
            pvTime.dialogContainerLayout.layoutParams = layoutParams
            it.window?.let {
                it.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim) //修改动画样式
                it.setGravity(Gravity.BOTTOM) //改成Bottom,底部显示
            }
            pvTime.show()
        }
    }
}