package com.ahhwxx.samplempu

import android.app.Application
import com.crearo.libs.CRAudioEncoder
import com.crearo.sdk.base.GlobalHelper
import com.crearo.sdk.base.SdkGlobal
import com.crearo.sdk.base.SdkSharedPreferencesHelper
import com.crearo.sdk.mpuclient.MPUEntity
import com.crearo.sdk.net.utils.Constant
import com.jiagu.sdk.cxsdkProtected


/**
 * @ClassName: App
 * User: xiaxiaoge
 * Date: 2020/12/23
 * @Description:
 */
class App:Application(){
    private val TAG = "MyApplication"
    var g_mpuEntity: MPUEntity? = null
    private var mGlobalHelper: GlobalHelper? = null
    var isEncryptVideo = false

    override fun onCreate() {
        super.onCreate()
        initMPUSDK()
    }

    private fun initMPUSDK() {
        cxsdkProtected.install(this);
        SdkGlobal.setContext(this);
        SdkSharedPreferencesHelper.setContext(this);
        SdkSharedPreferencesHelper.setStringValue("com.ahhwxx.mpuvideo.CrSdkConfig", Constant.SP_SdkConfigClassName);
        if(isEncryptVideo)
        {
            SdkSharedPreferencesHelper.setStringValue("com.crearo.sdk.filemanager.CrStreamWriter", Constant.SP_StreamWriterClassName);
            SdkSharedPreferencesHelper.setIntegerValue(CRAudioEncoder.ALG_ID_AAC8K, Constant.SP_AudioEncAlgId);
        }
        else
        {
            SdkSharedPreferencesHelper.setStringValue("com.crearo.sdk.filemanager.CrMediaMuxer", Constant.SP_StreamWriterClassName);
            SdkSharedPreferencesHelper.setIntegerValue(CRAudioEncoder.ALG_ID_AAC8K, Constant.SP_AudioEncAlgId);
        }

        mGlobalHelper = GlobalHelper.getInstance(getApplicationContext());
        mGlobalHelper?.onGlobalInit();
        g_mpuEntity = MPUEntity(this);
    }

    override fun onTerminate() {
        super.onTerminate()
        mGlobalHelper?.onGlobalClose();
    }
}