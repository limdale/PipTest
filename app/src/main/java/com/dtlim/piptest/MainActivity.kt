package com.dtlim.piptest

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import com.dtlim.piptest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ServiceConnection {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonVideo.setOnClickListener {
            startActivity(Intent(this, VideoActivity::class.java))
        }
        Intent(this, TestService::class.java).also { intent ->
            startService(intent)
            bindService(intent, this, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        // do nothing
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        // do nothing
    }
}