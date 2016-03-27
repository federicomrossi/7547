package com.example.ezequiel.ordertrack.services;

import com.example.ezequiel.ordertrack.pojo.Categorie;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by pablo on 26/3/2016.
 */
public final class CategorieService {
    public static final String API_URL = "http://ec2-54-207-103-117.sa-east-1.compute.amazonaws.com/";
    public Categories  categories;
    private static CategorieService instance = null;

    private CategorieService(){
        // Create a very simple REST adapter which points the GitHub API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
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
        Call<List<Categorie>> Categories();
    }



    public static void main(String... args) throws IOException {
        CategorieService cats = CategorieService.getInstance();

        // Create a call instance for looking up Retrofit contributors.
        Call<List<Categorie>> call = cats.categories.Categories();

        // Fetch and print a list of the contributors to the library.
        List<Categorie> clientList = call.execute().body();
        for (Categorie client : clientList) {
            System.out.println(client.getDescripcion() + " (" + client.getId() + ")");
        }

    }
}