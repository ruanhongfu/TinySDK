package com.example.codechallenge;

import android.content.Context;

import com.google.android.exoplayer2.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SampleGroupList {
    private static final String TAG = "samplelist";
    private static List<String>  m_title_list = new ArrayList<String>();
    private static List<String>  m_uri_list = new ArrayList<String>();;

    public static HashMap<String, List<String>> getData (Context context, String filename) {
        Log.e(TAG, "getData");
        HashMap<String, List<String>> StreamList = new HashMap<String, List<String>>();

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset(context, filename));
            JSONArray m_jArry = obj.getJSONArray("samples online");
            HashMap<String, String> m_li;
            Log.e(TAG, "m_jArry.length():"+m_jArry.length());
            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                String stream_name = jo_inside.getString("name");
                String stream_uri = jo_inside.getString("uri");
                m_title_list.add(i, stream_name);
                m_uri_list.add(i, stream_uri);
                Log.e(TAG, "to add: "+stream_name+ ", "+stream_uri);
            }
            StreamList.put("samples online", m_title_list);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return StreamList;
    }

    public static String GetSampleUri(int childposition) {
        return m_uri_list.get(childposition);
    }

    private static String loadJSONFromAsset(Context context, String Jsonfile) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(Jsonfile);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}


