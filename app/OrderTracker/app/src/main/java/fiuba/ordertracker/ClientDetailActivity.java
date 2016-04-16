package fiuba.ordertracker;

import android.content.Intent;
import android.graphics.Camera;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ClientDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private String clientID;
    private String clientName;
    private Double clientLatitude;
    private Double clientLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.client_detail_toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        Intent i = getIntent();

        //TextView name = (TextView) findViewById(R.id.client_name);
        TextView clientId = (TextView) findViewById(R.id.client_code);
        TextView address = (TextView) findViewById(R.id.client_address);
        TextView telephone = (TextView) findViewById(R.id.client_telephone);
        TextView distance = (TextView) findViewById(R.id.client_distance);

        // Set data from intent
        this.clientID = i.getStringExtra("clientID");
        this.clientName = i.getStringExtra("name");

        collapsingToolbar.setTitle(this.clientName);
        clientId.setText(i.getStringExtra("clientCode"));
        address.setText(i.getStringExtra("address"));
        telephone.setText(i.getStringExtra("telephone"));
        distance.setText("a " + i.getStringExtra("distance") + " Km.");

        // Set latitude and longitude from intent
        this.clientLatitude = Double.valueOf(i.getStringExtra("latitude"));
        this.clientLongitude = Double.valueOf(i.getStringExtra("longitude"));

        /**
         * Map to show client address
         */
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.client_map);
        mapFragment.getMapAsync(this);


        /*RelativeLayout viewOrdersLayout = (RelativeLayout) findViewById(R.id.viewOrdersLayout);
        viewOrdersLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("**** View client orders ****");
            }
        });*/
    }

    //Call when the user clicks the button
    public void onClickShoppingCart(View view){
        Intent intent = new Intent(view.getContext(), TabActivity.class);
        view.getContext().startActivity(intent);
    }

    // Call when the user clicks the go map button
    public void onClickGoMap(View view) {
        Intent intent = new Intent(view.getContext(), ClientsMapActivity.class);

        Bundle b = new Bundle();
        b.putString("clientID", this.clientID);
        intent.putExtras(b);

        view.getContext().startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap map) {

        // Client address
        LatLng map_client_address = new LatLng(this.clientLatitude, this.clientLongitude);
        String map_client_name = this.clientName;

        // Map settings
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //map.setTrafficEnabled(true);
        map.setIndoorEnabled(true);
        map.setBuildingsEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);

        // Client marker
        MarkerOptions marker = new MarkerOptions();
        marker.position(map_client_address);
        marker.flat(true);
        marker.title(map_client_name);

        // Add marker to map
        map.addMarker(marker);

        // Positioned camera to focus the marker
        CameraUpdate cu = CameraUpdateFactory.newLatLng(map_client_address);
        map.animateCamera(cu);
    }
}
