package com.example.trial1tmdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class SplashScreen extends AppCompatActivity {
    Handler handler;
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        iv=findViewById(R.id.splash_logo);
        YoYo.with(Techniques.RubberBand)
                .repeat(1)
                .duration(2500)
                .playOn(iv);
        handler =new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i1=new Intent(SplashScreen.this,MainActivity.class);
                startActivity(i1);
                finish();
            }
        },2500);
    }
}
