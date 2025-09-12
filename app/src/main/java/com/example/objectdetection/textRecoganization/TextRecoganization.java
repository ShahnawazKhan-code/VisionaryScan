package com.example.objectdetection.textRecoganization;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.objectdetection.R;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.IOException;

public class TextRecoganization extends AppCompatActivity {
    Button camerabtn, copybtn, clearbtn;
    Uri imageUri;
    EditText recgText;
    TextRecognizer textRecognizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_recoganization);

        camerabtn = findViewById(R.id.camerabtn);
        copybtn = findViewById(R.id.copybtn);
        clearbtn = findViewById(R.id.clearbtn);
        recgText = findViewById(R.id.recgText);

        textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

        camerabtn.setOnClickListener(v -> ImagePicker.with(TextRecoganization.this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start());

        copybtn.setOnClickListener(v -> {
            String text = recgText.getText() != null ? recgText.getText().toString() : "";
            if (text.isEmpty()) {
                Toast.makeText(TextRecoganization.this, "Text is empty", Toast.LENGTH_SHORT).show();
            } else {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Data", text);
                if (clipboardManager != null) {
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(TextRecoganization.this, "Text copied to Clipboard", Toast.LENGTH_SHORT).show();
                }
            }
        });

        clearbtn.setOnClickListener(v -> {
            String text = recgText.getText() != null ? recgText.getText().toString() : "";
            if (text.isEmpty()) {
                Toast.makeText(TextRecoganization.this, "Text is empty", Toast.LENGTH_SHORT).show();
            } else {
                recgText.setText("");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.getData();
            if (imageUri != null) {
                Toast.makeText(this, "Image selected", Toast.LENGTH_SHORT).show();
                recoganizetionText();
            } else {
                Toast.makeText(this, "Image URI is null", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Image not selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void recoganizetionText() {
        if (imageUri != null) {
            try {
                InputImage inputImage = InputImage.fromFilePath(this, imageUri);

                textRecognizer.process(inputImage)
                        .addOnSuccessListener(new OnSuccessListener<Text>() {
                            @Override
                            public void onSuccess(Text text) {
                                if (text != null) {
                                    String recognizeText = text.getText();
                                    if (recognizeText != null && !recognizeText.isEmpty()) {
                                        recgText.setText(recognizeText);
                                    } else {
                                        recgText.setText("");
                                        Toast.makeText(TextRecoganization.this, "No text detected", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    recgText.setText("");
                                    Toast.makeText(TextRecoganization.this, "No result from recognizer", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .addOnFailureListener(e -> Toast.makeText(TextRecoganization.this,
                                e.getMessage() != null ? e.getMessage() : "Text recognition failed",
                                Toast.LENGTH_SHORT).show());

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Image URI is null", Toast.LENGTH_SHORT).show();
        }
    }
}
