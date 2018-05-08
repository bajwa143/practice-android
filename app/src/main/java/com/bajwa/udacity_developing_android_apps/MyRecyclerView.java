package com.bajwa.udacity_developing_android_apps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bajwa.R;

/*
If your app needs to display a scrolling list of elements based on large data sets
(or data that frequently changes), you should use RecyclerView
The RecyclerView widget is a more advanced and flexible version of ListView.
The RecyclerView fills itself with views provided by a layout manager that you provide.
You can use one of our standard layout managers (such as LinearLayoutManager or GridLayoutManager), or implement your own.
*/


public class MyRecyclerView extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.udacity_dap_activity_my_recycler_view);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //String array that will be passed to Adapter
        String[] data= new String[100];
        for(int i = 0;i<data.length;i++){
            data[i]= String.valueOf(i);
        }
        mAdapter = new RecyclerViewAdapter(data);
        mRecyclerView.setAdapter(mAdapter);


    }
}
