package com.example.warnermediatest

import android.app.Application
import android.content.Context

class WarnerApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {
        private var context: Context? = null
        fun getContext(): Context? = context
    }

}