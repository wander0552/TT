package com.wander.tt.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.wander.tt.R;

public class Main2Activity extends AppCompatActivity {

    private ListView mListView;
    private MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mListView = (ListView) findViewById(R.id.listView);
        adapter = new MainAdapter(this);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                adapter.setCurrentPosition(position);
                adapter.notifyDataSetChanged();
            }
        });

    }
}
