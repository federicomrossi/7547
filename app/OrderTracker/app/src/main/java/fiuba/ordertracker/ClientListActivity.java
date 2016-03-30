package fiuba.ordertracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import fiuba.ordertracker.pojo.Client;
import fiuba.ordertracker.services.ClientService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ClientListAdapter clientListAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_list);

        // Toolbar
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle(getString(R.string.activity_client_list));

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        setProgressBarIndeterminateVisibility(true);

        // Clients list
        recyclerView = (RecyclerView) findViewById(R.id.clientsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ClientService cs = ClientService.getInstance();

        // Create a call instance for looking up Retrofit contributors.
        Call<List<Client>> call = cs.clients.Clients();

        final ClientListActivity self_ = this;
        call.enqueue(new Callback<List<Client>>() {
            @Override
            public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                // Get result Repo from response.body()
                List<Client> listClients = response.body();
                clientListAdapter = new ClientListAdapter(self_, listClients);
                progressBar.setVisibility(View.GONE);
                recyclerView.setAdapter(clientListAdapter);
            }

            @Override
            public void onFailure(Call<List<Client>> call, Throwable t) {
                //Aca tenemos que agregar el msj de error a mostrar... puto el que lee
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }


    /**
     * TEMP!!!
     */

    private List<Client> getData() {
        /*List<ClientModel> data = new ArrayList<>();

        String[] names = {"Sabrina Campa", "Pablo Ascarza", "Ezequier Reyes", "Federico Rossi"};
        String[] addresses = {"Av. Mitre 123, Bs. As.", "Av. Mitre 123, Bs. As.", "Av. Mitre 123, Bs. As.", "Av. Mitre 123, Bs. As."};
        String[] distances = {"1 km", "2 km", "3 km", "4 km"};

        for(int i = 0; i < names.length; i++) {
            ClientModel c = new ClientModel();
            c.name = names[i];
            c.address = addresses[i];
            c.distance = distances[i];
            data.add(c);
        }

        return data;
        */
        return null;
    }
}
