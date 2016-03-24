package com.example.scampa.starbuzzapp;

/**
 * Created by scampa on 24/3/2016.
 */
public class Food {
    private String name;
    private String description;
    private int imageResourceId;

    //Food es un array de comidas
    public static final Food[] foodItems = {
            new Food("Pizza", "Pan plano horneado elaborado con harina de trigo, sal, agua y levadura, cubierto con salsa de tomate.",
                    R.drawable.pizza),
            new Food("Sandwich", "Preparación que se realiza intercalando dos rodajas de pan con variados ingredientes",
                    R.drawable.sandwich),
            new Food("Pancho", "Salchicha servida en un pan que suele acompañarse con algún aderezo como salsa de tomate y mostaza.",
                    R.drawable.pancho)
    };

    // Cada comida tiene nombre, descripción e imagen
    private Food(String name, String description, int imageResourceId) {
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
