package com.example.codechallenge;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class TinySDK_Demo extends AppCompatActivity
        implements View.OnClickListener{

    private static final String TAG = "demo";
    private static final String streamlistfile = "media.exolist.json";
    public static final String test_stream1 = "https://demo.unified-streaming.com/video/smurfs/smurfs.ism/smurfs.mpd";
    public static final String test_stream2 = "https://media.axprod.net/TestVectors/v7-MultiDRM-SingleKey/Manifest_1080p.mpd";
    public static final String test_stream3 = "https://dash.akamaized.net/envivio/EnvivioDash3/manifest.mpd";
    public static final String TEST_STREAM_URI = "test_stream_url_info";
    ArrayList<HashMap<String, String>> StreamList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        loadstreams(streamlistfile);
        setContentView(R.layout.activity_tiny_s_d_k__demo);
        setButtons();   //stream 4,5,6 are fake buttons, not clickable
    }

    private void loadstreams(String filename) {
        Log.e(TAG, "loadstream");
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset(this, filename));
            JSONArray m_jArry = obj.getJSONArray("samples");
            StreamList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m_li;

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                String stream_name = jo_inside.getString("name");
                String stream_uri = jo_inside.getString("uri");

                m_li = new HashMap<String, String>();
                m_li.put("name", stream_name);
                m_li.put("uri", stream_uri);

                StreamList.add(m_li);
                Log.e(TAG, "to add: "+stream_name+ ", "+stream_uri);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String loadJSONFromAsset(Context context, String Jsonfile) {
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

    private void setButtons() {
        Button stream1_button = findViewById(R.id.test_stream_button1);
        Button stream2_button = findViewById(R.id.test_stream_button2);
        Button stream3_button = findViewById(R.id.test_stream_button3);
        Button stream4_button = findViewById(R.id.test_stream_button4);
        Button stream5_button = findViewById(R.id.test_stream_button5);
        Button stream6_button = findViewById(R.id.test_stream_button6);
        stream1_button.setOnClickListener(this);
        stream2_button.setOnClickListener(this);
        stream3_button.setOnClickListener(this);
        stream4_button.setOnClickListener(this);
        stream5_button.setOnClickListener(this);
        stream6_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Log.e(TAG, "onClick, to select stream for test:");
        String errmsg, streamtoplay=null;

        switch (view.getId()) {
            case R.id.test_stream_button1:
                streamtoplay = test_stream1;
                break;
            case R.id.test_stream_button2:
                streamtoplay = test_stream2;
                break;
            case R.id.test_stream_button3:
                streamtoplay = test_stream3;
                break;
            default:
                Log.e(TAG, "no stream assigned to this button yet");
                errmsg = "no stream assigned to this button yet";
                Toast.makeText(getApplicationContext(), errmsg, Toast.LENGTH_LONG).show();
                break;
        }

        if(streamtoplay != null) {
            Log.e(TAG, "to play stream: "+streamtoplay);
            Intent intent = new Intent(this, playeractivity.class);
            intent.putExtra(TEST_STREAM_URI, streamtoplay);
            startActivity(intent);
        }
    }
}
