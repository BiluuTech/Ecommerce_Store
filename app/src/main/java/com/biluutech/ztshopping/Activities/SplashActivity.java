package com.biluutech.ztshopping.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.biluutech.ztshopping.R;

public class SplashActivity extends AppCompatActivity {

    private ImageView ztLogoIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ztLogoIV = findViewById(R.id.zt_logo_IV);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.blur);

        ztLogoIV.startAnimation(animation);

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }, 4000);

    }
}