package com.example.scampa.starbuzzapp;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by scampa on 25/3/2016.
 */
public class ClientsActivity extends Activity {

    public static final String CLIENT_ID = "clientId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        int clientId = (Integer) getIntent().getExtras().get(CLIENT_ID);
        Client client = Client.clients[clientId];

        // Setea la imagen de acuerdo con el id del activity_client.xml
        ImageView photo = (ImageView) findViewById(R.id.photo);
        photo.setImageResource(client.getProfilePicture());
        photo.setContentDescription(client.getName());

        // Setea el nombre de acuerdo con el id del activity_client.xml
        TextView name = (TextView) findViewById(R.id.name);
        name.setTypeface(name.getTypeface(), Typeface.BOLD);
        name.setText(client.getName());

        // Setea la direccion de acuerdo con el id del activity_client_item.xml
        TextView description = (TextView) findViewById(R.id.address);
        description.setText(client.getAddress());

    }
}
