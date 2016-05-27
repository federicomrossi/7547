package fiuba.ordertracker;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

import fiuba.ordertracker.pojo.Report;
import fiuba.ordertracker.services.ReportService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("OrderTrackerPref", 0);
        int sellerId = pref.getInt("id", 0);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle("Reporte");

        ReportService rs = ReportService.getInstance();
        Call<Report> call = rs.getReport.GetReport(sellerId);

        call.enqueue(new Callback<Report>() {
            @Override
            public void onResponse(Call<Report> call, Response<Report> response) {
                NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("es_AR"));
                currencyFormatter.setMaximumFractionDigits(2);

                Report report = response.body();
                TextView textAmountClientsOfDay = (TextView)findViewById(R.id.amountClientsOfDay);
                TextView textAmountClientsOutOfRoad = (TextView)findViewById(R.id.amountClientsOutOfRoad);
                TextView textAmountSold = (TextView)findViewById(R.id.amountSold);
                TextView textAmountMoney = (TextView)findViewById(R.id.amountMoney);

                // Set report items
                textAmountClientsOfDay.setText(String.valueOf(report.getClientsOnRoute()));
                textAmountClientsOutOfRoad.setText(String.valueOf(report.getClientsNotOnRoute()));
                textAmountSold.setText(report.getTotalProductos());
                Double totalMoney = Double.parseDouble(report.getTotalMoney());
                textAmountMoney.setText(currencyFormatter.format(totalMoney));
            }

            @Override
            public void onFailure(Call<Report> call, Throwable t) {
                // TODO
            }

        });

    }
}
