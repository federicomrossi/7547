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
import android.widget.TextView;

import java.util.List;

import fiuba.ordertracker.pojo.Categorie;
import fiuba.ordertracker.services.CategorieService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by scampa on 1/4/2016.
 */
public class ProductCategoryListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ProductCategoryListAdapter productCategoryListAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_category_list);

        // Toolbar
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle(getString(R.string.activity_product_category_list));

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        setProgressBarIndeterminateVisibility(true);

        // Categories list
        recyclerView = (RecyclerView) findViewById(R.id.productsCategoriesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        CategorieService cs = CategorieService.getInstance();

        // Create a call instance for looking up Retrofit contributors.
        Call<List<Categorie>> call = cs.categories.Categories(null,null,null);

        final ProductCategoryListActivity self_ = this;
        call.enqueue(new Callback<List<Categorie>>() {
            @Override
            public void onResponse(Call<List<Categorie>> call, Response<List<Categorie>> response) {
                // Get result Repo from response.body()
                List<Categorie> listCategories = response.body();
                productCategoryListAdapter = new ProductCategoryListAdapter(self_, listCategories);
                progressBar.setVisibility(View.GONE);

                if(listCategories.size() == 0) {
                    TextView textNoCategories = (TextView) findViewById(R.id.text_no_categories);
                    textNoCategories.setVisibility(View.VISIBLE);
                }

                recyclerView.setAdapter(productCategoryListAdapter);
            }

            @Override
            public void onFailure(Call<List<Categorie>> call, Throwable t) {
                //Aca tenemos que agregar el msj de error a mostrar... puto el que lee
                progressBar.setVisibility(View.GONE);
                TextView textNoCategories = (TextView) findViewById(R.id.text_no_categories);
                textNoCategories.setText("Hubo un error al cargar las categorias por favor reintente mas tarde");
                textNoCategories.setVisibility(View.VISIBLE);
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


}
