package com.example.ezequiel.ordertrack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        ListView lw = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> listAdapter ;

        ArrayList<String> stringItems = new ArrayList<String>();
        stringItems.add("cliente1");
        stringItems.add("cliente2");
        stringItems.add("cliente3");
        listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, stringItems);
        lw.setAdapter(listAdapter);


    }
}
