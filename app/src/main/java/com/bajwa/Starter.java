package com.bajwa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Starter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter);

        // String array for adapter
        final String[] packages = new String[]{"newboston"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Starter.this, android.R.layout.simple_list_item_1, packages);

        ListView listView = (ListView) findViewById(R.id.starter_listview);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Class cls = Class.forName("com.bajwa." + packages[position] + ".MainActivity");
                    Intent intent = new Intent(Starter.this, cls);








                    startActivity(intent);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

    }
}
