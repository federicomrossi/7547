package fiuba.ordertracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import fiuba.ordertracker.helpers.Constants;

import org.w3c.dom.Text;

public class ClientDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_detail);

        Intent i = getIntent();

        TextView name = (TextView) findViewById(R.id.client_name);
        TextView address = (TextView) findViewById(R.id.client_address);
        TextView clientNumber = (TextView) findViewById(R.id.client_number);
        TextView distance = (TextView) findViewById(R.id.client_distance);

        // Set data from intent
        name.setText("Name: " + i.getStringExtra("name"));
        address.setText("Address: " + i.getStringExtra("address"));
        clientNumber.setText("#Cliente: " + i.getStringExtra("clientNumber"));
        distance.setText("Distance: " + i.getStringExtra("distance") + Constants.COMPLETE_UNIT);
    }
}
