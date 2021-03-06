package fiuba.ordertracker.services;

import java.io.IOException;
import java.util.List;

import fiuba.ordertracker.helpers.Constants;
import fiuba.ordertracker.pojo.Product;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by pablo on 26/3/2016.
 */

public final class ProductService {
    public Products  products;
    private static ProductService instance = null;

    private ProductService(){
        // Create a very simple REST adapter which points the GitHub API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL_SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an instance of our GitHub API interface.
        products = retrofit.create(Products.class);
    }

    public static synchronized ProductService getInstance() {
        if (instance == null) {
            instance = new ProductService();
        }

        return instance;
    }

    //productos
    public interface Products {
        @GET("products")
        //Integer: categoria, stock, precio. String: nombre, descripcion, marca
        Call<List<Product>> Products(@Query("categoria") String categoria, @Query("stock") String stock, @Query("precio") String precio,
                                     @Query("nombre%") String nombre, @Query("descripcion%") String descripcion, @Query("marca%") String marca,
                                     @Query("orderby") String orderby, @Query("orientation") String orientation);
    }



    public static void main(String... args) throws IOException {
        ProductService prods = ProductService.getInstance();

        // Create a call instance for looking up Retrofit contributors.
        Call<List<Product>> call = prods.products.Products("1", null, null, "Televisor",null,null,null,null);

        // Fetch and print a list of the contributors to the library.
        List<Product> clientList = call.execute().body();
        for (Product client : clientList) {
            //System.out.println(client.getDescripcion() + " (" + client.getId() + ")");
        }

    }
}