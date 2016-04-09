package fiuba.ordertracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.view.View.OnKeyListener;

import java.util.List;

import fiuba.ordertracker.helpers.Constants;
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
    private  Intent intent ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        intent = getIntent();
        // Toolbar
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        final SearchView searchView = (SearchView)findViewById(R.id.searchView);
        final EditText marcaFilterView = (EditText)findViewById(R.id.editText_brand);
        this.changeSearchViewTextColorBlack(searchView);
        this.changeSearchViewTextColorBlack(marcaFilterView);
        setFiltersValues(searchView, marcaFilterView);
        setSupportActionBar(toolbar);
        String toolbarSubtitle = this.getToolbarSubtitle();
        getSupportActionBar().setSubtitle(toolbarSubtitle);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        setProgressBarIndeterminateVisibility(true);

        // Clients list
        recyclerView = (RecyclerView) findViewById(R.id.productsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        ProductService ps = ProductService.getInstance();

        // Create a call instance for looking up Retrofit contributors.

        Call<List<Product>> call = ps.products.Products(intent.getStringExtra("category"), null, null, intent.getStringExtra("nameFilter"), null, intent.getStringExtra("marca"), "nombre", null);

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
                TextView textNoProducts = (TextView) findViewById(R.id.text_no_products);
                textNoProducts.setText("Hubo un error al cargar los productos por favor reintente mas tarde");
                textNoProducts.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != "") {
                    setIntentsInFilters(searchView,marcaFilterView);
                }
                return false;
            }

        });

        marcaFilterView.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    setIntentsInFilters(searchView,marcaFilterView);
                    return true;
                }
                return false;
            }
        });

        marcaFilterView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_ACTION_DONE) || ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_DOWN ))){
                   setIntentsInFilters(searchView,marcaFilterView);
                    return true;
                }
                else{
                    return false;
                }

            }
        });

    }

    private void setFiltersValues(SearchView searchView, EditText marcaFilterView) {
        if(intent.getStringExtra("nameFilter") != null)
        {
            searchView.setQuery(intent.getStringExtra("nameFilter"), false);
            searchView.setQueryHint(intent.getStringExtra("nameFilter"));
            searchView.setHovered(true);
            searchView.setSelected(true);
        }
        if(intent.getStringExtra("marca") != null )
        {
            marcaFilterView.setText(intent.getStringExtra("marca"));
            View filtersContainer = findViewById(R.id.filters_container);
            filtersContainer.setVisibility(View.VISIBLE);
        }
    }

    protected void setIntentsInFilters(SearchView searchView, EditText marcaFilterView)
    {
        Intent newIntent = new Intent(searchView.getContext(), ProductListActivity.class);
        if (!searchView.getQuery().toString().equals("")) {
            newIntent.putExtra("nameFilter", searchView.getQuery().toString());
        }
        if (!marcaFilterView.getText().toString().equals("")) {
            newIntent.putExtra("marca", marcaFilterView.getText().toString());
        }
        newIntent.putExtra("category", intent.getStringExtra("category"));
        searchView.getContext().startActivity(newIntent);
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

    public void onClickShowHideFilters(View view) {

        LinearLayout button_filter = (LinearLayout) findViewById(R.id.filters_container);

        if(button_filter.getVisibility() == View.GONE)
            button_filter.setVisibility(View.VISIBLE);
        else {
            button_filter.setVisibility(View.GONE);
        }
    }

    private void changeSearchViewTextColorBlack(View view) {
        if (view != null) {
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(Constants.COLOR_TEXT_FILTER);
                ((TextView) view).setHintTextColor(Constants.COLOR_HINT_FILTER);
                ((TextView) view).setCursorVisible(true);
                return;
            } else if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    changeSearchViewTextColorBlack(viewGroup.getChildAt(i));
                }
            }
        }
    }

    private String getToolbarSubtitle()
    {
        String subtitle =  getString(R.string.activity_product_list) + " filtrado por ";
        if(intent.getStringExtra("nameFilter") != null)
        {
            subtitle += "nombre: " + intent.getStringExtra("nameFilter");
        } else if(intent.getStringExtra("categoryName") != null) {
            subtitle += "categoria:  " + intent.getStringExtra("categoryName");
        }
        return subtitle;
    }
}
