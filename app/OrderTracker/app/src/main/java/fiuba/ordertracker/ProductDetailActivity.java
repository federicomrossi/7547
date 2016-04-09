package fiuba.ordertracker;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import fiuba.ordertracker.helpers.ImageLoadTask;

/**
 * Created by scampa on 3/4/2016.
 */
public class ProductDetailActivity extends AppCompatActivity {

    private FragmentManager fm = getSupportFragmentManager();
    private Toolbar toolbar;

    private int product_id;
    private String product_name;
    private String product_brand;
    private String product_availability;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.client_detail_toolbar); // TODO change for the toolbar in the product layout
        setSupportActionBar(toolbar);
        //CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        // Toolbar
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle("Detalle de producto");

        Intent i = getIntent();

        // Set data from intent
        //collapsingToolbar.setTitle(i.getStringExtra("name"));

        this.product_id = 1;
        this.product_availability = i.getStringExtra("availability");
        this.product_name = i.getStringExtra("name");
        this.product_brand = i.getStringExtra("brand");

        TextView name = (TextView) findViewById(R.id.product_name);
        TextView brand = (TextView) findViewById(R.id.product_brand);
        TextView description = (TextView) findViewById(R.id.product_description);
        TextView price = (TextView) findViewById(R.id.product_price);
        TextView category = (TextView) findViewById(R.id.product_category);
        TextView availability = (TextView) findViewById(R.id.product_availability);
        ImageView image = (ImageView) findViewById(R.id.detail_view);
        name.setText(i.getStringExtra("name"));
        brand.setText(i.getStringExtra("brand"));
        description.setText(i.getStringExtra("description"));
        price.setText("$ " + i.getStringExtra("price"));
        category.setText(i.getStringExtra("category"));
        availability.setText(i.getStringExtra("availability"));
        new ImageLoadTask(i.getStringExtra("url_image_normal"), image).execute();
    }

    public void addProductToCart(View view) {

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        AddProductToCartFragment newFragment = AddProductToCartFragment.newInstance(this.product_id,
                this.product_name, this.product_brand, this.product_availability);
        newFragment.show(ft, "dialog");
    }
}
