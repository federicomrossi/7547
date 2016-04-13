package fiuba.ordertracker;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import fiuba.ordertracker.pojo.Client;
import fiuba.ordertracker.services.ClientService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientsMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ClientService clientService = ClientService.getInstance();
    private String focusInClient = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clients_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle b = getIntent().getExtras();
        this.focusInClient = b.getString("clientID");
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

        // Load the clients in the map
        this.loadClientsInMap(googleMap);

        mMap.setIndoorEnabled(true);
        mMap.setBuildingsEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }

    private void loadClientsInMap(GoogleMap googleMap) {

        Call<List<Client>> call = clientService.clients.Clients(null,null,null,null);
        final ClientsMapActivity _self = this;
        final GoogleMap _googleMap = googleMap;

        call.enqueue(new Callback<List<Client>>() {

            @Override
            public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {

                // Get result Repo from response.body()
                List<Client> listClients = response.body();

                for (Client client : listClients) {
                    LatLng client_position = new LatLng(client.getLatitude(), client.getLongitude());
                    String client_name = client.getApenom();

                    // Client marker
                    MarkerOptions marker = new MarkerOptions();
                    marker.position(client_position);
                    marker.flat(true);
                    marker.title(client_name);

                    _googleMap.addMarker(marker).showInfoWindow();

                    // If theres a client specified to be focused, we positioned the camera
                    // over the marker.
                    if(client.getId().contentEquals(_self.focusInClient)) {
                        _googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(client_position, 15));
                        _googleMap.animateCamera(CameraUpdateFactory.zoomIn());
                        _googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Client>> call, Throwable t) {
                /*//Aca tenemos que agregar el msj de error a mostrar... puto el que lee
                TextView textNoClients = (TextView) findViewById(R.id.text_no_clients);
                textNoClients.setText("Hubo un error al cargar los clientes por favor reintente mas tarde");
                textNoClients.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);*/
            }
        });
    }

}
