package com.example.objectdetection.helper;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.objectdetection.R;

public class AudioHelperActivity extends AppCompatActivity {

    protected TextView audio_output_textview;
    protected TextView audio_specs_textview;
    protected Button audio_btn_start;
    protected Button audio_btn_stop;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_helper);

        audio_output_textview = findViewById(R.id.audio_output_textview);
        audio_specs_textview = findViewById(R.id.audio_specs_textview);
        audio_btn_start = findViewById(R.id.audio_btn_start);
        audio_btn_stop = findViewById(R.id.audio_btn_stop);

        audio_btn_stop.setEnabled(false);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.RECORD_AUDIO)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, 0);
            }

        }
    }

    public  void startRecording(View view){
        audio_btn_start.setEnabled(false);
        audio_btn_stop.setEnabled(true);
    }
    public  void stopRecording(View view){
        audio_btn_start.setEnabled(true);
        audio_btn_stop.setEnabled(false);
    }
}