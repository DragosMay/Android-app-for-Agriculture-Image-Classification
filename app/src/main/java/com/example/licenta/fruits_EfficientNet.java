// Adaptat dupa tensorflow android | Deploy deep learning model android studio | Java | ml, https://www.youtube.com/watch?v=tySgZ1rEbW4&t=272s

package com.example.licenta;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.licenta.ml.FruitsEfficientNet;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;

public class fruits_EfficientNet extends AppCompatActivity {
    private ImageView imgvieW;
    private Button select, predict;
    private TextView tv;
    private Bitmap img;

    private String[] classLabels = {
            "Apple 6", "Apple Braeburn 1", "Apple Crimson Snow 1", "Apple Golden 1", "Apple Golden 2", "Apple Golden 3",
            "Apple Granny Smith 1", "Apple Pink Lady 1", "Apple Red 1", "Apple Red 2", "Apple Red 3", "Apple Red Delicious 1",
            "Apple Red Yellow 1", "Apple Red Yellow 2", "Apple hit 1", "Apricot 1", "Avocado 1", "Avocado ripe 1",
            "Banana 1", "Banana Lady Finger 1", "Banana Red 1", "Beetroot 1", "Blueberry 1", "Cabbage white 1",
            "Cactus fruit 1", "Cantaloupe 1", "Cantaloupe 2", "Carambula 1", "Carrot 1", "Cauliflower 1", "Cherry 1",
            "Cherry 2", "Cherry Rainier 1", "Cherry Wax Black 1", "Cherry Wax Red 1", "Cherry Wax Yellow 1", "Chestnut 1",
            "Clementine 1", "Cocos 1", "Corn 1", "Corn Husk 1", "Cucumber 1", "Cucumber 3", "Cucumber Ripe 1",
            "Cucumber Ripe 2", "Dates 1", "Eggplant 1", "Eggplant long 1", "Fig 1", "Ginger Root 1", "Granadilla 1",
            "Grape Blue 1", "Grape Pink 1", "Grape White 1", "Grape White 2", "Grape White 3", "Grape White 4",
            "Grapefruit Pink 1", "Grapefruit White 1", "Guava 1", "Hazelnut 1", "Huckleberry 1", "Kaki 1", "Kiwi 1",
            "Kohlrabi 1", "Kumquats 1", "Lemon 1", "Lemon Meyer 1", "Limes 1", "Lychee 1", "Mandarine 1", "Mango 1",
            "Mango Red 1", "Mangostan 1", "Maracuja 1", "Melon Piel de Sapo 1", "Mulberry 1", "Nectarine 1", "Nectarine Flat 1",
            "Nut Forest 1", "Nut Pecan 1", "Onion Red 1", "Onion Red Peeled 1", "Onion White 1", "Orange 1", "Papaya 1",
            "Passion Fruit 1", "Peach 1", "Peach 2", "Peach Flat 1", "Pear 1", "Pear 2", "Pear 3", "Pear Abate 1",
            "Pear Forelle 1", "Pear Kaiser 1", "Pear Monster 1", "Pear Red 1", "Pear Stone 1", "Pear Williams 1",
            "Pepino 1", "Pepper Green 1", "Pepper Orange 1", "Pepper Red 1", "Pepper Yellow 1", "Physalis 1",
            "Physalis with Husk 1", "Pineapple 1", "Pineapple Mini 1", "Pitahaya Red 1", "Plum 1", "Plum 2", "Plum 3",
            "Pomegranate 1", "Pomelo Sweetie 1", "Potato Red 1", "Potato Red Washed 1", "Potato Sweet 1", "Potato White 1",
            "Quince 1", "Rambutan 1", "Raspberry 1", "Redcurrant 1", "Salak 1", "Strawberry 1", "Strawberry Wedge 1",
            "Tamarillo 1", "Tangelo 1", "Tomato 1", "Tomato 2", "Tomato 3", "Tomato 4", "Tomato Cherry Red 1",
            "Tomato Heart 1", "Tomato Maroon 1", "Tomato Yellow 1", "Tomato not Ripened 1", "Walnut 1", "Watermelon 1",
            "Zucchini 1", "Zucchini dark 1"
    };
    private Bundle SavedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fruits_efficientnet);

        select = (Button) findViewById(R.id.bt1);
        predict = (Button) findViewById(R.id.bt2);
        imgvieW = (ImageView) findViewById(R.id.imgview);
        tv = (TextView) findViewById(R.id.textview);

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 100);
            }
        });

        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (img != null) {
                    ProgressDialog progressDialog = new ProgressDialog(fruits_EfficientNet.this);
                    progressDialog.setMessage("Processing...");
                    progressDialog.setCancelable(false);
                    progressDialog.show(); // Show ProgressDialog before starting prediction
                    img = Bitmap.createScaledBitmap(img, 100, 100, true);

                    new Thread(() -> {
                        try {
                            FruitsEfficientNet model = FruitsEfficientNet.newInstance(fruits_EfficientNet.this);

                            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 100, 100, 3}, DataType.FLOAT32);
                            TensorImage tensorImage = new TensorImage(DataType.FLOAT32);
                            tensorImage.load(img);
                            inputFeature0.loadBuffer(tensorImage.getBuffer());

                            // Start measuring time
                            long startTime = System.currentTimeMillis();

                            FruitsEfficientNet.Outputs outputs = model.process(inputFeature0);
                            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                            long endTime = System.currentTimeMillis();
                            long inferenceTime = endTime - startTime; // Time in milliseconds

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
                                tv.setText(result);
                                progressDialog.dismiss(); // Hide ProgressDialog after prediction
                            });

                            model.close();
                        } catch (IOException e) {
                            runOnUiThread(() -> {
                                tv.setText("Model loading failed: " + e.getMessage());
                                progressDialog.dismiss(); // Hide ProgressDialog on error
                            });
                        } catch (Exception e) {
                            runOnUiThread(() -> {
                                tv.setText("Prediction failed: " + e.getMessage());
                                progressDialog.dismiss(); // Hide ProgressDialog on error
                            });
                        }
                    }).start(); // Run prediction in a background thread
                } else {
                    tv.setText("Please select an image first");
                }
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
                imgvieW.setImageURI(uri);
            } catch (IOException e) {
                tv.setText("Error loading image");
            }
        }

    }
}

