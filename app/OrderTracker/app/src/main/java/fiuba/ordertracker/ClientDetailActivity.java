package fiuba.ordertracker;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;

public class ClientDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.client_detail_toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        Intent i = getIntent();

        //TextView name = (TextView) findViewById(R.id.client_name);
        TextView address = (TextView) findViewById(R.id.client_address);
        TextView distance = (TextView) findViewById(R.id.client_distance);

        // Set data from intent
        collapsingToolbar.setTitle(i.getStringExtra("name"));
        //name.setText(i.getStringExtra("name"));
        address.setText(i.getStringExtra("address"));
        distance.setText(i.getStringExtra("distance"));


    }

    //Call when the user clicks the button
    public void onClickShoppingCart(View view){
        Intent intent = new Intent(view.getContext(), ProductCategoryListActivity.class);
        view.getContext().startActivity(intent);

    }
}
