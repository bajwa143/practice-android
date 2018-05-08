package com.bajwa.udacity_developing_android_apps;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bajwa.R;

/**
 * Created by Saqib on 5/5/2018.
 */

// When we write <RecyclerViewAdapter.ViewHolder>
// we need to make a class ViewHolder in our RecyclerViewAdapter class
// which must extends from RecyclerView.ViewHolder, otherwise it will error
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    // String array which contains data to be displayed
    String[] myData;

    // Constructor to get String[] data and initialize our myData variable
    public RecyclerViewAdapter(String[] myData) {
        this.myData = myData;
    }

    /*
        Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item.
    This new ViewHolder should be constructed with a new View that can represent the items of the given type.
    You can either create a new View manually or inflate it from an XML layout file.
    The new ViewHolder will be used to display items of the adapter using onBindViewHolder(RecyclerView.ViewHolder, int, List).
    Since it will be re-used to display different items in the data set, it is a good idea to cache references to
    sub views of the View to avoid unnecessary View.findViewById(int) calls.
         */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create Layout inflater
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // Inflate our view
        View view = inflater.inflate(R.layout.recycler_view_list_item, parent, false);
        // return ViewHolder Object by calling its constructor
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(myData[position]);

    }

    @Override
    public int getItemCount() {
        return myData.length;
    }

    // This is our ViewHolder inner class

    // From https://developer.android.com/guide/topics/ui/layout/recyclerview
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    public class ViewHolder extends RecyclerView.ViewHolder {

        // Our TextView to be displayed
        public TextView mTextView;

        // Constructor of ViewHolder class
        // In this class we will
        public ViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.tv_my_recycler_view);
        }
    }
}
