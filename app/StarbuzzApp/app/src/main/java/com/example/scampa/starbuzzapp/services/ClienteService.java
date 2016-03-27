package com.example.scampa.starbuzzapp.services;

import com.example.scampa.starbuzzapp.pojo.Cliente;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by pablo on 25/3/2016.
 */


public final class ClienteService {
    public static final String API_URL = "http://ec2-54-207-103-117.sa-east-1.compute.amazonaws.com/";
    public ClienteByVendIdService clienteByVendIdService;
    public ClienteService(){
        // Create a very simple REST adapter which points the GitHub API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an instance of our GitHub API interface.
        clienteByVendIdService = retrofit.create(ClienteByVendIdService.class);
    }

    public interface ClienteByVendIdService {
        @GET("client/getFromToday/{id}")
        Call<List<Cliente>> clienteByVendIdService(@Path("id") int vendId);
    }

    public static void main(String... args) throws IOException {
        ClienteService cs = new ClienteService();

        // Create a call instance for looking up Retrofit contributors.
        Call<List<Cliente>> call = cs.clienteByVendIdService.clienteByVendIdService(1);

        // Fetch and print a list of the contributors to the library.
        List<Cliente> clienteList = call.execute().body();
        for (Cliente cliente : clienteList) {
            System.out.println(cliente.getApenom() + " (" + cliente.getDireccion() + ")");
        }
    }
}