package fiuba.ordertracker.services;

import java.io.IOException;
import java.util.List;

import fiuba.ordertracker.helpers.Constants;
import fiuba.ordertracker.pojo.Categorie;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by pablo on 26/3/2016.
 */
public final class CategorieService {
    public Categories  categories;
    private static CategorieService instance = null;

    private CategorieService(){
        // Create a very simple REST adapter which points the GitHub API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL_SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an instance of our GitHub API interface.
        categories = retrofit.create(Categories.class);
    }

    public static synchronized CategorieService getInstance() {
        if (instance == null) {
            instance = new CategorieService();
        }

        return instance;
    }

    //categorias
    public interface Categories {
        @GET("categories")
        Call<List<Categorie>> Categories(@Query("nombre%") String nombre, @Query("orderby") String orderby, @Query("orientation") String orientation);
    }



    public static void main(String... args) throws IOException {
        CategorieService cats = CategorieService.getInstance();

        // Create a call instance for looking up Retrofit contributors.
        Call<List<Categorie>> call = cats.categories.Categories(null,null,null);

        // Fetch and print a list of the contributors to the library.
        List<Categorie> clientList = call.execute().body();
        for (Categorie client : clientList) {
            System.out.println(client.getDescripcion() + " (" + client.getId() + ")");
        }

    }
}