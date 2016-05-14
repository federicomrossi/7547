package fiuba.ordertracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

import fiuba.ordertracker.pojo.Order;
import fiuba.ordertracker.services.OrderService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private String clientID;
    private String clientName;
    private Double clientLatitude;
    private Double clientLongitude;
    private String agendaDate = null;
    private boolean pedidoActivoConfirmado = false;

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

        this.agendaDate = i.getStringExtra("agendaDate");

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
    public void onClickShoppingCart(final View view){
        final ArrayList<String> info = new ArrayList<String>();
        info.add(this.clientID);
        info.add(this.clientName);

        final Intent intent = new Intent(view.getContext(), TabActivity.class);
        intent.putExtra("clientName", this.clientName);
        intent.putExtra("clientID", this.clientID);
        intent.putExtra("agendaDate", this.agendaDate);

        final OrderService os = OrderService.getInstance();
        Integer clientIntID = new Integer(this.clientID);

        Call<Order> call = os.order.getActiveProductOrderByClient(clientIntID.intValue());

        final ClientDetailActivity self_ = this;
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                System.out.println("****************** onResponse TabActivity *********************");
                Order activeOrder;
                //progressBar.setVisibility(View.GONE);
                if (response.code() == 500) {
                    //no tiene pedido activo... entonces creo uno

                    IntentIntegrator integrator = new IntentIntegrator(self_);
                    integrator.initiateScan(info);

                } else {
                    view.getContext().startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                IntentIntegrator integrator = new IntentIntegrator(self_);
                integrator.initiateScan(info);
            }
        });



        /*Intent intent = new Intent(view.getContext(), TabActivity.class);
        intent.putExtra("clientName", this.clientName);
        intent.putExtra("clientID",this.clientID);
        intent.putExtra("agendaDate",this.agendaDate);
        view.getContext().startActivity(intent);*/
    }

    /**
     * Handles the QR scan result
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            System.out.println("******* SCAN OK *******");

            // TODO Show field to add comment ?????
            System.out.println("****scanResult.getContents(): " + scanResult.getContents());

            String clientID = scanResult.getContents();
            System.out.println("*** " + clientID);

            if (this.clientID.equals(clientID)){
                Intent intent2 = new Intent(getApplicationContext(), TabActivity.class);
                intent2.putExtra("clientName", this.clientName);
                intent2.putExtra("clientID",this.clientID);
                intent2.putExtra("agendaDate",this.agendaDate);
                startActivity(intent2);
            } else{
                Toast.makeText(getApplicationContext(), "El QR no corresponde al cliente", Toast.LENGTH_LONG).show();
            }

        } else{
            System.out.println("******* SCAN NOT OK ? *******");
            Toast.makeText(getApplicationContext(), "Error de lectura de código QR", Toast.LENGTH_LONG).show();
            super.onActivityResult(requestCode, resultCode, intent);
        }
    }

    // Call when the user clicks the go map button
    public void onClickGoMap(View view) {
        Intent intent = new Intent(view.getContext(), ClientsMapActivity.class);

        Bundle b = new Bundle();
        b.putString("clientID", this.clientID);
        b.putString("agendaDate", this.agendaDate);
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
