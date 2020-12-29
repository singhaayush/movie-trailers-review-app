package com.example.trial1tmdb

import android.content.Intent
import android.os.Bundle
import android.transition.Fade
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class VideoPlayActivity : AppCompatActivity() {
    lateinit var customPlayerUi: View
    lateinit var ytPlayer: YouTubePlayer
    var youTubePlayerView: YouTubePlayerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_play)
        val fade = Fade()
        val decor = window.decorView
        fade.excludeTarget(decor.findViewById<View>(R.id.action_bar_container), true)
        fade.excludeTarget(android.R.id.statusBarBackground, true)
        fade.excludeTarget(android.R.id.navigationBarBackground, true)
        window.enterTransition = fade
        window.exitTransition = fade
        youTubePlayerView = findViewById(R.id.youtube_player_view)
        val youTubePlayerView =
                findViewById<YouTubePlayerView>(R.id.youtube_player_view)
//        val iFramePlayerOptions =
//            IFramePlayerOptions.Builder()
//                .controls(0)
//                .rel(1)
//                .ivLoadPolicy(3)
//                .ccLoadPolicy(1)
//                .build()
        lifecycle.addObserver(youTubePlayerView)
        customPlayerUi =
                youTubePlayerView.inflateCustomPlayerUi(R.layout.custom_player_ui)
        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                ytPlayer = youTubePlayer
                val customPlayerUiController = CustomPlayerUiController(
                        this@VideoPlayActivity,
                        customPlayerUi,
                        youTubePlayer,
                        youTubePlayerView
                )
                youTubePlayer.addListener(customPlayerUiController)
                youTubePlayerView.addFullScreenListener(customPlayerUiController)
                youTubePlayer.loadOrCueVideo(lifecycle, intent.getStringExtra("key"), 0f)
            }
        })
    }


}