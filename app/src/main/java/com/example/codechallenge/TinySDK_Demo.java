package com.example.codechallenge;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.util.Log;

public class TinySDK_Demo extends AppCompatActivity
        implements View.OnClickListener{

    private static final String TAG = "demo";

    public static final String test_stream1 = "https://demo.unified-streaming.com/video/smurfs/smurfs.ism/smurfs.mpd";
    public static final String test_stream2 = "https://media.axprod.net/TestVectors/v7-MultiDRM-SingleKey/Manifest_1080p.mpd";
    public static final String test_stream3 = "https://dash.akamaized.net/envivio/EnvivioDash3/manifest.mpd";
    public static final String TEST_STREAM_URI = "test_stream_url_info";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tiny_s_d_k__demo);
        setButtons();   //stream 4,5,6 are fake buttons, not clickable
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
