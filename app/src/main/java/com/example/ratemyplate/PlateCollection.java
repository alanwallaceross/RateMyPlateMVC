package com.example.ratemyplate;


import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

public class PlateCollection {
    private static final String TAG = "PlateCollection";
    private static final String FILENAME = "plates.json";

    private ArrayList<Plate> mPlates;
    private RateMyPlateJSONSerializer mSerializer;

    private static PlateCollection sPlateCollection;
    private Context mAppContext;

    private PlateCollection(Context appContext){
        mAppContext = appContext;
        mSerializer = new RateMyPlateJSONSerializer(mAppContext, FILENAME);

        try {
            mPlates = mSerializer.loadPlates();
        } catch (Exception e) {
            mPlates = new ArrayList<Plate>();
            Log.e(TAG, "Error loading crimes: ", e);
        }
    }

    public static PlateCollection get(Context c){
        if (sPlateCollection == null) {
            sPlateCollection = new PlateCollection(c.getApplicationContext());
        }
        return sPlateCollection;
    }

    public void addPlate(Plate p){
        mPlates.add(p);
    }

    public void removePlateByPosition(int i){
        mPlates.remove(i);
    }

    public boolean savePlates() {
        try {
            mSerializer.savePlates(mPlates);
            Log.d(TAG, "plates saved to file");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error saving plates: ", e);
            return false;
        }
    }

    public ArrayList<Plate> getmPlates() {
        return mPlates;
    }

    public void setmPlates(ArrayList<Plate> mPlates) {
        this.mPlates = mPlates;
    }

    public Context getmAppContext() {
        return mAppContext;
    }

    public void setmAppContext(Context mAppContext) {
        this.mAppContext = mAppContext;
    }
}
