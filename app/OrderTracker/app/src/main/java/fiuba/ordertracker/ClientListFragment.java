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
import java.util.Date;
import java.util.List;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_client_list, container, false);

        // Set current date
        TextView textDayOfWeek = (TextView) view.findViewById(R.id.day_of_week_text);
        textDayOfWeek.setText(this.dayOfWeekScreen);

        //final SearchView razonFilterView = (SearchView) view.findViewById(R.id.searchView);
        //final EditText clientCodeFilterView = (EditText) view.findViewById(R.id.editText_client_code);
        //Fonts.changeSearchViewTextColorBlack(clientCodeFilterView);
        //Fonts.changeSearchViewTextColorBlack(razonFilterView);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        getActivity().setProgressBarIndeterminateVisibility(true);

        // Clients list
        recyclerView = (RecyclerView) view.findViewById(R.id.clientsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        ClientService cs = ClientService.getInstance();

        intent = getActivity().getIntent();

        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("OrderTrackerPref", 0);
        int idVendedor = pref.getInt("id", 0);


        // Create a call instance for looking up Retrofit contributors.
        String orderBy = intent.getStringExtra("orderBy") != null ? intent.getStringExtra("orderBy") : "razon_social";
        //Call<List<Client>> call = cs.clients.Clients(null,null,orderBy,null);
        Call<List<Client>> call = cs.clients.Clients(Integer.toString(idVendedor), intent.getStringExtra("socialReasonFilter"), orderBy, null, intent.getStringExtra("codClientFilter"));
        //Call<List<Client>> call = cs.clientsFromTodayByVendIdService.ClientsFromTodayByVendIdService(idVendedor,orderBy,null);

        final ClientListActivity self_ = (ClientListActivity) getActivity();
        final View _view = view;

        call.enqueue(new Callback<List<Client>>() {
            @Override
            public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                // Get result Repo from response.body()
                List<Client> listClients = response.body();
                clientListAdapter = new ClientListAdapter(self_, listClients);
                clientListAdapter.setOriginalData(listClients);
                progressBar.setVisibility(View.GONE);

                if (listClients.size() == 0) {
                    TextView textNoClients = (TextView) _view.findViewById(R.id.text_no_clients);
                    textNoClients.setVisibility(View.VISIBLE);
                }

                recyclerView.setAdapter(clientListAdapter);
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

        /*razonFilterView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equals(""))
                    onQueryTextSubmit("");
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                //setRecycler();
                return false;
            }

        });

        clientCodeFilterView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_ACTION_DONE) || ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_DOWN))) {
                    //setRecycler();
                    return true;
                } else {
                    return false;
                }
            }
        });*/

        return view;
    }

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
}
