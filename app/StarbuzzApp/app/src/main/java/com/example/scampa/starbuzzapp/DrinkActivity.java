package com.example.scampa.starbuzzapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by scampa on 24/3/2016.
 */
public class DrinkActivity extends Activity {

    public static final String DRINK_ID = "drinkId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        int drinkId = (Integer) getIntent().getExtras().get(DRINK_ID);
        Drink drink = Drink.drinkItems[drinkId];

        // Setear la imagen de la bebida con el id del activity_drink.xml
        ImageView photo = (ImageView) findViewById(R.id.photo);
        photo.setImageResource(drink.getImageResourceId());
        photo.setContentDescription(drink.getName());

        // Setear el nombre de la bebida con el id del activity_drink.xml
        TextView name = (TextView) findViewById(R.id.name);
        name.setText(drink.getName());

        // Setear la descripcion de la bebida con el id del activity_drink.xml
        TextView description = (TextView) findViewById(R.id.description);
        description.setText(drink.getDescription());
    }
}
