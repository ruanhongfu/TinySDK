package com.example.tinysdk;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackPreparer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.StyledPlayerControlView;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;

public class ExoPlayerWrapper implements
        View.OnClickListener,
        View.OnTouchListener,
        Player.EventListener,
        StyledPlayerControlView.VisibilityListener,
        PlaybackPreparer {

    private static final String TAG = "tinysdk";

    private Context mContext;
    private SimpleExoPlayer player;
    private StyledPlayerView mPlayview;
    private MediaSource mMediaSource;
    private DataSource.Factory dataSourceFactory;

    public ExoPlayerWrapper(Context context) {
        mContext = context;
    }

    //public APIs opening to upper player
    public void onCreate(Bundle savedInstanceState, StyledPlayerView playerView) {
        mPlayview = playerView;
        mPlayview.setOnTouchListener(this);
        mPlayview.requestFocus();

        InitializePlayer();

        mPlayview.setPlayer(player);
        mPlayview.setPlaybackPreparer(this);
    }

    public void onStart() {
        Log.e(TAG, "onStart");
    }

    public void onPause() {
        Log.e(TAG, "onPause");
        if(player!=null) {
            player.setPlayWhenReady(false);
        }
    }

    public void onResume() {
        Log.e(TAG, "onResume");
        if(player!=null) {
            player.setPlayWhenReady(true);
        }
    }

    public void onStop() {
        Log.e(TAG, "onStop");
        if(player!=null)
            player.stop(true);
    }

    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        if(player!=null) {
            player.stop(true);
            player.release();
            player = null;
        }
    }

    public void playStreambyUri(Uri stream_uri) {
        if(player == null)  return;

        Log.e(TAG, "playStreambyUri: " + stream_uri.toString());
        if(stream_uri == null) {
            Log.e(TAG, "empty stream uri, skip...");
            return;
        }
        //only play one stream each time
        MediaSource[] mediaSources = new MediaSource[1];
        mediaSources[0] = new DashMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(stream_uri));
        mMediaSource = new ConcatenatingMediaSource(mediaSources);
        player.setMediaSource(mMediaSource);
        player.prepare();
    }

    //override APIs for more control over simpleExoPlayer
    @Override
    public void onVisibilityChange(int visibility) {
        Log.e(TAG, "onVisibilityChange");
    }

    @Override
    public void preparePlayback() {
        Log.e(TAG, "preparePlayback");
    }

    //for touch on player control view
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Log.e(TAG, "onTouch");

        FrameLayout layout = mPlayview.getOverlayFrameLayout();
        return layout != null && view.getId() == layout.getId();
    }

    //prepared for track selection button
    @Override
    public void onClick(View view) {
        Log.e(TAG, "onClick");
        //todo
        switch (view.getId()) {
            default:
                break;
        }
    }

    //private functions
    private void InitializePlayer() {
        Log.e(TAG, "InitializePlayer");
        if (player != null)     return;

        //prepare renderersfactory and mediasourcefactory for player
        RenderersFactory renderersFactory = new DefaultRenderersFactory(mContext);
        dataSourceFactory = new DefaultDataSourceFactory(mContext,
                Util.getUserAgent(mContext, null), new DefaultBandwidthMeter.Builder(mContext).build());
        MediaSourceFactory mediaSourceFactory = new DefaultMediaSourceFactory(dataSourceFactory);

        if(renderersFactory!=null && mediaSourceFactory!=null) {
            DefaultTrackSelector trackSelector = new DefaultTrackSelector(mContext);
            player = new SimpleExoPlayer.Builder(mContext, renderersFactory)
                    .setMediaSourceFactory(mediaSourceFactory)
                    .setTrackSelector(trackSelector)
                    .build();

            player.setPlayWhenReady(true);
        }
    }
}
