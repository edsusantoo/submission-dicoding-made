package com.edsusantoo.movied.videoplayer

import android.os.Bundle
import android.widget.Toast
import com.edsusantoo.movied.videoplayer.databinding.ActivityYoutubePlayerBinding
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer

class YoutubePlayerActivity : YouTubeBaseActivity() {
    private lateinit var binding: ActivityYoutubePlayerBinding
    private val api_key = "AIzaSyBYmxZzmdjWgsq2LzdTCvdMPUNJZ5hrodM"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYoutubePlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initPlayer()
    }

    private fun initPlayer() {

        val intent = intent
        val data = intent.data
        val key = data?.getQueryParameter("video")
        binding.ytPlayer.initialize(api_key, object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                provider: YouTubePlayer.Provider?,
                player: YouTubePlayer?,
                p2: Boolean
            ) {
                player?.loadVideo(key)
                player?.play()
            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                Toast.makeText(
                    this@YoutubePlayerActivity,
                    "Video player failed",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })

    }

}