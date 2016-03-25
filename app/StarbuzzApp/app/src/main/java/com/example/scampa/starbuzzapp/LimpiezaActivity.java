package com.example.scampa.starbuzzapp;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class LimpiezaActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView listArticles = getListView();
        ArrayList<String> articles = new ArrayList<String>();
        articles.add("jabon");
        articles.add("jabon2");
        articles.add("jabon3");
        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                articles);
        listArticles.setAdapter(listAdapter);
    }

}
