package fiuba.ordertracker;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ClientListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ClientListAdapter clientListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_list);

        // Toolbar
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle(getString(R.string.activity_client_list));

        // Clients list
        recyclerView = (RecyclerView) findViewById(R.id.clientsList);
        clientListAdapter = new ClientListAdapter(this, getData());
        recyclerView.setAdapter(clientListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }


    /**
     * TEMP!!!
     */

    public static List<ClientModel> getData() {
        List<ClientModel> data = new ArrayList<>();

        String[] names = {"Sabrina Campa", "Pablo Ascarza", "Ezequier Reyes", "Federico Rossi"};
        String[] addresses = {"Av. Mitre 123, Bs. As.", "Av. Mitre 123, Bs. As.", "Av. Mitre 123, Bs. As.", "Av. Mitre 123, Bs. As."};
        String[] distances = {"1 km", "2 km", "3 km", "4 km"};

        for(int i = 0; i < names.length; i++) {
            ClientModel c = new ClientModel();
            c.name = names[i];
            c.address = addresses[i];
            c.distance = distances[i];
            data.add(c);
        }

        return data;
    }
}
