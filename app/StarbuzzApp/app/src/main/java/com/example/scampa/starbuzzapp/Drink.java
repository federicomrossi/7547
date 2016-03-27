package com.example.scampa.starbuzzapp;

/**
 * Created by scampa on 24/3/2016.
 */
public class Drink {

    private String name;
    private String description;
    private int imageResourceId;

    //Drinks es un array de bebidas
    public static final Drink[] drinkItems = {
            new Drink("Latte", "Bebida preparada con una mitad de café y otra de leche caliente.",
                    R.drawable.latte),
            new Drink("Cappuccino", "Preparación a base de café y leche con canela y chocolate en polvo",
                    R.drawable.cappuccino),
            new Drink("Filtro", "Preparado con granos de café recién tostados de la más alta calidad",
                    R.drawable.filter)
    };

    // Cada bebida tiene nombre, descripción e imagen
    private Drink(String name, String description, int imageResourceId) {
        this.name = name;
        this.description = description;
        this.imageResourceId = imageResourceId;
    }
    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String toString() {
        return this.name;
    }

}
