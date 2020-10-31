package com.example.codechallenge;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tinysdk.ExoPlayerWrapper;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.util.Log;

public class TinySDK_Demo extends AppCompatActivity
        implements View.OnClickListener{

    private static final String TAG = "demo-main";
    protected ExoPlayerWrapper mWrapper;

    private StyledPlayerView playerView;
    private Uri[] VideosUris;

    public static final String test_stream1 = "https://demo.unified-streaming.com/video/smurfs/smurfs.ism/smurfs.mpd";
    public static final String test_stream2 = "https://media.axprod.net/TestVectors/v7-MultiDRM-SingleKey/Manifest_1080p.mpd";
    public static final String test_stream3 = "https://dash.akamaized.net/envivio/EnvivioDash3/manifest.mpd";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tiny_s_d_k__demo);
        Button stream1_button = findViewById(R.id.test_stream_button1);
        Button stream2_button = findViewById(R.id.test_stream_button2);
        Button stream3_button = findViewById(R.id.test_stream_button3);
        stream1_button.setOnClickListener(this);
        stream2_button.setOnClickListener(this);
        stream3_button.setOnClickListener(this);
        playerView = findViewById(R.id.player_view);
        mWrapper = new ExoPlayerWrapper(this);
        mWrapper.onCreate(savedInstanceState, playerView);
    }

    @Override
    public void onStart() {
        Log.e(TAG, "onStart");
        super.onStart();
        mWrapper.onStart();
    }

    @Override
    public void onResume() {
        Log.e(TAG, "onResume");
        super.onResume();
        mWrapper.onResume();
    }

    @Override
    public void onPause() {
        Log.e(TAG, "onPause");
        super.onPause();
        mWrapper.onPause();
    }

    @Override
    public void onStop() {
        Log.e(TAG, "onStop");
        super.onStop();
        mWrapper.onPause();
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        mWrapper.onPause();
    }

    @Override
    public void onClick(View view) {
        Log.e(TAG, "onClick");
        Uri selected_uri=null;
        switch (view.getId()) {
            case R.id.test_stream_button1:
                Log.e(TAG, "to play stream #1");
                selected_uri = Uri.parse(test_stream1);
                break;
            case R.id.test_stream_button2:
                Log.e(TAG, "to play stream #2");
                selected_uri = Uri.parse(test_stream2);
                break;
            case R.id.test_stream_button3:
                Log.e(TAG, "to play stream #3");
                selected_uri = Uri.parse(test_stream3);
                break;
        }
        if(selected_uri != null)
            mWrapper.playStreambyUri(selected_uri);
    }

}
