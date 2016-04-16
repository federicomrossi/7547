package fiuba.ordertracker;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
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

import java.util.List;

import fiuba.ordertracker.helpers.FiltersHelper;
import fiuba.ordertracker.helpers.Fonts;
import fiuba.ordertracker.pojo.Categorie;
import fiuba.ordertracker.services.CategorieService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductCategoryListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductCategoryListFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class ProductCategoryListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private ProductCategoryListAdapter productCategoryListAdapter;
    private ProgressBar progressBar;
    private OnFragmentInteractionListener mListener;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductCategoryListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductCategoryListFragment newInstance(String param1, String param2) {
        ProductCategoryListFragment fragment = new ProductCategoryListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public ProductCategoryListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_category_list, container, false);


        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        getActivity().setProgressBarIndeterminateVisibility(true); // POSIBLE BUG

        // Categories list
        recyclerView = (RecyclerView) view.findViewById(R.id.productsCategoriesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        CategorieService cs = CategorieService.getInstance();

        // Create a call instance for looking up Retrofit contributors.
        //Call<List<Categorie>> call = cs.categories.Categories(intent.getStringExtra("nameFilter"),null,null);
        Call<List<Categorie>> call = cs.categories.Categories("",null,null);


        final FragmentActivity self_ = getActivity();
        final Fragment _parentFragment = this.getParentFragment();
        final View _view = view;

        call.enqueue(new Callback<List<Categorie>>() {
            @Override
            public void onResponse(Call<List<Categorie>> call, Response<List<Categorie>> response) {
                // Get result Repo from response.body()
                List<Categorie> listCategories = response.body();
                productCategoryListAdapter = new ProductCategoryListAdapter(self_, listCategories, _parentFragment);
                productCategoryListAdapter.setOriginalData(listCategories);
                progressBar.setVisibility(View.GONE);

                if(listCategories.size() == 0) {
                    TextView textNoCategories = (TextView) _view.findViewById(R.id.text_no_categories);
                    textNoCategories.setVisibility(View.VISIBLE);
                }

                recyclerView.setAdapter(productCategoryListAdapter);
            }

            @Override
            public void onFailure(Call<List<Categorie>> call, Throwable t) {
                //Aca tenemos que agregar el msj de error a mostrar...
                progressBar.setVisibility(View.GONE);
                TextView textNoCategories = (TextView) _view.findViewById(R.id.text_no_categories);
                textNoCategories.setText("Hubo un error al cargar las categorias por favor reintente mas tarde");
                textNoCategories.setVisibility(View.VISIBLE);
            }

        });

        final SearchView nameFilter = (SearchView) view.findViewById(R.id.searchView);
        Fonts.changeSearchViewTextColorBlack(nameFilter);
        nameFilter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equals("")){
                    this.onQueryTextSubmit("");
                }
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                String filter = nameFilter.getQuery().toString();
                List<Categorie> listFiltered = FiltersHelper.filterCategoriesByName(productCategoryListAdapter.getOriginalData(), filter);
                productCategoryListAdapter.setData(listFiltered);
                recyclerView.setAdapter(productCategoryListAdapter);
                return true;
            }

        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onProductCategoryListFragmentInteraction(uri);
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

    // Filters //


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onProductCategoryListFragmentInteraction(Uri uri);
    }

}
