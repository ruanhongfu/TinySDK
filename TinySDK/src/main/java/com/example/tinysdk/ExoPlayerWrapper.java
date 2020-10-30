package com.example.tinysdk;

import android.content.Context;

import com.google.android.exoplayer2.SimpleExoPlayer;

public class ExoPlayerWrapper {
    private SimpleExoPlayer player;
    protected ExoPlayerWrapper(Context context) {
        player = new SimpleExoPlayer.Builder(context).build();
    }
}
