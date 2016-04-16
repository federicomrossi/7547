package fiuba.ordertracker.helpers;

import java.util.ArrayList;
import java.util.List;

import fiuba.ordertracker.pojo.Categorie;
import fiuba.ordertracker.pojo.Product;

/**
 * Created by ezequiel on 14/04/16.
 */
public class FiltersHelper {


    public static List<Categorie> filterCategoriesByName(List<Categorie> listToFilter, String filter)
    {
        if(filter.equals("")) return listToFilter;
        List<Categorie> result = new ArrayList<Categorie>();
        for (Categorie category: listToFilter) {
            String name = category.getNombre().toString();
            if (name.toLowerCase().contains((filter.toLowerCase()))) {
                result.add(category);
            }
        }
        return result;
    }

    public static List<Product> filterProductsByName(List<Product> listToFilter, String filter)
    {
        if(filter.equals("")) return listToFilter;
        List<Product> result = new ArrayList<Product>();
        for (Product product: listToFilter) {
            String name = product.getNombre().toString();
            if (name.toLowerCase().contains((filter.toLowerCase()))) {
                result.add(product);
            }
        }
        return result;
    }

    public static List<Product> filterProductsByMarca(List<Product> listToFilter, String filter)
    {
        if(filter.equals("")) return listToFilter;
        List<Product> result = new ArrayList<Product>();
        for (Product product: listToFilter) {
            String marca = product.getMarca().toString();
            if (marca.toLowerCase().contains((filter.toLowerCase()))) {
                result.add(product);
            }
        }
        return result;
    }

}
