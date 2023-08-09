package com.example.objectdetection.audio;

import android.media.AudioRecord;
import android.os.Bundle;
import android.view.View;

import com.example.objectdetection.helper.AudioHelperActivity;

import org.tensorflow.lite.support.audio.TensorAudio;
import org.tensorflow.lite.support.label.Category;
import org.tensorflow.lite.task.audio.classifier.AudioClassifier;
import org.tensorflow.lite.task.audio.classifier.Classifications;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class AudioClassificationActivity extends AudioHelperActivity {

    private String model = "lite-model_yamnet_classification_tflite_1.tflite";

    private AudioRecord audioRecord;
    private TimerTask timerTask;

    private AudioClassifier audioClassifier;
    private TensorAudio tensorAudio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            audioClassifier = AudioClassifier.createFromFile(this,model);
        }
        catch (IOException e){
            e.printStackTrace();
        }

        tensorAudio = audioClassifier.createInputTensorAudio();
    }

    @Override
    public void startRecording(View view) {
        super.startRecording(view);

        TensorAudio.TensorAudioFormat format = audioClassifier.getRequiredTensorAudioFormat();
        String specs = "Number of channels: "+ format.getChannels() + "\n"
                + "Sample Rate: " + format.getSampleRate();
        specsTextView.setText(specs);

        audioRecord = audioClassifier.createAudioRecord();
        audioRecord.startRecording();

        timerTask = new TimerTask() {
            @Override
            public void run() {
                List<Classifications> output = audioClassifier.classify(tensorAudio);

                List<Category> finalOutput = new ArrayList<>();

                for (Classifications classifications : output){
                    for (Category category: classifications.getCategories()){
                        if (category.getScore() > 0.3f){
                            finalOutput.add(category);
                        }
                    }
                }

                StringBuilder outputstr = new StringBuilder();

                for (Category category : finalOutput){
                    outputstr.append(category.getLabel())
                            .append(": ")
                            .append(category.getScore())
                            .append("\n");
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        outputTextView.setText(outputstr.toString());

                    }
                });
            }
        };
        new Timer().scheduleAtFixedRate(timerTask,1,500);
    }

    @Override
    public void stopRecording(View view) {
        super.stopRecording(view);
        timerTask.cancel();
        audioRecord.stop();
    }
}
