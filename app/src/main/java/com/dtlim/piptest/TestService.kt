package com.dtlim.piptest

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class TestService : Service() {
    override fun onBind(intent: Intent?): IBinder {
        return TestServiceBinder()
    }

    inner class TestServiceBinder : Binder() {
        fun getService() = this@TestService
    }
}
