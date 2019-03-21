package com.example.ratemyplate;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class Plate {

    private static final String JSON_NAME = "mName";
    private static final String JSON_CAPTION = "mCaption";
    private static final String JSON_IMAGE = "image";



    private String mName;
    private String mCaption;
    private Image mImage;

    public Plate(String mName, String mCaption, Image mImage){
        this.mName = mName;
        this.mCaption = mCaption;
        this.mImage = mImage;
    }

    public Plate(JSONObject json) throws JSONException {
        mName = json.getString(JSON_NAME);
        mCaption = json.getString(JSON_CAPTION);
        mImage = new Image(json.getJSONObject(JSON_IMAGE));
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_NAME, mName);
        json.put(JSON_CAPTION, mCaption);
        json.put(JSON_IMAGE, mImage);
        return json;
    }

    private String getStringFromBitmap(Bitmap bitmapPicture) {
        final int COMPRESSION_QUALITY = 100;
        String encodedImage;
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bitmapPicture.compress(Bitmap.CompressFormat.PNG, COMPRESSION_QUALITY,
                byteArrayBitmapStream);
        byte[] b = byteArrayBitmapStream.toByteArray();
        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encodedImage;
    }

    private Bitmap getBitmapFromString(String stringPicture) {
        byte[] decodedString = Base64.decode(stringPicture, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }






    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmCaption() {
        return mCaption;
    }

    public void setmCaption(String mCaption) {
        this.mCaption = mCaption;
    }

    public Image getmImage() {
        return mImage;
    }

    public void setmImage(Image mImage) {
        this.mImage = mImage;
    }
}
