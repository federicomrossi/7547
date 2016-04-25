
package fiuba.ordertracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Hashtable;
import java.util.List;

import fiuba.ordertracker.listeners.GPSTracker;
import fiuba.ordertracker.pojo.Client;
import fiuba.ordertracker.services.ClientService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientsMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker marker;
    private Hashtable<String, Client> markers;
    private ClientService clientService = ClientService.getInstance();
    private String focusInClient = "";
    private String agendaDate = null;
    private LatLng centeredMarker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clients_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        markers = new Hashtable<String, Client>();

        String clientFocusedId = getIntent().getExtras().getString("clientID");
        this.agendaDate = getIntent().getExtras().getString("agendaDate");

        if(clientFocusedId == null)
        {
            GPSTracker gpsTracker = new GPSTracker(this);
            centeredMarker = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
            // came from client list
            this.focusInClient = "-1";
        } else {
            this.focusInClient = clientFocusedId;

        }
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

        // Change the default info window with a custom info window
        //mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());

        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

        // Load the clients in the map
        this.loadClientsInMap(googleMap);

        mMap.setIndoorEnabled(true);
        mMap.setBuildingsEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                Client client = null;

                if (marker.getId() != null && markers != null && markers.size() > 0) {
                    if ( markers.get(marker.getId()) != null &&
                            markers.get(marker.getId()) != null) {
                        client = markers.get(marker.getId());
                    }
                }

                // TODO: Refactor here and in ClientListAdapter
                GPSTracker gps = new GPSTracker(ClientsMapActivity.this);
                Double currentLatitude = gps.getLatitude();
                Double currentLongitude = gps.getLongitude();

                Intent intent = new Intent(ClientsMapActivity.this, ClientDetailActivity.class);
                intent.putExtra("clientID", client.getId());
                intent.putExtra("name", client.getSocialReason());
                intent.putExtra("clientCode", client.getCode());
                intent.putExtra("address", client.getDireccion());
                intent.putExtra("telephone", client.getTelefono());
                intent.putExtra("distance", String.valueOf(client.getDistance(currentLatitude,currentLongitude)));
                intent.putExtra("latitude", String.valueOf(client.getLatitude()));
                intent.putExtra("longitude", String.valueOf(client.getLongitude()));

                startActivity(intent);
            }
        });
    }

    private void loadClientsInMap(final GoogleMap googleMap) {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("OrderTrackerPref", 0);
        int idVendedor = pref.getInt("id", 0);

        Call<List<Client>> call = clientService.clients.Clients(Integer.toString(idVendedor), null, null, null, null, this.agendaDate);
        final ClientsMapActivity _self = this;
        final GoogleMap _googleMap = googleMap;

        call.enqueue(new Callback<List<Client>>() {

            @Override
            public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {

                // Get result Repo from response.body()
                List<Client> listClients = response.body();

                for (Client client : listClients) {
                    LatLng client_position = new LatLng(client.getLatitude(), client.getLongitude());
                    String client_name = client.getSocialReason();
                    String client_address = client.getDireccion();

                    // Client marker
                    MarkerOptions marker = new MarkerOptions();
                    marker.position(client_position);
                    marker.flat(true);
                    marker.title(client_name);
                    marker.snippet(client_address);

                    if(client.getId().equals(_self.focusInClient))
                        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

                    Marker googleMarker = _googleMap.addMarker(marker);
                    markers.put(googleMarker.getId(), client);

                    // If theres a client specified to be focused, we positioned the camera
                    // over the marker.
                    if(client.getId().equals(_self.focusInClient)) {
                        googleMarker.showInfoWindow();
                        _googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(client_position, 15));
                        _googleMap.animateCamera(CameraUpdateFactory.zoomIn());
                        _googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
                    }
                }
                if (centeredMarker != null) {
                    MarkerOptions marker = new MarkerOptions();
                    marker.position(centeredMarker);
                    marker.flat(true);
                    marker.title("Tu ubicación");
                    marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    _googleMap.addMarker(marker).showInfoWindow();
                    _googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centeredMarker, 15));
                    _googleMap.animateCamera(CameraUpdateFactory.zoomIn());
                    _googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
                }
            }

            @Override
            public void onFailure(Call<List<Client>> call, Throwable t) {
                /*//Aca tenemos que agregar el msj de error a mostrar...
                TextView textNoClients = (TextView) findViewById(R.id.text_no_clients);
                textNoClients.setText("Hubo un error al cargar los clientes por favor reintente mas tarde");
                textNoClients.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);*/
            }
        });
    }

    private class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private View view;

        public CustomInfoWindowAdapter() {
            view = getLayoutInflater().inflate(R.layout.client_map_info_window,
                    null);
        }

        @Override
        public View getInfoContents(Marker marker) {

            if (ClientsMapActivity.this.marker != null
                    && ClientsMapActivity.this.marker.isInfoWindowShown()) {
                ClientsMapActivity.this.marker.hideInfoWindow();
                ClientsMapActivity.this.marker.showInfoWindow();
            }
            return null;
        }

        @Override
        public View getInfoWindow(final Marker marker) {
            ClientsMapActivity.this.marker = marker;

            Client client = null;

            if (marker.getId() != null && markers != null && markers.size() > 0) {
                if ( markers.get(marker.getId()) != null &&
                        markers.get(marker.getId()) != null) {
                    client = markers.get(marker.getId());
                }
            }


            final String title = marker.getTitle();
            final TextView titleUi = ((TextView) view.findViewById(R.id.title));
            if (title != null) {
                titleUi.setText(title);
            } else {
                titleUi.setText("");
            }

            final String snippet = marker.getSnippet();
            final TextView snippetUi = ((TextView) view
                    .findViewById(R.id.snippet));
            if (snippet != null) {
                snippetUi.setText(snippet);
            } else {
                snippetUi.setText("");
            }

            return view;
        }
    }

}
