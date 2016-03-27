package com.example.scampa.starbuzzapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by scampa on 24/3/2016.
 */
public class FoodCategoryActivity extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView listFoods = getListView();
        ArrayAdapter<Food> listAdapter = new ArrayAdapter<Food>(this, android.R.layout.simple_list_item_1,
                Food.foodItems);
        listFoods.setAdapter(listAdapter);
    }

    /**
     * Metodo que se dispara al hacer click sobre un elemento de la lista de comidas
     */
    @Override
    public void onListItemClick(ListView listView, View itemView, int position, long id) {
        Intent intent = new Intent(FoodCategoryActivity.this, FoodActivity.class);
        intent.putExtra(FoodActivity.FOOD_ID, (int) id);
        startActivity(intent);
    }
}
