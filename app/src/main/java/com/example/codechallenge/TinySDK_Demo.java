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
    private HashMap<String, List<String>> StreamList;
    private List<String> StreamListTitle;
    SampleGroupListAdapter sampleGroupListAdapter;
    ExpandableListView streamListView;
    Context mContext = this;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiny_s_d_k__demo);

        streamListView = findViewById(R.id.sample_list);
        StreamList = SampleGroupList.getData(mContext, streamlistfile);
        StreamListTitle = new ArrayList<String>(StreamList.keySet());
        sampleGroupListAdapter = new SampleGroupListAdapter(this, StreamListTitle, StreamList);
        streamListView.setAdapter(sampleGroupListAdapter);
        streamListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
            }
        });

        streamListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });

        streamListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Log.e(TAG, "click on: "+ SampleGroupList.GetSampleUri(childPosition));
                Intent intent = new Intent(mContext, playeractivity.class);
                intent.putExtra(TEST_STREAM_URI, SampleGroupList.GetSampleUri(childPosition));
                startActivity(intent);
                return false;
            }
        });
    }
}
