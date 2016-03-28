package com.example.scampa.starbuzzapp;

/**
 * Created by scampa on 24/3/2016.
 */
public class CatalogueItem {

    private String name;
    private String description;
    private int imageResourceId;
    private String stock;

    // TODO traer de la base!!
    // catalogueItems es un array de items/productos del catálogo
    public static final CatalogueItem[] catalogueItems = {
            new CatalogueItem("Latte", "Bebida preparada con una mitad de café y otra de leche caliente",
                    R.drawable.latte, "22"),
            new CatalogueItem("Cappuccino", "Preparación a base de café y leche con canela y chocolate en polvo",
                    R.drawable.cappuccino, "63"),
            new CatalogueItem("Filtro", "Preparado con granos de café recién tostados de la más alta calidad",
                    R.drawable.filter, "79"),
            new CatalogueItem("Pizza", "Pan plano horneado elaborado con harina de trigo, sal, agua y levadura, cubierto con salsa de tomate",
                    R.drawable.pizza, "85"),
            new CatalogueItem("Sandwich", "Preparación que se realiza intercalando dos rodajas de pan con variados ingredientes",
                    R.drawable.sandwich, "132"),
            new CatalogueItem("Pancho", "Salchicha servida en un pan que suele acompañarse con algún aderezo como salsa de tomate y mostaza",
                    R.drawable.pancho, "26")
    };

    // Cada item/producto tiene nombre, descripción, imagen y stock
    private CatalogueItem(String name, String description, int imageResourceId, String stock) {
        this.name = name;
        this.description = description;
        this.imageResourceId = imageResourceId;
        this.stock = stock;
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

    public String getStock() {
        return "Stock: " + stock;
    }

    public String toString() {
        return this.name;
    }

    public static int itemPosition(CatalogueItem item){
        for (int i = 0; i < catalogueItems.length; i++){
            if (catalogueItems[i].name.equals(item.name))
                return i;
        }
        return -1;
    }
}
