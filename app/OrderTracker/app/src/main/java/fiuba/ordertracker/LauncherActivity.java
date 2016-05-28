package fiuba.ordertracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SharedPreferences pref = getApplicationContext().getSharedPreferences("OrderTrackerPref", 0);
        int idVendedor = pref.getInt("id", 0);
        if(idVendedor == 0){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this, ClientListActivity.class);
            startActivity(intent);
        }
        finish();
    }
}
