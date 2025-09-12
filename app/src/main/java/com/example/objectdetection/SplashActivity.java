package com.example.objectdetection;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_SCREEN_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        // Use a lambda to avoid anonymous inner class issues with D8
        new Handler().postDelayed(() -> {
            // Null-safe Intent creation
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            if (intent != null) {
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }
}
