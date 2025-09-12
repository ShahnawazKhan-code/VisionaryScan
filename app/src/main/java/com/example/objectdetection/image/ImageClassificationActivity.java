package com.example.objectdetection.image;

import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.example.objectdetection.helper.ImageHelperActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;
import java.util.List;

public class ImageClassificationActivity extends ImageHelperActivity {

    private ImageLabeler imageLabeler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imageLabeler = ImageLabeling.getClient(new ImageLabelerOptions.Builder()
                .setConfidenceThreshold(0.7f)
                .build()
        );
    }

    @Override
    protected void runclassification(Bitmap bitmap) {
        if (bitmap == null) return;

        InputImage inputImage = InputImage.fromBitmap(bitmap, 0);

        imageLabeler.process(inputImage)
                .addOnSuccessListener(new OnSuccessListener<List<ImageLabel>>() {
                    @Override
                    public void onSuccess(@NonNull List<ImageLabel> imageLabels) {
                        if (imageLabels != null && !imageLabels.isEmpty()) {
                            StringBuilder builder = new StringBuilder();
                            for (ImageLabel label : imageLabels) {
                                if (label != null && label.getText() != null) {
                                    builder.append(label.getText())
                                            .append(" : ")
                                            .append(label.getConfidence())
                                            .append("\n");
                                }
                            }

                            if (getTextViewOutput() != null) {
                                String resultText = builder.length() > 0 ? builder.toString() : "No labels detected";
                                getTextViewOutput().setText(resultText);
                            }
                        } else {
                            if (getTextViewOutput() != null)
                                getTextViewOutput().setText("Could not classify");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                        if (getTextViewOutput() != null)
                            getTextViewOutput().setText("Classification failed: " + (e.getMessage() != null ? e.getMessage() : ""));
                    }
                });
    }
}
