// Adaptat dupa tensorflow android | Deploy deep learning model android studio | Java | ml, https://www.youtube.com/watch?v=tySgZ1rEbW4&t=272s

package com.example.licenta;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.licenta.ml.LeafDiseaseVresCnn;

import android.app.ProgressDialog; // Add this import

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;

public class frunze extends AppCompatActivity { // Renamed activity class

    private ImageView imgview;
    private Button select, predict;
    private TextView tv;
    private Bitmap img;
    private String[] classLabels = {
            "Apple___Apple_scab", "Apple___Black_rot", "Apple___Cedar_apple_rust", "Apple___healthy",
            "Blueberry___healthy", "Cherry_(including_sour)___Powdery_mildew", "Cherry_(including_sour)___healthy",
            "Corn_(maize)___Cercospora_leaf_spot_Gray_leaf_spot", "Corn_(maize)___Common_rust", "Corn_(maize)___Northern_Leaf_Blight",
            "Corn_(maize)___healthy", "Grape___Black_rot", "Grape___Esca_(Black_Measles)", "Grape___Leaf_blight_(Isariopsis_Leaf_Spot)",
            "Grape___healthy", "Orange___Haunglongbing_(Citrus_greening)", "Peach___Bacterial_spot", "Peach___healthy",
            "Pepper,_bell___Bacterial_spot", "Pepper,_bell___healthy", "Potato___Early_blight", "Potato___Late_blight",
            "Potato___healthy", "Raspberry___healthy", "Soybean___healthy", "Squash___Powdery_mildew", "Strawberry___Leaf_scorch",
            "Strawberry___healthy", "Tomato___Bacterial_spot", "Tomato___Early_blight", "Tomato___Late_blight", "Tomato___Leaf_Mold",
            "Tomato___Septoria_leaf_spot", "Tomato___Spider_mites_Two-spotted_spider_mite", "Tomato___Target_Spot",
            "Tomato___Tomato_Yellow_Leaf_Curl_Virus", "Tomato___Tomato_mosaic_virus", "Tomato___healthy"
    };


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaves);  // Corrected layout file

        imgview = findViewById(R.id.imageView);
        select = findViewById(R.id.button);
        predict = findViewById(R.id.button2);
        tv = findViewById(R.id.textView);

        select.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, 100);
        });

        predict.setOnClickListener(v -> {
            if (img != null) {
                // Show the loading dialog
                ProgressDialog progressDialog = new ProgressDialog(frunze.this);
                progressDialog.setMessage("Processing...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                img = Bitmap.createScaledBitmap(img, 100, 100, true); // Adjust size

                new Thread(() -> {
                    try {
                        LeafDiseaseVresCnn model = LeafDiseaseVresCnn.newInstance(frunze.this);

                        // Preprocess the image
                        TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 100, 100, 3}, DataType.FLOAT32);
                        TensorImage tensorImage = new TensorImage(DataType.FLOAT32);
                        tensorImage.load(img);
                        inputFeature0.loadBuffer(tensorImage.getBuffer());

                        // Start measuring time
                        long startTime = System.currentTimeMillis();

                        LeafDiseaseVresCnn.Outputs outputs = model.process(inputFeature0);
                        TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                        long endTime = System.currentTimeMillis();
                        long inferenceTime = endTime - startTime; // Time in milliseconds

                        // Get predictions
                        float[] confidences = outputFeature0.getFloatArray();
                        int maxIdx = 0;
                        float maxConfidence = confidences[0];
                        for (int i = 1; i < confidences.length; i++) {
                            if (confidences[i] > maxConfidence) {
                                maxConfidence = confidences[i];
                                maxIdx = i;
                            }
                        }

                        String result = "Prediction: " + classLabels[maxIdx] + "\nConfidence: "
                                + String.format("%.2f", maxConfidence * 100) + "%"
                                + "\nInference Time: " + inferenceTime + " ms";

                        runOnUiThread(() -> {
                            tv.setText(result); // Update UI with prediction result
                            progressDialog.dismiss(); // Hide the loading dialog
                        });

                        model.close(); // Close model
                    } catch (IOException e) {
                        runOnUiThread(() -> {
                            tv.setText("Error during prediction");
                            progressDialog.dismiss(); // Hide the loading dialog
                        });
                        e.printStackTrace();
                    }
                }).start(); // Run the prediction in a background thread
            } else {
                tv.setText("Please select an image first");
            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onActivityResult(int requestcode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestcode, resultCode, data);

        if (requestcode == 100 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                img = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                imgview.setImageURI(uri);
            } catch (IOException e) {
                tv.setText("Error loading image: " + e.getMessage());
            }
        }
    }
}
