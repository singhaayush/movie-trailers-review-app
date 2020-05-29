package com.example.trial1tmdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class VideoPlayActivity extends AppCompatActivity {
     YouTubePlayerView youTubePlayerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        Fade fade=new Fade();
        View decor=getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.action_bar_container),true);
        fade.excludeTarget(android.R.id.statusBarBackground,true);
        fade.excludeTarget(android.R.id.navigationBarBackground,true);
        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);
        youTubePlayerView =findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);


    }

    @Override
    protected void onResume() {
        super.onResume();
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                super.onReady(youTubePlayer);

                try {
                    YouTubePlayerUtils.loadOrCueVideo(
                            youTubePlayer,
                            getLifecycle(),
                            getIntent().getStringExtra("key"),
                            0.0f
                    );
                }
                catch (Exception e)
                {
                    Intent intent=new Intent(VideoPlayActivity.this,SingleMovieDetail.class);
                    startActivity(intent);
                    Toast.makeText(VideoPlayActivity.this,"Something went wrong try again", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

});
}
}