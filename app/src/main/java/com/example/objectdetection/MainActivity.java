package com.example.objectdetection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.objectdetection.helper.ImageHelperActivity;
import com.example.objectdetection.image.ImageClassificationActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onGotoImageActivity(View view){
        Intent intent = new Intent(this, ImageClassificationActivity.class);
        startActivity(intent);
    }


}