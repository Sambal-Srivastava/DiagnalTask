package com.diagnal.diagnaltask

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DiagnalApplication:Application(){
    companion object {
        private lateinit var instance: DiagnalApplication

        fun getInstance(): DiagnalApplication {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}