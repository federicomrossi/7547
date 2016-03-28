package com.example.scampa.starbuzzapp;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by scampa on 24/3/2016.
 * Representa la Activity del catálogo de productos
 */
public class CatalogueActivity extends ListActivity {
    // Filtro de busqueda
    EditText inputProductSearch;

    // ListView Adapter
    ArrayAdapter<CatalogueItem> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogue_items_list);
        ListView listCatalogueItems = getListView();
        listCatalogueItems.setBackgroundColor(Color.rgb(172,225,175)); // @color/mainListOptions
        inputProductSearch = (EditText) findViewById(R.id.inputSearch); // id del search input en activity_catalogue_items_list.xml


        listAdapter = new ArrayAdapter<CatalogueItem>(this, android.R.layout.simple_list_item_1,
                CatalogueItem.catalogueItems);
        listCatalogueItems.setAdapter(listAdapter);

        // Listener del filtro de busqueda
        inputProductSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // Busca matches con la primera letra
                CatalogueActivity.this.listAdapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

    }

    /**
     * Metodo que se dispara al hacer click sobre un elemento del catálogo
     */
    @Override
    public void onListItemClick(ListView listView, View itemView, int position, long id) {
        Intent intent = new Intent(CatalogueActivity.this, CatalogueItemActivity.class);

        int originalArrayIndex = CatalogueItem.itemPosition(listAdapter.getItem(position));
        //intent.putExtra(CatalogueItemActivity.ITEM_ID, (int) id); // id es la posicion en el array original
        // TODO: no vamos a usar un array, dependiendo de la estructura hay que ver como tomar el elemento de la lista filtrada
        intent.putExtra(CatalogueItemActivity.ITEM_ID, (int) originalArrayIndex);

        startActivity(intent);
    }

}

