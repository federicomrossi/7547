package com.example.scampa.starbuzzapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by scampa on 24/3/2016.
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Crea un OnItemClickListener
        AdapterView.OnItemClickListener itemClickListener =
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> listView, View v, int position, long id) {
                        if (position == 0) {
                            Intent intent = new Intent(MainActivity.this, CatalogueActivity.class);
                            startActivity(intent);
                        }
                        /*if (position == 1) {
                            Intent intent = new Intent(MainActivity.this, FoodCategoryActivity.class);
                            startActivity(intent);
                        }*/
                    }
                };

        // Agrega el listener a la listView
        ListView listView = (ListView) findViewById(R.id.list_options);
        listView.setOnItemClickListener(itemClickListener);

    }
}
