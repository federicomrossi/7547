package com.example.scampa.starbuzzapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by scampa on 24/3/2016.
 */
public class FoodActivity extends Activity {
    public static final String FOOD_ID = "foodId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        int foodId = (Integer) getIntent().getExtras().get(FOOD_ID);
        Food food = Food.foodItems[foodId];

        // Setear la imagen de la comida con el id del activity_food.xml
        ImageView photo = (ImageView) findViewById(R.id.photo);
        photo.setImageResource(food.getImageResourceId());
        photo.setContentDescription(food.getName());

        // Setear el nombre de la comida con el id del activity_food.xml
        TextView name = (TextView) findViewById(R.id.name);
        name.setText(food.getName());

        // Setear la descripcion de la comida con el id del activity_food.xml
        TextView description = (TextView) findViewById(R.id.description);
        description.setText(food.getDescription());
    }
}
