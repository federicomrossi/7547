package fiuba.ordertracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ClientDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_detail);

        Intent i = getIntent();

        TextView name = (TextView) findViewById(R.id.client_name);
        TextView address = (TextView) findViewById(R.id.client_address);
        TextView distance = (TextView) findViewById(R.id.client_distance);

        // Set data from intent
        name.setText("Name: " + i.getStringExtra("name"));
        address.setText("Address: " + i.getStringExtra("address"));
        distance.setText("Distance: " + i.getStringExtra("distance"));
    }

    //Call when the user clicks the button
    public void onClickShoppingCart(View view){
        Intent intent = new Intent(view.getContext(), ProductCategoryListActivity.class);
        view.getContext().startActivity(intent);

    }

}
