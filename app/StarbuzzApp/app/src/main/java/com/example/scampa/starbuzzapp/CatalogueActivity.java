package com.example.scampa.starbuzzapp;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by scampa on 24/3/2016.
 * Representa la Activity del catálogo de productos
 */
public class CatalogueActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView listCatalogueItems = getListView();
        listCatalogueItems.setBackgroundColor(Color.rgb(172,225,175)); // @color/mainListOptions
        ArrayAdapter<CatalogueItem> listAdapter = new ArrayAdapter<CatalogueItem>(this, android.R.layout.simple_list_item_1,
                CatalogueItem.catalogueItems);
        listCatalogueItems.setAdapter(listAdapter);
    }

    /**
     * Metodo que se dispara al hacer click sobre un elemento del catálogo
     */
    @Override
    public void onListItemClick(ListView listView, View itemView, int position,long id) {
        Intent intent = new Intent(CatalogueActivity.this, CatalogueItemActivity.class);
        intent.putExtra(CatalogueItemActivity.ITEM_ID, (int) id);
        startActivity(intent);
    }

}
