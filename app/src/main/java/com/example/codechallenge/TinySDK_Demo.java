package com.example.codechallenge;

import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackPreparer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.StyledPlayerControlView;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.EventLogger;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

public class TinySDK_Demo extends AppCompatActivity
        implements View.OnClickListener, StyledPlayerControlView.VisibilityListener, PlaybackPreparer, View.OnTouchListener, Player.EventListener {

    private static final String TAG = "demo-main";

    private StyledPlayerView playerView;
    private DataSource.Factory dataSourceFactory;
    private SimpleExoPlayer player;
    private List<MediaItem> mediaItems;
    private DefaultTrackSelector trackSelector;
    private DefaultTrackSelector.Parameters trackSelectorParameters;
    private DefaultLoadControl mLoadControl;
    private Uri[] VideosUris;
    private MediaSource mMediaSource;

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
        findViewById(R.id.player_view).setOnTouchListener(this);

        playerView = findViewById(R.id.player_view);
        playerView.setControllerVisibilityListener(this);
        playerView.requestFocus();

        dataSourceFactory = new DefaultDataSourceFactory(this.getBaseContext(),
                Util.getUserAgent(this.getBaseContext(), this.getBaseContext().getString(R.string.app_name)), null);
        DefaultTrackSelector.ParametersBuilder builder =
                new DefaultTrackSelector.ParametersBuilder(/* context= */ this);
        trackSelectorParameters = builder.build();

        InitializePlayer();
    }

    private void InitializePlayer() {
        Log.e(TAG, "InitializePlayer");
        dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, this.getString(R.string.app_name)), new DefaultBandwidthMeter.Builder(this).build());

        DefaultLoadControl.Builder builder = new DefaultLoadControl.Builder();
        builder.setAllocator(new DefaultAllocator(true, 2 * 1024 * 1024));
        builder.setBufferDurationsMs(5000, 5000, 5000, 5000);
        builder.setPrioritizeTimeOverSizeThresholds(true);
        mLoadControl = builder.build();

        VideosUris = new Uri[3];
        VideosUris[0] = Uri.parse(test_stream1);
        VideosUris[1] = Uri.parse(test_stream2);
        VideosUris[2] = Uri.parse(test_stream3);

        RenderersFactory renderersFactory = new DefaultRenderersFactory(this.getApplicationContext());
        MediaSourceFactory mediaSourceFactory =
                new DefaultMediaSourceFactory(dataSourceFactory);

        player = new SimpleExoPlayer.Builder(this, renderersFactory)
                    .setMediaSourceFactory(mediaSourceFactory)
                    .build();
        player.addAnalyticsListener(new EventLogger(trackSelector));
        player.setAudioAttributes(AudioAttributes.DEFAULT, /* handleAudioFocus= */ true);
        player.setPlayWhenReady(true);

        playerView.setPlayer(player);
        playerView.setPlaybackPreparer(this);

        MediaSource[] mediaSources = new MediaSource[3];
        mediaSources[0] = new DashMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(VideosUris[0]));
        mediaSources[1] = new DashMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(VideosUris[1]));
        mediaSources[2] = new DashMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(VideosUris[2]));
        mMediaSource = new ConcatenatingMediaSource(mediaSources);
        player.setMediaSource(mMediaSource);
        player.prepare();
    }

    @Override
    public void onStart() {
        Log.e(TAG, "onStart");
        super.onStart();
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
    }

    @Override
    public void preparePlayback() {
        Log.e(TAG, "preparePlayback");
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Log.e(TAG, "onTouch");

        FrameLayout layout = playerView.getOverlayFrameLayout();
        return layout != null && view.getId() == layout.getId();

    }
}
