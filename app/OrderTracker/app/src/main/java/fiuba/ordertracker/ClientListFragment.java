package fiuba.ordertracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import fiuba.ordertracker.helpers.FiltersHelper;
import fiuba.ordertracker.helpers.Fonts;
import fiuba.ordertracker.pojo.Client;
import fiuba.ordertracker.services.ClientService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ClientListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ClientListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClientListFragment extends Fragment {

    private RecyclerView recyclerView;
    private ClientListAdapter clientListAdapter;
    private ProgressBar progressBar;
    private Intent intent ;
    private String dayOfWeekScreen;
    private String dayOfWeekFilter; // null if option is 'Fuera de ruta'
    private OnFragmentInteractionListener mListener;

    public ClientListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param calendar the day of the week the fragment belongs to
     * @return A new instance of fragment ClientListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClientListFragment newInstance(Calendar calendar) {
        ClientListFragment fragment = new ClientListFragment();

        DateFormat dateFormatScreen = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat dateFormatFilter = new SimpleDateFormat("yyyy-MM-dd");

        Bundle args = new Bundle();
        if (calendar != null) {
            args.putString("day_of_week_screen", dateFormatScreen.format(calendar.getTime()));
            args.putString("day_of_week_filter", dateFormatFilter.format(calendar.getTime()));
            fragment.setArguments(args);
        } else{
            args.putString("day_of_week_screen", "Fuera de ruta");
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dayOfWeekScreen = getArguments().getString("day_of_week_screen");
            dayOfWeekFilter = getArguments().getString("day_of_week_filter");
        }
    }

    public void reloadClients() {

        ClientService cs = ClientService.getInstance();

        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("OrderTrackerPref", 0);
        int idVendedor = pref.getInt("id", 0);

        // Create a call instance for looking up Retrofit contributors.
        String orderBy = intent.getStringExtra("orderBy") != null ? intent.getStringExtra("orderBy") : "razon_social";
        //Call<List<Client>> call = cs.clients.Clients(null,null,orderBy,null);
        Call<List<Client>> call = cs.clients.Clients(Integer.toString(idVendedor), intent.getStringExtra("socialReasonFilter"), orderBy, null, intent.getStringExtra("codClientFilter"), this.dayOfWeekFilter, null, null);
        //Call<List<Client>> call = cs.clientsFromTodayByVendIdService.ClientsFromTodayByVendIdService(idVendedor,orderBy,null);

        final ClientListActivity self_ = (ClientListActivity) getActivity();
        final View _view = this.getView();
        final String _date = this.dayOfWeekFilter;
        final ClientListFragment _this = this;

        call.enqueue(new Callback<List<Client>>() {
            @Override
            public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                // Get result Repo from response.body()
                List<Client> listClients = response.body();
                clientListAdapter = new ClientListAdapter(self_, listClients);
                clientListAdapter.setOriginalData(listClients, _date);
                Collections.sort(listClients);

                progressBar.setVisibility(View.GONE);

                if (listClients.size() == 0) {
                    TextView textNoClients = (TextView) _view.findViewById(R.id.text_no_clients);
                    textNoClients.setVisibility(View.VISIBLE);
                }

                recyclerView.setAdapter(clientListAdapter);

                self_.filterClientsInCurrentTab();
            }

            @Override
            public void onFailure(Call<List<Client>> call, Throwable t) {
                //Aca tenemos que agregar el msj de error a mostrar... puto el que lee
                TextView textNoClients = (TextView) _view.findViewById(R.id.text_no_clients);
                textNoClients.setText("Hubo un error al cargar los clientes por favor reintente mas tarde");
                textNoClients.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_client_list, container, false);

        // Set current date
        TextView textDayOfWeek = (TextView) view.findViewById(R.id.day_of_week_text);
        textDayOfWeek.setText(this.dayOfWeekScreen);
        
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        getActivity().setProgressBarIndeterminateVisibility(true);

        // Clients list
        recyclerView = (RecyclerView) view.findViewById(R.id.clientsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        intent = getActivity().getIntent();

        // Load the clients for the first time
        this.reloadClients();

        return view;
    }

    /*public void onResume() {
        ClientListActivity self_ = (ClientListActivity) getActivity();
        self_.filterClientsInCurrentTab();
    }*/

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public String getTabDate() {
        return this.dayOfWeekFilter;
    }

    public void executeFiltering(Map<String, String> filterValues) {

        if(clientListAdapter == null) return;

        List<Client> listFiltered = FiltersHelper.filterClientsBySocialReason(clientListAdapter.getOriginalData(), filterValues.get("name"));
        listFiltered = FiltersHelper.filterClientsByCode(listFiltered, filterValues.get("code"));
        clientListAdapter.setData(listFiltered);
        recyclerView.setAdapter(clientListAdapter);
    }
}
