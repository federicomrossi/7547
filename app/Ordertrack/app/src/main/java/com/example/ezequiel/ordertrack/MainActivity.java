package com.example.ezequiel.ordertrack;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Crea un OnItemClickListener
        AdapterView.OnItemClickListener itemClickListener =
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> listView, View v, int position, long id) {
                        if (position == 0) {
                            /*Intent intent = new Intent(TopLevelActivity.this, DrinkCategoryActivity.class);
                            startActivity(intent);*/
                        }
                        else if (position == 1) {
                            /*Intent intent = new Intent(TopLevelActivity.this, FoodCategoryActivity.class);
                            startActivity(intent);*/
                        }
                    }
                };

        // Agrega el listener a la listView
        ListView listView = (ListView) findViewById(R.id.main_menu_list);
        listView.setOnItemClickListener(itemClickListener);

    }

}
