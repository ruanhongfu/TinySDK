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
    //save uri of both external and internal stream samples into hash with title as key
    //stream title couldn't be same, or uri will be overwritten
    private final static HashMap<String, String> m_uri_hash = new HashMap<>();
    private final static HashMap<String, List<String>> mStreamList = new HashMap<>();

    public static HashMap<String, List<String>> LoadStreamSamples (Context context, String filename) {
        Log.d(TAG, "getData");

        List<String>  m_external_title_list = new ArrayList<>();
        List<String>  m_local_title_list = new ArrayList<>();
        m_uri_hash.clear();
        mStreamList.clear();
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset(context, filename));
            //String.valueof doesn't work, don't know why...use hard code for temp
            JSONArray m_jArry_external = obj.getJSONArray("external sample streams"/*String.valueOf(R.string.group_external)*/);

            for (int i = 0; i < m_jArry_external.length(); i++) {
                JSONObject jo_inside = m_jArry_external.getJSONObject(i);
                String stream_name = jo_inside.getString("name");
                String stream_uri = jo_inside.getString("uri");
                m_external_title_list.add(i, stream_name);
                m_uri_hash.put(stream_name, stream_uri);
                Log.d(TAG, "to add: "+ stream_name+ ", "+stream_uri);
            }
            mStreamList.put("external sample streams"/*String.valueOf(R.string.group_external)*/, m_external_title_list);
            //add new stream group for local server
            JSONArray m_jArry_internal = obj.getJSONArray("local sample streams");
            Log.d(TAG, "m_jArry.length():"+m_jArry_internal.length());
            for (int i = 0; i < m_jArry_internal.length(); i++) {
                JSONObject jo_inside = m_jArry_internal.getJSONObject(i);
                String stream_name = jo_inside.getString("name");
                String stream_uri = jo_inside.getString("uri");
                m_local_title_list.add(i, stream_name);
                m_uri_hash.put(stream_name, stream_uri); //not support same stream name....
                Log.d(TAG, "to add: "+ stream_name+ ", "+stream_uri);
            }
            mStreamList.put("local sample streams", m_local_title_list);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e(TAG, "stream list size:" + mStreamList.size());
        Log.e(TAG, ""+mStreamList.get("external sample streams"));
        Log.e(TAG, ""+mStreamList.get("local sample streams"));

        return mStreamList;
    }

    public static String GetSampleUri(int groupposition, int childposition) {
        //Log.e(TAG, "length of "+m_uri_list.get(groupposition).length());
        String uri=null;
        if((groupposition == 0) && (mStreamList.size()!=0)) {
            uri = mStreamList.get("local sample streams").get(childposition);
            Log.e(TAG, "" + uri);
        } else if ((groupposition == 1) && (mStreamList.size()!=0)) {
            uri = mStreamList.get("external sample streams").get(childposition);
            Log.e(TAG, "" + uri);
        }
        return m_uri_hash.get(uri);
    }

    private static String loadJSONFromAsset(Context context, String Jsonfile) {
        String json;
        try {
            InputStream is = context.getAssets().open(Jsonfile);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF_8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}


