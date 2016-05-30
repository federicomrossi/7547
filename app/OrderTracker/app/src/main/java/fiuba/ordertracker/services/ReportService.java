package fiuba.ordertracker.services;

import java.io.IOException;

import fiuba.ordertracker.helpers.Constants;
import fiuba.ordertracker.pojo.Report;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by pablo on 25/5/2016.
 */

public final class ReportService {
    public GetReport  getReport;
    private static ReportService instance = null;

    private ReportService(){
        // Create a very simple REST adapter which points the GitHub API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL_SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an instance of our GitHub API interface.
        getReport = retrofit.create(GetReport.class);
    }

    public static synchronized ReportService getInstance() {
        if (instance == null) {
            instance = new ReportService();
        }

        return instance;
    }

    //categorias
    public interface GetReport {
        @GET("report/getReport/{id}")
        Call<Report> GetReport(@Path("id") int vendId);
    }



    public static void main(String... args) throws IOException {
        ReportService cats = ReportService.getInstance();

        // Create a call instance for looking up Retrofit contributors.
        Call<Report> call = cats.getReport.GetReport(1);

        // Fetch and print a list of the contributors to the library.
        Report report = call.execute().body();
    }
}