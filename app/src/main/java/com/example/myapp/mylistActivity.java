package com.example.myapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class mylistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylist);

        ListView listView = findViewById(R.id.mylist);
        String data[] ={"111","222"};

        ListAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,data);
        listView.setAdapter(adapter);

    }
}
