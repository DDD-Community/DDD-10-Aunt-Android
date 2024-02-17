package com.aunt.opeace

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class OPeaceApplication : Application() {


    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "e55ab48902aa38be8a6fb0f1a3431e8d")
    }
}
