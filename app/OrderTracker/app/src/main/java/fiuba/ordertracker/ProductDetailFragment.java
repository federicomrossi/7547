package fiuba.ordertracker;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import fiuba.ordertracker.helpers.ImageLoadTask;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Toolbar toolbar;

    private String product_id;
    private String product_name;
    private String product_brand;
    private String product_availability;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProductDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductDetailFragment newInstance(String param1, String param2) {
        ProductDetailFragment fragment = new ProductDetailFragment();
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
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_product_detail, container, false);
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);

        //Toolbar toolbar = (Toolbar) view.findViewById(R.id.client_detail_toolbar); // TODO change for the toolbar in the product layout
        //setSupportActionBar(toolbar);
        //CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        // Toolbar
        //toolbar = (Toolbar) view.findViewById(R.id.app_bar);
        //((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        //((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("Detalle de producto");

        /*Intent i = getIntent();

        // Set data from intent
        //collapsingToolbar.setTitle(i.getStringExtra("name"));*/

        /*this.product_id = "1";
        this.product_availability = i.getStringExtra("availability");
        this.product_name = i.getStringExtra("name");
        this.product_brand = i.getStringExtra("brand");*/

        TextView name = (TextView) view.findViewById(R.id.product_name);
        TextView brand = (TextView) view.findViewById(R.id.product_brand);
        TextView description = (TextView) view.findViewById(R.id.product_description);
        TextView price = (TextView) view.findViewById(R.id.product_price);
        TextView category = (TextView) view.findViewById(R.id.product_category);
        TextView availability = (TextView) view.findViewById(R.id.product_availability);
        ImageView image = (ImageView) view.findViewById(R.id.detail_view);

        name.setText(getArguments().getString("name"));
        brand.setText(getArguments().getString("brand"));
        description.setText(getArguments().getString("description"));
        price.setText("$ " + getArguments().getString("price"));
        category.setText(getArguments().getString("category"));
        availability.setText(getArguments().getString("availability"));
        new ImageLoadTask(getArguments().getString("url_image_normal"), image).execute();

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onProductDetailFragmentInteraction(uri);
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
        void onProductDetailFragmentInteraction(Uri uri);
    }
}
