package com.example.ratemyplate;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

public class RateMyPlateJSONSerializer {

    private Context mContext;
    private String mFilename;

    public RateMyPlateJSONSerializer(Context c, String f) {
        mContext = c;
        mFilename = f;
    }

    public void savePlates(ArrayList<Plate> plates)
        throws JSONException, IOException {
        // Build an array in JSON
        JSONArray array = new JSONArray();
        for (Plate p : plates)
            array.put(p.toJSON());

        //
        Writer writer = null;
        try {
            OutputStream out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        } finally {
            if (writer != null)
                writer.close();
        }
    }
}
