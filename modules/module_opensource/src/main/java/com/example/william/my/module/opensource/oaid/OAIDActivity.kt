package com.example.william.my.module.opensource.oaid

import com.alibaba.android.arouter.facade.annotation.Route
import com.bun.miitmdid.core.MdidSdkHelper
import com.example.william.my.basic.basic_module.router.path.ARouterPath
import com.example.william.my.library.activity.BaseVBActivity
import com.example.william.my.module.opensource.databinding.OpenActivityOaidBinding
import com.example.william.my.module.opensource.oaid.DemoHelper.AppIdsUpdater
import com.example.william.my.module.opensource.oaid.utils.CertUtil
import com.example.william.my.module.opensource.oaid.utils.SystemInfoUtil

@Route(path = ARouterPath.Opensource.OAID)
class OAIDActivity : BaseVBActivity<OpenActivityOaidBinding?>(), AppIdsUpdater {

    override fun getViewBinding(): OpenActivityOaidBinding {
        return OpenActivityOaidBinding.inflate(layoutInflater)
    }

    private lateinit var mDemoHelper: DemoHelper

    override fun initView() {
        super.initView()

        initOAID()
    }

    private fun initOAID() {
        mBinding.tvSdk.text = sdkVersionInfo
        mBinding.tvSys.text = sysInfo

        mBinding.tvCert.text = CertUtil.getCertInfo(
            DemoHelper.loadPemFromAssetFile(
                this,
                DemoHelper.ASSET_FILE_NAME_CERT
            )
        )

        mDemoHelper = DemoHelper(this)
        mDemoHelper.getDeviceIds(this)
    }

    private val sdkVersionInfo: String
        get() = String.format(
            "OAID SDK Test\nVersion: 2.1.0 (%d)",
            MdidSdkHelper.SDK_VERSION_CODE
        )

    private val sysInfo: String
        get() = String.format(
            "Time: %s\nBrand: %s\nManufacturer: %s\nModel: %s\nAndroidVersion: %s",
            SystemInfoUtil.getSystemTime(),
            SystemInfoUtil.getDeviceBrand(),
            SystemInfoUtil.getDeviceManufacturer(),
            SystemInfoUtil.getSystemModel(),
            SystemInfoUtil.getSystemVersion()
        )

    override fun onIdsValid(ids: String) {
        runOnUiThread {
            mBinding.tvInfo.text = "OAID: \n$ids"
        }
    }
}