package com.example.scampa.starbuzzapp;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.scampa.starbuzzapp.pojo.Cliente;
import com.example.scampa.starbuzzapp.services.ClienteService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LimpiezaActivity extends ListActivity {


    private List<Cliente> listClientes = new ArrayList<Cliente>();;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ListView listClientesView = getListView();
        /*
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }*/

        ClienteService cs = new ClienteService();

        // Create a call instance for looking up Retrofit contributors.
        Call<List<Cliente>> call = cs.clienteByVendIdService.clienteByVendIdService(1);

        final LimpiezaActivity self_ = this;
        call.enqueue(new Callback<List<Cliente>>(){
            @Override
            public void onResponse(Call<List<Cliente>> call,Response<List<Cliente>> response) {
                // Get result Repo from response.body()
                listClientes = response.body();
                Cliente cl = new Cliente();
                cl.setApenom("pepe");
                cl.setDireccion("dire");
                listClientes.add(cl);
                listClientes.add(cl);
                listClientes.add(cl);

                ArrayAdapter<Cliente> listAdapter = new ArrayAdapter<Cliente>(self_, android.R.layout.simple_list_item_1,
                        listClientes);
                listClientesView.setAdapter(listAdapter);
            }

            @Override
            public void onFailure(Call<List<Cliente>> call, Throwable t) {

            }
        });

        // Fetch and print a list of the contributors to the library.
        /*
        try {
            listClientes = call.execute().body();
        }catch (IOException e){
            e.printStackTrace();
        }

        */

    }

}
