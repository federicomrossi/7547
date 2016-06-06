package fiuba.ordertracker;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;
    private OnFragmentInteractionListener mListener;
    private Boolean hasDiscount = false;

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
        this.view = inflater.inflate(R.layout.fragment_product_detail, container, false);

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

        LinearLayout discount_1_container = (LinearLayout) view.findViewById(R.id.discount_1_container);
        TextView discount_1 = (TextView) view.findViewById(R.id.discount_1);
        TextView discount_1_min = (TextView) view.findViewById(R.id.discount_1_min);
        this.loadDiscount(discount_1_container, discount_1, discount_1_min, getArguments().getString("discount_1"), getArguments().getString("discount_1_min"));

        LinearLayout discount_2_container = (LinearLayout) view.findViewById(R.id.discount_2_container);
        TextView discount_2 = (TextView) view.findViewById(R.id.discount_2);
        TextView discount_2_min = (TextView) view.findViewById(R.id.discount_2_min);
        this.loadDiscount(discount_2_container, discount_2, discount_2_min, getArguments().getString("discount_2"), getArguments().getString("discount_2_min"));

        LinearLayout discount_3_container = (LinearLayout) view.findViewById(R.id.discount_3_container);
        TextView discount_3 = (TextView) view.findViewById(R.id.discount_3);
        TextView discount_3_min = (TextView) view.findViewById(R.id.discount_3_min);
        this.loadDiscount(discount_3_container, discount_3, discount_3_min, getArguments().getString("discount_3"), getArguments().getString("discount_3_min"));

        LinearLayout discount_4_container = (LinearLayout) view.findViewById(R.id.discount_4_container);
        TextView discount_4 = (TextView) view.findViewById(R.id.discount_4);
        TextView discount_4_min = (TextView) view.findViewById(R.id.discount_4_min);
        this.loadDiscount(discount_4_container, discount_4, discount_4_min, getArguments().getString("discount_4"), getArguments().getString("discount_4_min"));

        LinearLayout discount_5_container = (LinearLayout) view.findViewById(R.id.discount_5_container);
        TextView discount_5 = (TextView) view.findViewById(R.id.discount_5);
        TextView discount_5_min = (TextView) view.findViewById(R.id.discount_5_min);
        this.loadDiscount(discount_5_container, discount_5, discount_5_min, getArguments().getString("discount_5"), getArguments().getString("discount_5_min"));

        // Hide discounts section if there's no discounts.
        if(!this.hasDiscount) {
            ((LinearLayout) view.findViewById(R.id.discounts_container)).setVisibility(View.GONE);
        }

        name.setText(getArguments().getString("name"));
        brand.setText(getArguments().getString("brand"));
        description.setText(getArguments().getString("description"));

        // Price formatter
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("es_AR"));
        currencyFormatter.setMaximumFractionDigits(2);
        Double formattedPrice = Double.parseDouble(getArguments().getString("price"));
        price.setText(currencyFormatter.format(formattedPrice));

        category.setText(getArguments().getString("category"));
        availability.setText(getArguments().getString("availability"));
        new ImageLoadTask(getArguments().getString("url_image_normal"), image).execute();

        final String productId = getArguments().getString("id");
        final String productName = getArguments().getString("name");
        final String productBrand = getArguments().getString("brand");
        final String productStock = getArguments().getString("stock");

        Button button = (Button) view.findViewById(R.id.product_detail_add_to_cart);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = ((Activity) view.getContext()).getFragmentManager().beginTransaction();
                AddProductToCartFragment newFragment = AddProductToCartFragment.newInstance(productId,
                        productName, productBrand, productStock);
                newFragment.show(ft, "dialog");
            }
        });

        return view;
    }

    private void loadDiscount(LinearLayout container, TextView discount_view,
                              TextView discount_min_view, String discount, String discount_min) {

        if(discount == null || discount_min == null || discount == "" || discount_min == "" ||
                (discount.equals("0") && discount_min.equals("0"))) {
            container.setVisibility(View.GONE);
            return;
        }

        discount_min_view.setText(discount_min + " prod. o más");
        discount_view.setText(discount + "%");
        this.hasDiscount = true;
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
