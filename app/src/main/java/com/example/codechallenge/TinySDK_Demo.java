package com.example.codechallenge;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.ui.StyledPlayerControlView;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.util.Log;

public class TinySDK_Demo extends AppCompatActivity
        implements View.OnClickListener, StyledPlayerControlView.VisibilityListener {
    private static final String TAG = "demo-main";
    protected StyledPlayerView playerView;
    protected LinearLayout debugRootView;
    protected TextView debugTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiny_s_d_k__demo);
        Button stream1_button = findViewById(R.id.test_stream_button1);
        Button stream2_button = findViewById(R.id.test_stream_button2);
        Button stream3_button = findViewById(R.id.test_stream_button3);
        stream1_button.setOnClickListener(this);
        stream2_button.setOnClickListener(this);
        stream3_button.setOnClickListener(this);

        debugRootView = findViewById(R.id.controls_root);
        debugTextView = findViewById(R.id.debug_text_view);
        playerView = findViewById(R.id.player_view);
        playerView.setControllerVisibilityListener(this);
        playerView.requestFocus();
    }

    @Override
    public void onClick(View view) {
        Log.e(TAG, "onClick");

        switch (view.getId()) {
            case R.id.test_stream_button1:
                Log.e(TAG, "to play stream #1");
                break;
            case R.id.test_stream_button2:
                Log.e(TAG, "to play stream #2");
                break;
            case R.id.test_stream_button3:
                Log.e(TAG, "to play stream #3");
                break;
        }
    }

    @Override
    public void onVisibilityChange(int visibility) {
        Log.e(TAG, "onVisibilityChange");
        debugRootView.setVisibility(visibility);
    }
}