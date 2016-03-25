package com.example.scampa.starbuzzapp;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by scampa on 25/3/2016.
 */
public class ClientsPortfolioActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView listCatalogueItems = getListView();
        listCatalogueItems.setBackgroundColor(Color.rgb(172, 225, 175)); // @color/mainListOptions
        ArrayAdapter<Client> listAdapter = new ArrayAdapter<Client>(this, android.R.layout.simple_list_item_1,
                Client.clients);
        listCatalogueItems.setAdapter(listAdapter);
    }

    /**
     * Metodo que se dispara al hacer click sobre un cliente
     */
    @Override
    public void onListItemClick(ListView listView, View itemView, int position,long id) {
        itemView.setBackgroundColor(Color.rgb(222, 225, 172)); //@color/selectedOptionList
        Intent intent = new Intent(ClientsPortfolioActivity.this, ClientsActivity.class);
        intent.putExtra(ClientsActivity.CLIENT_ID, (int) id);
        startActivity(intent);
    }

}
