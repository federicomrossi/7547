package fiuba.ordertracker;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.content.Intent;

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

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

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
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        ClientService cs = ClientService.getInstance();

        Intent intent = getIntent();

        SharedPreferences pref = getApplicationContext().getSharedPreferences("OrderTrackerPref", 0);
        int idVendedor = pref.getInt("id", 0);


        // Create a call instance for looking up Retrofit contributors.
        String orderBy = intent.getStringExtra("orderBy") != null ? intent.getStringExtra("orderBy") : "razon_social";
        //Call<List<Client>> call = cs.clients.Clients(null,null,orderBy,null);
        Call<List<Client>> call = cs.clients.Clients(Integer.toString(idVendedor),null,orderBy,null);
        //Call<List<Client>> call = cs.clientsFromTodayByVendIdService.ClientsFromTodayByVendIdService(idVendedor,orderBy,null);

        final ClientListActivity self_ = this;
        call.enqueue(new Callback<List<Client>>() {
            @Override
            public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                // Get result Repo from response.body()
                List<Client> listClients = response.body();
                clientListAdapter = new ClientListAdapter(self_, listClients);
                progressBar.setVisibility(View.GONE);

                if(listClients.size() == 0) {
                    TextView textNoClients = (TextView) findViewById(R.id.text_no_clients);
                    textNoClients.setVisibility(View.VISIBLE);
                }

                recyclerView.setAdapter(clientListAdapter);
            }

            @Override
            public void onFailure(Call<List<Client>> call, Throwable t) {
                //Aca tenemos que agregar el msj de error a mostrar... puto el que lee
                TextView textNoClients = (TextView) findViewById(R.id.text_no_clients);
                textNoClients.setText("Hubo un error al cargar los clientes por favor reintente mas tarde");
                textNoClients.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);

        /*final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));*/

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        /*switch(id) {

            case R.id.action_filter:
                toolbar_filter();
                break;

            case R.id.action_search:
                break;
        }*/

        return super.onOptionsItemSelected(item);
    }

    private void toolbar_filter() {

    }

    public void onClickShowHideFilters(View view) {

        LinearLayout button_filter = (LinearLayout) findViewById(R.id.filters_container);

        if(button_filter.getVisibility() == View.GONE)
            button_filter.setVisibility(View.VISIBLE);
        else {
            button_filter.setVisibility(View.GONE);

            EditText editText_brand = (EditText) findViewById(R.id.editText_brand);
            editText_brand.clearFocus();

            EditText editText_client_code = (EditText) findViewById(R.id.editText_client_code);
            editText_client_code.clearFocus();
        }
    }

    // When the user clicks the "Ver carrito" button
    public void onClickViewShoppingCart(View view){
        System.out.println("**** View shopping cart ****");
    }
}
