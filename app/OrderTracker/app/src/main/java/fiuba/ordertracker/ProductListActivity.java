package fiuba.ordertracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import fiuba.ordertracker.pojo.Product;
import fiuba.ordertracker.services.ProductService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ProductListAdapter productListAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        final Intent intent = getIntent();

        // Toolbar
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle(getString(R.string.activity_product_list) + " de " + intent.getStringExtra("categoryName"));

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        setProgressBarIndeterminateVisibility(true);

        // Clients list
        recyclerView = (RecyclerView) findViewById(R.id.productsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ProductService ps = ProductService.getInstance();

        Call<List<Product>> call = ps.products.Products(intent.getStringExtra("category"), null, null, null, null, null, "nombre", null);

        final ProductListActivity self_ = this;
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                // Get result Repo from response.body()
                List<Product> listProducts = response.body();
                productListAdapter = new ProductListAdapter(self_, listProducts);
                productListAdapter.setCategory(intent.getStringExtra("categoryName"));
                progressBar.setVisibility(View.GONE);

                if(listProducts.size() == 0) {
                    TextView textNoProducts = (TextView) findViewById(R.id.text_no_products);
                    textNoProducts.setVisibility(View.VISIBLE);
                }

                recyclerView.setAdapter(productListAdapter);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                //Aca tenemos que agregar el msj de error a mostrar... puto el que lee
                TextView textNoProducts = (TextView) findViewById(R.id.text_no_products);
                textNoProducts.setText("Hubo un error al cargar los productos por favor reintente mas tarde");
                textNoProducts.setVisibility(View.VISIBLE);
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

            /*EditText editText_brand = (EditText) findViewById(R.id.editText_brand);
            editText_brand.clearFocus();

            EditText editText_client_code = (EditText) findViewById(R.id.editText_client_code);
            editText_client_code.clearFocus();*/
        }
    }
}
