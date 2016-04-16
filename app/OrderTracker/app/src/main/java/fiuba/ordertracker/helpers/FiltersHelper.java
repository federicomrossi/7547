package fiuba.ordertracker.helpers;

import java.util.ArrayList;
import java.util.List;

import fiuba.ordertracker.pojo.Categorie;

/**
 * Created by ezequiel on 14/04/16.
 */
public class FiltersHelper {


    public static List<Categorie> filterCategoriesByName(List<Categorie> listToFilter, String filter)
    {

        List<Categorie> result = new ArrayList<Categorie>();
        for (Categorie category: listToFilter) {
            String name = category.getNombre().toString();
            if (name.toLowerCase().contains((filter.toLowerCase()))) {
                result.add(category);
            }
        }
        return result;
    }

}
