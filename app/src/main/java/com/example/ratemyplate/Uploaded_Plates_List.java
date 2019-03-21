package com.example.ratemyplate;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Uploaded_Plates_List extends AppCompatActivity {
    // for debugging
    private static final String TAG = "Uploaded_Plates_List";
    private static final String imageNameFileName = "ImageNameFile";
    private static final String imageCaptionFileName = "ImageCaptionFile";
    private static final String imageFileName = "ImageFile";

    // variables


    //passing the data to the adapters
    static private ArrayList<String> imageNames = new ArrayList<>();
    static private ArrayList<String> imageCaptions = new ArrayList<>();
    static private ArrayList<Bitmap> images = new ArrayList<>();
    private RecyclerViewAdapter adapter;
    static private PlateCollection plateCollection;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_plates);

//        loadData
//        for(int i = 0; i < imageNames.size(); i++){
//            imageNames.set(i, getSavedStringArrayList(imageNameFileName).get(i));
//            imageCaptions.set(i, getSavedStringArrayList(imageCaptionFileName).get(i));
//            images.set(i, getSavedImageArrayList(imageFileName).get(i));
//
//        }

        initRecyclerView();
        initImageBitmaps();


//        saveData
//        saveStringArrayList(imageNames, imageNameFileName);
//        saveStringArrayList(imageCaptions, imageCaptionFileName);
//        saveImageArrayList(images, imageFileName);

        // debugging
        Log.d(TAG, "onCreate: started");
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(imageNames, imageCaptions, images, this){

        };
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        if (savedInstanceState != null){
            imageNames = savedInstanceState.getStringArrayList("image_names");
            imageCaptions = savedInstanceState.getStringArrayList("image_captions");
            images = savedInstanceState.getParcelableArrayList("images");
        }


    }

    @Override
    public void onPause(){
        super.onPause();
        PlateCollection.get(getApplication()).savePlates();
    }


    private void saveData(){
//        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        Gson gson = new Gson();
//        String jsonNames = gson.toJson(imageNames);
//        String jsonCaptions = gson.toJson(imageCaptions);
//        String jsonImages = gson.toJson(images);
//        editor.putString("image_name_list", jsonNames);
//        editor.putString("image_caption_list", jsonCaptions);
//        editor.putString("image_list", jsonImages);
//        editor.apply();
//        String fileForNames = "fileForNames";
//        File file = new File(this.getFilesDir(), fileForNames);
//        FileOutputStream outputStream;
//        try{
//            outputStream = openFileOutput(fileForNames, Context.MODE_PRIVATE);
//            for(int i = 0; i < imageNames.size(); i++){
//                outputStream.write(imageNames.indexOf(0));
//            }
//            outputStream.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String fileForCaptions = "fileForCaptions";
//        File file1 = new File(this.getFilesDir(), fileForCaptions);
//        FileOutputStream outputStream1;
//        try{
//            outputStream = openFileOutput(fileForCaptions, Context.MODE_PRIVATE);
//            for(int i = 0; i < imageCaptions.size(); i++){
//                outputStream1.write(imageCaptions.index);
//            }
//        }
    }

    private ArrayList<String> getSavedStringArrayList(String fileName) {
        ArrayList<String> savedArrayList = null;

        try {
            FileInputStream inputStream = openFileInput(fileName);
            ObjectInputStream in = new ObjectInputStream(inputStream);
            savedArrayList = (ArrayList<String>) in.readObject();
            in.close();
            inputStream.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return savedArrayList;
    }

    private ArrayList<Bitmap> getSavedImageArrayList(String fileName) {
        ArrayList<Bitmap> savedArrayList = null;

        try {
            FileInputStream inputStream = openFileInput(fileName);
            ObjectInputStream in = new ObjectInputStream(inputStream);
            savedArrayList = (ArrayList<Bitmap>) in.readObject();
            in.close();
            inputStream.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return savedArrayList;
    }

    private void saveStringArrayList(ArrayList<String> arrayList, String fileName) {
        try {
            FileOutputStream fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
            out.writeObject(arrayList);
            out.close();
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveImageArrayList(ArrayList<Bitmap> arrayList, String fileName) {
        try {
            FileOutputStream fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
            out.writeObject(arrayList);
            out.close();
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void loadData(){
//        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
//        Gson gson = new Gson();
//        String jsonNames = sharedPreferences.getString("image_name_list", null);
//        String jsonCaptions = sharedPreferences.getString("image_caption_list", null);
//        String jsonImages = sharedPreferences.getString("image_list", null);
//        Type typeNames = new TypeToken<ArrayList<String>>() {}.getType();
//        Type typeCaptions = new TypeToken<ArrayList<String>>() {}.getType();
//        Type typeImages = new TypeToken<ArrayList<Bitmap>>() {}.getType();
//        imageNames = gson.fromJson(jsonNames, typeNames);
//        imageCaptions = gson.fromJson(jsonCaptions, typeCaptions);
//        images = gson.fromJson(jsonImages, typeImages);
//
//
//        if (imageNames == null || imageCaptions == null || images == null){
//            imageNames = new ArrayList<>();
//            imageCaptions = new ArrayList<>();
//            images = new ArrayList<>();
//        }

    }

//    @Override
//    protected void onSaveInstanceState(Bundle savedInstanceState){
//        super.onSaveInstanceState(savedInstanceState);
//        savedInstanceState.putStringArrayList("image_names", imageNames);
//        savedInstanceState.putStringArrayList("image_captions", imageCaptions);
//        savedInstanceState.putParcelableArrayList("images", images);
//    }

//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState){
//        super.onRestoreInstanceState(savedInstanceState);
//        imageNames = savedInstanceState.getStringArrayList("image_names");
//        imageCaptions = savedInstanceState.getStringArrayList("image_captions");
//        images = savedInstanceState.getParcelableArrayList("images");
//    }

    protected void initImageBitmaps() {
        Log.d(TAG, "initImagesBitmaps: preparing bitmaps");

        //plate images from google
        //Plate name

        imageNames.add(getIntent().getStringExtra("name"));
        imageCaptions.add(getIntent().getStringExtra("caption"));
        byte[] byteArray = getIntent().getByteArrayExtra("image");
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        images.add(bitmap);

    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init RecyclerView");



    }




}
