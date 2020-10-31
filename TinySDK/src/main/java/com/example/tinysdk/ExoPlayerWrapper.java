package com.example.tinysdk;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

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

public class ExoPlayerWrapper implements
        View.OnClickListener,
        View.OnTouchListener,
        Player.EventListener,
        StyledPlayerControlView.VisibilityListener,
        PlaybackPreparer {
    private static final String TAG = "exo-wrapper";
    private DataSource.Factory dataSourceFactory;
    private SimpleExoPlayer player;
    private List<MediaItem> mediaItems;
    private DefaultTrackSelector trackSelector;
    private DefaultTrackSelector.Parameters trackSelectorParameters;
    private DefaultLoadControl mLoadControl;
    private MediaSource mMediaSource;
    private Context mContext;
    private StyledPlayerView mPlayview;

    public ExoPlayerWrapper(Context context) {
        mContext = context;
    }

    public void onCreate(Bundle savedInstanceState, StyledPlayerView playerView) {
        mPlayview = playerView;
        mPlayview.setOnTouchListener(this);
        mPlayview.requestFocus();

        dataSourceFactory = new DefaultDataSourceFactory(mContext,
                Util.getUserAgent(mContext, null));
        DefaultTrackSelector.ParametersBuilder builder =
                new DefaultTrackSelector.ParametersBuilder(/* context= */ mContext);
        trackSelectorParameters = builder.build();

        InitializePlayer();
    }

    private void InitializePlayer() {
        Log.e(TAG, "InitializePlayer");
        dataSourceFactory = new DefaultDataSourceFactory(mContext,
                Util.getUserAgent(mContext, null), new DefaultBandwidthMeter.Builder(mContext).build());

        DefaultLoadControl.Builder builder = new DefaultLoadControl.Builder();
        builder.setAllocator(new DefaultAllocator(true, 2 * 1024 * 1024));
        builder.setBufferDurationsMs(5000, 5000, 5000, 5000);
        builder.setPrioritizeTimeOverSizeThresholds(true);
        mLoadControl = builder.build();
        RenderersFactory renderersFactory = new DefaultRenderersFactory(mContext);
        MediaSourceFactory mediaSourceFactory =
                new DefaultMediaSourceFactory(dataSourceFactory);

        player = new SimpleExoPlayer.Builder(mContext, renderersFactory)
                .setMediaSourceFactory(mediaSourceFactory)
                .build();
        player.addAnalyticsListener(new EventLogger(trackSelector));
        player.setAudioAttributes(AudioAttributes.DEFAULT, /* handleAudioFocus= */ true);
        player.setPlayWhenReady(true);

        mPlayview.setPlayer(player);
        mPlayview.setPlaybackPreparer(this);
    }

    public void playStreambyUri(Uri stream_uri) {
        Log.e(TAG, "playStreambyUri" + stream_uri.toString());
        MediaSource[] mediaSources = new MediaSource[1];
        mediaSources[0] = new DashMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(stream_uri));
        mMediaSource = new ConcatenatingMediaSource(mediaSources);
        player.setMediaSource(mMediaSource);
        player.prepare();
        return;
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

        FrameLayout layout = mPlayview.getOverlayFrameLayout();
        return layout != null && view.getId() == layout.getId();

    }

    @Override
    public void onClick(View view) {
        Log.e(TAG, "onClick");
        Uri selected_uri;
        switch (view.getId()) {
            default:
                break;
        }
    }

    public void onStart() {
    }

    public void onResume() {
    }

    public void onPause() {
    }

    public void onStop() {
    }

    public void onDestroy() {
    }
}
