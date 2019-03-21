package com.example.ratemyplate;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class UploadActivity extends AppCompatActivity {

    private static final String TAG = "UploadActivity";
    private static final int REQUEST_PHOTO = 1;
    private static final int PICK_IMAGE = 2;
    private static final String EXTRA_PHOTO_FILENAME = "com.example.ratemyplate.photo_filename";

    private Button takePhotoButton;
    private Button choosePhotoButton;
    private Button uploadButton;
    private EditText nameText;
    private EditText captionText;

    Uri imageUri;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        takePhotoButton = findViewById(R.id.takePhoto);
        choosePhotoButton = findViewById(R.id.chooseImage);
        uploadButton = findViewById(R.id.upload);
        nameText = findViewById(R.id.editNameText);
        captionText = findViewById(R.id.editCaptionText);
        imageView = findViewById(R.id.imageView);

        verifyPermissions();
        takePhoto();
        choosePhotoFromStorage();
        upload(uploadButton);

        //TooLargeTool.INSTANCE.startLogging(this.getApplication());


    }

    public String getNameText(){
        return nameText.getText().toString();

    }

    public String getCaptionText(){
        return captionText.getText().toString();
    }

    public Bitmap getImage(){
        return ((BitmapDrawable)imageView.getDrawable()).getBitmap();
    }



    private void upload(View view) {
        View.OnClickListener uploadListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMyPlates();
            }

        };
        uploadButton.setOnClickListener(uploadListener);
    }

    private void openMyPlates(){
        Intent intent = new Intent(this, Uploaded_Plates_List.class);
        startActivity(intent);
    }



    private void verifyPermissions() {
        Log.d(TAG, "verifyPermissions: asking user for permissions");
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[1]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[2]) == PackageManager.PERMISSION_GRANTED) {

        } else {
            ActivityCompat.requestPermissions(UploadActivity.this, permissions, REQUEST_PHOTO);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if(requestCode == REQUEST_PHOTO){
                //Create a new Image object and attach it to imageView
                String filename = data.getStringExtra(MediaStore.ACTION_IMAGE_CAPTURE);
                if (filename != null) {
                    Image i = new Image(filename);

                }




            }
            else if(requestCode == PICK_IMAGE){
                imageUri = data.getData();
                String absoluteImagePath = getImagePath(imageUri);
                Bitmap bitmap = BitmapFactory.decodeFile(absoluteImagePath);
                Bitmap rotatedBm = getRotatedImage(bitmap, absoluteImagePath);
                Bitmap resizedBm = getResizedBitmap(rotatedBm, 300, 220);
                imageView.setImageBitmap(resizedBm);
            }
        }
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {

        int width = bm.getWidth();

        int height = bm.getHeight();

        float scaleWidth = ((float) newWidth) / width;

        float scaleHeight = ((float) newHeight) / height;

// CREATE A MATRIX FOR THE MANIPULATION

        Matrix matrix = new Matrix();

// RESIZE THE BIT MAP

        matrix.postScale(scaleWidth, scaleHeight);

// RECREATE THE NEW BITMAP

        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);

        return resizedBitmap;

    }

    public String getImagePath(Uri uri){
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":")+1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    private void takePhoto() {
        Log.d(TAG, "takePhoto: launching intent to capture image");

        View.OnClickListener takePhotoListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePhoto, REQUEST_PHOTO);
            }
        };
        takePhotoButton.setOnClickListener(takePhotoListener);
    }


    private File createPhotoFile() {
        String name = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(name, ".jpg", storageDir);
        } catch (IOException e) {
            Log.d("my log", "Excep : " + e.toString());
        }
        return image;
    }

    private void choosePhotoFromStorage(){
        Log.d(TAG, "selectPhotoFromStorage: selecting photo from gallery");

        View.OnClickListener choosePhotoListener = new View.OnClickListener(){
            @Override
            public void onClick(View view){
                openGallery();

            }
        };
        choosePhotoButton.setOnClickListener(choosePhotoListener);
    }

    private void openGallery(){
        Intent gallery = new Intent();
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        gallery.addCategory(Intent.CATEGORY_OPENABLE);
        gallery.setType("image/*");
        startActivityForResult(Intent.createChooser(gallery, "Select plate"), PICK_IMAGE);
    }

    private Bitmap getRotatedImage(Bitmap bitmap, String pathToFile) {
        ExifInterface exifInterface = null;
        try {
            exifInterface = new ExifInterface(pathToFile);
        } catch (IOException e){
            e.printStackTrace();
        }
        int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(270);
                break;
            case ExifInterface.ORIENTATION_NORMAL:
                matrix.setRotate(0);
                break;
        }
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return rotatedBitmap;
    }



}
