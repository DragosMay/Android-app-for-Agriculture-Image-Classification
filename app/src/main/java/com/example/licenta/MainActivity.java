// Adaptat dupa tensorflow android | Deploy deep learning model android studio | Java | ml, https://www.youtube.com/watch?v=tySgZ1rEbW4&t=272s
package com.example.licenta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button fruitButton, leafButton, fruitMobileNetButton, fruitsEfficientNetButton, leafMobileNet, leafefff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Layout with buttons

        // Initialize buttons
        fruitButton = findViewById(R.id.fruitVCNN);
        leafButton = findViewById(R.id.leafvres);
        fruitMobileNetButton = findViewById(R.id.fruitmob);  // New button
        fruitsEfficientNetButton = findViewById(R.id.fruitEff);
        leafMobileNet = findViewById(R.id.leafmob);
        leafefff = findViewById(R.id.leafeff);

        // Set click listeners for each button
        setButtonClickListener(fruitButton, fruits_classification.class);  // Fruit Classification
        setButtonClickListener(leafButton, frunze.class);                  // Leaf Disease Classification
        setButtonClickListener(fruitMobileNetButton, fruits_MobileNet.class);  // Fruit MobileNet Classification
        setButtonClickListener(fruitsEfficientNetButton, fruits_EfficientNet.class);  // Fruit EfficientNet Classification
        setButtonClickListener(leafMobileNet, leafvcnn.class);
        setButtonClickListener(leafefff, leafefficient.class);
    }

    // Helper method to set up click listeners for buttons
    private void setButtonClickListener(Button button, final Class<?> activityClass) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the specified activity
                Intent intent = new Intent(MainActivity.this, activityClass);
                startActivity(intent);
            }
        });
    }
}
