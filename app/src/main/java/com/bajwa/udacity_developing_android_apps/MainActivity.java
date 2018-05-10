package com.bajwa.udacity_developing_android_apps;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

// Using ListActivity class
/*
An activity that displays a list of items by binding to a data source such as an array or Cursor,
and exposes event handlers when the user selects an item.
ListActivity hosts a ListView object that can be bound to different data sources, typically either an array or
a Cursor holding query results
 */
public class MainActivity extends ListActivity {

    String[] classes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.udacity_dap_activity_main);       //No need to setContent view when we are using ListActivity
        classes = new String[]{"GitHubRepSearch", "MyRecyclerView", "ImplicitIntents"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, classes);

        // Set list acapter
        setListAdapter(adapter);

    }


    // Now we will override onlistItemClick() method to implement on Click method of list
    // Here we can't user list.setOnItemClickListener() because we don't have list object directly
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        try {
            Class cls = Class.forName("com.bajwa.udacity_developing_android_apps." + classes[position]);
            Intent intent = new Intent(MainActivity.this, cls);
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
