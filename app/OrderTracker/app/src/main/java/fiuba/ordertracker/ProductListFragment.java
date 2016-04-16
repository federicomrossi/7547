package fiuba.ordertracker;

import android.app.FragmentTransaction;
import android.content.Context;
import android.media.Image;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.List;

import fiuba.ordertracker.helpers.FiltersHelper;
import fiuba.ordertracker.helpers.Fonts;
import fiuba.ordertracker.pojo.Product;
import fiuba.ordertracker.services.ProductService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private ProductListAdapter productListAdapter;
    private ProgressBar progressBar;

    private OnFragmentInteractionListener mListener;

    public ProductListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductListFragment newInstance(String param1, String param2) {
        ProductListFragment fragment = new ProductListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
                            final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        getActivity().setProgressBarIndeterminateVisibility(true);

        // Products list
        recyclerView = (RecyclerView) view.findViewById(R.id.productsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        ProductService ps = ProductService.getInstance();
        Log.i("CATEGORIAAAAAA", getArguments().getString("category"));
        // Create a call instance for looking up Retrofit contributors.
        Call<List<Product>> call = ps.products.Products(getArguments().getString("category"), null, null, null, null, null, null, null);

        final FragmentActivity self_ = getActivity();
        final Fragment _parentFragment = this.getParentFragment();
        final View _view = view;

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                // Get result Repo from response.body()
                List<Product> listProducts = response.body();
                productListAdapter = new ProductListAdapter(self_, listProducts, _parentFragment);
                productListAdapter.setCategory(getArguments().getString("categoryName")); // no longer needed :P
                productListAdapter.setOriginalData(listProducts);
                progressBar.setVisibility(View.GONE);

                if (listProducts.size() == 0) {
                    TextView textNoProducts = (TextView) _view.findViewById(R.id.text_no_products);
                    textNoProducts.setVisibility(View.VISIBLE);
                }

                recyclerView.setAdapter(productListAdapter);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                TextView textNoProducts = (TextView) _view.findViewById(R.id.text_no_products);
                textNoProducts.setText("Hubo un error al cargar los productos por favor reintente mas tarde");
                textNoProducts.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });

        setFilters(view);


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onProductListFragmentInteraction(uri);
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
        void onProductListFragmentInteraction(Uri uri);
    }

    public void onClickShowHideFilters(View view) {

        LinearLayout button_filter = (LinearLayout) view.findViewById(R.id.filters_container);

        if(button_filter.getVisibility() == View.GONE)
            button_filter.setVisibility(View.VISIBLE);
        else {
            button_filter.setVisibility(View.GONE);
            EditText editText_brand = (EditText) view.findViewById(R.id.marca_filter);
            editText_brand.clearFocus();

        }
    }

    public void setFilters(View view)
    {
        final SearchView nameFilter = (SearchView)view.findViewById(R.id.searchView);
        Fonts.changeSearchViewTextColorBlack(nameFilter);
        nameFilter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals("")) {
                    this.onQueryTextSubmit("");
                }
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                setRecycler();
                return true;
            }

        });



        final EditText marcaFilter = (EditText) view.findViewById(R.id.marca_filter);
        Fonts.changeSearchViewTextColorBlack(marcaFilter);
        marcaFilter.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event != null && event.getAction() != KeyEvent.ACTION_DOWN) {
                    return false;
                } else if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || event == null
                        || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    setRecycler();
                    return true;
                } else {
                    return false;
                }
            }
        });

        ImageButton button = (ImageButton) view.findViewById(R.id.imageButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickShowHideFilters(getView());
            }
        });

    }

    public void setRecycler()
    {
        View view = getView();
        String marcaFilter = ((EditText) view.findViewById(R.id.marca_filter)).getText().toString();
        String nameFilter = ((SearchView) view.findViewById(R.id.searchView)).getQuery().toString();
        List<Product> listFiltered = FiltersHelper.filterProductsByName(productListAdapter.getOriginalData(), nameFilter);
        listFiltered = FiltersHelper.filterProductsByMarca(listFiltered, marcaFilter);
        productListAdapter.setData(listFiltered);
        recyclerView.setAdapter(productListAdapter);
    }
}
