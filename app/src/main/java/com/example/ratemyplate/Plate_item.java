package com.example.ratemyplate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Plate_item extends AppCompatActivity {

    // define a constant for TAGS
    private static final String TAG = "Plate_item";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plate);

        getIncomingIntent();
    }


    private void getIncomingIntent() {
        Log.d(TAG, "getIncomingIntent: checking for incoming intents");

        //checks if there is intent, stops the app from crashing if there isnt anything to get
        if(getIntent().hasExtra("image_url") && getIntent().hasExtra("image_name")) {
            Log.d(TAG, "getIncomingIntent: found intent");

            // gets the image intent
            String imageUrl = getIntent().getStringExtra("image_url");
            String imageName = getIntent().getStringExtra("image_name");
            Log.d("images", imageName + imageUrl);
            setImage(imageUrl, imageName);
        }
    }

    private void setImage(String imageUrl, String imageName) {
        Log.d(TAG,"setmImage: setting the image and to widgets");

        // sets the text view
        TextView name = findViewById(R.id.image_name);
        name.setText(imageName);

        //set the image view
        ImageView image = findViewById(R.id.imageView);
        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(image);
    }
}
