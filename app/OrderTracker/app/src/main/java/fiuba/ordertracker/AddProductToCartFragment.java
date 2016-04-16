package fiuba.ordertracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import org.w3c.dom.Text;

import fiuba.ordertracker.pojo.Product;


public class AddProductToCartFragment extends DialogFragment {

    private static Product product;

    public static AddProductToCartFragment newInstance(Product p) {

        AddProductToCartFragment frag = new AddProductToCartFragment();
        Bundle args = new Bundle();
        product = p;
        args.putString("product_id", p.getId());
        args.putString("product_name", p.getNombre());
        args.putString("product_brand", p.getMarca());

        frag.setArguments(args);
        return frag;
    }

    public static AddProductToCartFragment newInstance(String product_id, String product_name, String product_brand) {

        AddProductToCartFragment frag = new AddProductToCartFragment();
        Bundle args = new Bundle();

        args.putString("product_id", product_id);
        args.putString("product_name", product_name);
        args.putString("product_brand", product_brand);

        frag.setArguments(args);
        return frag;
    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_add_product_to_cart, container, false);

        return v;
    }*/

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        /**
         * Product info
         */
        String product_id = getArguments().getString("product_id");
        String product_name = getArguments().getString("product_name");
        String product_brand = getArguments().getString("product_brand");
        String product_availability = getArguments().getString("product_availability");

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.fragment_add_product_to_cart, null);

        /**
         * Number picker for select stock
         */
        NumberPicker np = (NumberPicker) v.findViewById(R.id.numberPicker);
        np.setMinValue(0);
        np.setMaxValue(1000);
        np.setWrapSelectorWheel(true);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.add_product)

                // Confirm button
                .setPositiveButton(R.string.confirm,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //((ProductDetailActivity)getActivity()).doPositiveClick();
                            }
                        }
                )

                // Cancel button
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //((ProductDetailActivity)getActivity()).doNegativeClick();
                                getDialog().dismiss();
                            }
                        }
                )
                .create();
    }
}
