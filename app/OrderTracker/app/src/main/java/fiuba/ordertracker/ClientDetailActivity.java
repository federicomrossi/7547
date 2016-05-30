package fiuba.ordertracker;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
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
import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fiuba.ordertracker.pojo.Agenda;
import fiuba.ordertracker.pojo.Client;
import fiuba.ordertracker.pojo.Order;
import fiuba.ordertracker.services.ClientService;
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
    private View view;
    private Client client;
    private Agenda agenda = null;

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
        SharedPreferences pref = getApplicationContext().getSharedPreferences("OrderTrackerPref", 0);
        int vendedorID = pref.getInt("id", 0);

        // Populate client attribute
        ClientService clientService = ClientService.getInstance();
        Call<List<Client>> call = clientService.clients.Clients(this.clientID, String.valueOf(vendedorID), null, null, null,
                null, null, this.agendaDate, null);

        call.enqueue(new Callback<List<Client>>() {
            @Override
            public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                List<Client> clientsList = response.body();

                for (Client c : clientsList) {
                    client = c;
                }
            }

            @Override
            public void onFailure(Call<List<Client>> call, Throwable t) {
                t.printStackTrace();
            }
        });


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

        final String fechaVisitaConcretada;
        final String comment;
        final Boolean isOrderDone;

        // Bullshit to support the fucking model.
        if(this.agenda == null) {
            // Use the client
            fechaVisitaConcretada = this.client.getFechaVisitaConcretada();
            comment = this.client.getComment();
            isOrderDone = this.client.getIsOrderGenerated();
        } else {
            // Use the agenda
            fechaVisitaConcretada = this.agenda.getFechaVisitaConcretada();
            comment = this.agenda.getComment();
            isOrderDone = this.agenda.getIsOrderGenerated();
        }

        // Order validation
        if(fechaVisitaConcretada != null && !isOrderDone && !comment.isEmpty()) {
            Toast.makeText(getApplicationContext(), "No es posible generar un pedido. La visita ya ha sido registrada previamente.", Toast.LENGTH_LONG).show();
            return;
        }


        ArrayList<String> info = new ArrayList<String>();
        info.add(this.clientID);
        info.add(this.clientName);

        this.view = view;

        final OrderService os = OrderService.getInstance();
        Integer clientIntID = new Integer(this.clientID);

        Call<Order> call = os.order.getActiveProductOrderByClient(clientIntID.intValue());

        final ClientDetailActivity self_ = this;
        final ArrayList<String> _info = info;
        final View _view = view;

        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {

                Order activeOrder;

                if (response.code() == 500) {
                    // No se tiene un pedido activo...
                    // Mostrar QR
                    IntentIntegrator integrator = new IntentIntegrator(self_);
                    integrator.setCaptureActivity(QrScannerActivity.class);
                    integrator.setOrientationLocked(false);
                    integrator.addExtra("PROMPT_MESSAGE", "Posicione el código QR dentro del rectángulo para escanearlo");
                    integrator.initiateScan(_info);

                } else {
                    // No mostrar QR, ir directamente a tab
                    Intent intent = new Intent(_view.getContext(), TabActivity.class);
                    intent.putExtra("clientName", self_.clientName);
                    intent.putExtra("clientID",self_.clientID);
                    intent.putExtra("agendaDate",self_.agendaDate);
                    _view.getContext().startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                // Actuar en caso de error. Una posibilidad: ir al login.
            }
        });
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

            String clientID = scanResult.getContents();

            final String _clientName = this.clientName;
            final String _clientID = this.clientID;
            final Client _client = this.client;
            final String _agendaDate = this.agendaDate;
            final View _view = this.view;

            if (this.agenda != null) {

            }

            if (this.clientID.equals(clientID)){
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("Seleccione su acción");

                // Ingresar pedido button
                alertDialog.setPositiveButton(R.string.new_order,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                                Intent intent2 = new Intent(getApplicationContext(), TabActivity.class);
                                intent2.putExtra("clientName", _clientName);
                                intent2.putExtra("clientID", _clientID);
                                intent2.putExtra("agendaDate", _agendaDate);
                                startActivity(intent2);
                            }
                        });

                // Ingresar comentario button
                alertDialog.setNegativeButton(R.string.new_comment,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                FragmentTransaction ft = ((Activity) _view.getContext()).getFragmentManager().beginTransaction();
                                AddCommentFragment commentFragment = AddCommentFragment.newInstance(_client);
                                commentFragment.show(ft, "dialog");
                            }
                        }
                );

                // Cancel button
                alertDialog.setNeutralButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }
                );

                alertDialog.create().show();

            } else{
                Toast.makeText(getApplicationContext(), "El QR no corresponde al cliente", Toast.LENGTH_LONG).show();
            }

        } else{
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

    public void updateAgenda(Agenda agenda) {
        this.agenda = agenda;
    }

    public void saveComment(String comment) {

        ClientService clientService = ClientService.getInstance();
        Call<Agenda> call = clientService.comment.AddComment(client.getAgendaId(), comment);
        final ClientDetailActivity _this = this;

        call.enqueue(new Callback<Agenda>() {
            @Override
            public void onResponse(Call<Agenda> call, Response<Agenda> response) {
                _this.updateAgenda(response.body());
                System.out.println("**** Stored comment: " + agenda.getComment());
            }

            @Override
            public void onFailure(Call<Agenda> call, Throwable t) {
                t.printStackTrace();
            }
        });
        Toast.makeText(this, "El comentario ha sido ingresado correctamente", Toast.LENGTH_LONG).show();
    }
}
