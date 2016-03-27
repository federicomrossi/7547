package com.example.scampa.starbuzzapp;

/**
 * Created by scampa on 24/3/2016.
 */
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DrinkCategoryActivity extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView listDrinks = getListView();
        ArrayAdapter<Drink> listAdapter = new ArrayAdapter<Drink>(this, android.R.layout.simple_list_item_1,
                                                                    Drink.drinkItems);
        listDrinks.setAdapter(listAdapter);
    }

    /**
     * Metodo que se dispara al hacer click sobre un elemento de la lista de bebidas
     */
    @Override
     public void onListItemClick(ListView listView, View itemView, int position,long id) {
        Intent intent = new Intent(DrinkCategoryActivity.this, DrinkActivity.class);
        intent.putExtra(DrinkActivity.DRINK_ID, (int) id);
        startActivity(intent);
    }

}
