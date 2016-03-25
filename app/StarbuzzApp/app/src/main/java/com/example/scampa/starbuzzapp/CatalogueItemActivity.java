package com.example.scampa.starbuzzapp;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Size;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by scampa on 24/3/2016.
 * Activity para un item del catálogo
 */
public class CatalogueItemActivity extends Activity {

    public static final String ITEM_ID = "itemId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogue_item);

        int itemId = (Integer) getIntent().getExtras().get(ITEM_ID);
        CatalogueItem catalogueItem = CatalogueItem.catalogueItems[itemId];

        // Setea la imagen de acuerdo con el id del activity_catalogue_item.xml
        ImageView photo = (ImageView) findViewById(R.id.photo);
        photo.setImageResource(catalogueItem.getImageResourceId());
        photo.setContentDescription(catalogueItem.getName());

        // Setea el nombre de acuerdo con el id del activity_catalogue_item.xml
        TextView name = (TextView) findViewById(R.id.name);
        name.setTypeface(name.getTypeface(), Typeface.BOLD);
        name.setText(catalogueItem.getName());

        // Setea la descripcion de acuerdo con el id del activity_catalogue_item.xml
        TextView description = (TextView) findViewById(R.id.description);
        description.setText(catalogueItem.getDescription());

        // Setea el stock de acuerdo con el id del activity_catalogue_item.xml
        TextView stock = (TextView) findViewById(R.id.stock);
        stock.setText(catalogueItem.getStock());
    }
}
