package fiuba.ordertracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

/**
 * Created by scampa on 3/4/2016.
 */
public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.client_detail_toolbar); // TODO change for the toolbar in the product layout
        //setSupportActionBar(toolbar);
        //CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        Intent i = getIntent();

        // Set data from intent
        //collapsingToolbar.setTitle(i.getStringExtra("name"));
        //name.setText(i.getStringExtra("name"));

        /*TextView name = (TextView) findViewById(R.id.product_name);
        TextView brand = (TextView) findViewById(R.id.product_brand);
        TextView description = (TextView) findViewById(R.id.product_description);
        TextView price = (TextView) findViewById(R.id.product_price);
        TextView category = (TextView) findViewById(R.id.product_category);
        TextView availability = (TextView) findViewById(R.id.product_availability);

        // Set data from intent
        //collapsingToolbar.setTitle(i.getStringExtra("name"));

        name.setText(i.getStringExtra("name"));
        brand.setText(i.getStringExtra("brand"));
        description.setText(i.getStringExtra("description"));
        price.setText(i.getStringExtra("price"));
        category.setText(i.getStringExtra("category"));
        category.setText(i.getStringExtra("availability")); */

    }

}
