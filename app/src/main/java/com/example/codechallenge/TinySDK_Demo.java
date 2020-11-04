package com.example.codechallenge;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.exoplayer2.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TinySDK_Demo extends AppCompatActivity {

    private static final String TAG = "demo";
    private static final String streamlistfile = "media.exolist.json";
    public static final String TEST_STREAM_URI = "test_stream_url_info";
    private Context mContext = this;

    //for sample list
    private SampleGroupListAdapter sampleGroupListAdapter;
    private ExpandableListView streamListView;
    private HashMap<String, List<String>> StreamList;
    private List<String> StreamListTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tiny_s_d_k__demo);

        //prepare sample list and listener, which starts player activity
        streamListView = findViewById(R.id.sample_list);
        StreamList = SampleGroupList.LoadStreamSamples(mContext, streamlistfile);
        StreamListTitle = new ArrayList<String>(StreamList.keySet());
        sampleGroupListAdapter = new SampleGroupListAdapter(this, StreamListTitle, StreamList);
        streamListView.setAdapter(sampleGroupListAdapter);

        streamListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Log.i(TAG, "click on: group "+ groupPosition
                        +" child "+ childPosition
                        +" uri: "+ SampleGroupList.GetSampleUri(groupPosition, childPosition));
                Intent intent = new Intent(mContext, playeractivity.class);

                intent.putExtra(TEST_STREAM_URI, SampleGroupList.GetSampleUri(groupPosition, childPosition));
                startActivity(intent);
                return false;
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG,"onSaveInstanceState");
        super.onSaveInstanceState(outState);
        //todo
    }

    //below APIs seem not necessary
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onActivityStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onActivityResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onActivityPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onActivityStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onActivityDestroy");
    }
}
