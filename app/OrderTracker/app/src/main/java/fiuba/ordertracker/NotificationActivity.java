package fiuba.ordertracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class NotificationActivity extends AppCompatActivity {

    private Toolbar toolbar;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle("Notificación");

        textView = (TextView) findViewById(R.id.textView);
        Intent intent = NotificationActivity.this.getIntent();
        String data = intent.getStringExtra("data");
        textView.setText(data);
    }
}
