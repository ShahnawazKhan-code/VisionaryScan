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

    protected TextView outputTextView;
    protected TextView specsTextView;
    protected Button startRecordingbtn;
    protected Button stopRecordingbtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_helper);

        outputTextView = findViewById(R.id.audio_output_textview);
        specsTextView = findViewById(R.id.audio_specs_textview);
        startRecordingbtn = findViewById(R.id.audio_btn_start);
        stopRecordingbtn = findViewById(R.id.audio_btn_stop);

        stopRecordingbtn.setEnabled(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.RECORD_AUDIO)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, 0);
            }

        }
    }

    public  void startRecording(View view){
        startRecordingbtn.setEnabled(false);
        stopRecordingbtn.setEnabled(true);
    }
    public  void stopRecording(View view){
        startRecordingbtn.setEnabled(true);
        stopRecordingbtn.setEnabled(false);
    }
}