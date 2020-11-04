package com.example.codechallenge;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.example.tinysdk.ExoPlayerWrapper;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.util.Log;

public class playeractivity extends Activity implements View.OnClickListener {
    private static final String TAG = "player";

    protected ExoPlayerWrapper mWrapper;
    StyledPlayerView playerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_player);
        setButtons();

        playerView = findViewById(R.id.player_view);
        mWrapper = new ExoPlayerWrapper(this);
        mWrapper.onCreate(savedInstanceState, playerView);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String streamtoplay = intent.getStringExtra(TinySDK_Demo.TEST_STREAM_URI);
        Log.e(TAG, "got url: "+ streamtoplay);
        mWrapper.playStreambyUri(Uri.parse(streamtoplay));
    }

    private void setButtons() {
        RadioButton vt_auto_button = findViewById(R.id.videotrack_auto);
        RadioButton vt1_button = findViewById(R.id.videotrack1_btn);
        RadioButton vt2_button = findViewById(R.id.videotrack2_btn);
        RadioButton vt3_button = findViewById(R.id.videotrack3_btn);
        RadioButton vt4_button = findViewById(R.id.videotrack4_btn);
        RadioButton vt5_button = findViewById(R.id.videotrack5_btn);
        RadioButton at1_button = findViewById(R.id.audiotrack1_btn);
        RadioButton at2_button = findViewById(R.id.audiotrack2_btn);
        RadioButton at3_button = findViewById(R.id.audiotrack3_btn);

        vt_auto_button.setOnClickListener(this);
        vt1_button.setOnClickListener(this);
        vt2_button.setOnClickListener(this);
        vt3_button.setOnClickListener(this);
        vt4_button.setOnClickListener(this);
        vt5_button.setOnClickListener(this);
        at1_button.setOnClickListener(this);
        at2_button.setOnClickListener(this);
        at3_button.setOnClickListener(this);
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
        mWrapper.onStop();
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        mWrapper.onDestroy();
    }

    @Override
    public void onClick(View view) {
        Log.e(TAG, "onClick, to select track:");
    }
}
