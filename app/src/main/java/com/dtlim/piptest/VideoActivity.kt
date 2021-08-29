package com.dtlim.piptest

import android.app.PictureInPictureParams
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.dtlim.piptest.databinding.ActivityVideoBinding
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.util.Util

class VideoActivity : AppCompatActivity() {

    companion object {
        const val URL_CLEAR = "https://storage.googleapis.com/wvmedia/clear/h264/tears/tears.mpd"
        const val URL_SECURE_CENC =
            "https://storage.googleapis.com/wvmedia/cenc/h264/tears/tears.mpd"
    }

    lateinit var binding: ActivityVideoBinding
    private var player: SimpleExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            initializePlayer()
            binding.playerView.onResume()
        }
    }

    override fun onResume() {
        super.onResume()
        if (player == null) {
            initializePlayer()
            binding.playerView.onResume()
        }
        if (Util.SDK_INT <= 23) {
            binding.playerView.onResume()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            binding.playerView.onPause()
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            binding.playerView.onPause()
            releasePlayer()
        }
    }

    private fun initializePlayer(): Boolean {
        val mediaItem = MediaItem.Builder()
            .setUri(URL_SECURE_CENC)
            .setDrmUuid(C.WIDEVINE_UUID)
            .setDrmLicenseUri("https://proxy.uat.widevine.com/proxy?provider=widevine_test")
            .build()
        if (player == null) {
            player = SimpleExoPlayer.Builder(this)
                .build()
            binding.playerView.player = player
        }
        player?.setMediaItem(mediaItem)
        player?.prepare()
        player?.play()
        return true
    }

    private fun releasePlayer() {
        if (player != null) {
            player?.release()
            player = null
        }
    }

    override fun onUserLeaveHint() {
        enterPictureInPictureMode(PictureInPictureParams.Builder().build())
    }
}