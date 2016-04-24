package fiuba.ordertracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import fiuba.ordertracker.pojo.Order;
import fiuba.ordertracker.pojo.Product;
import fiuba.ordertracker.services.OrderService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddProductToCartFragment extends DialogFragment {

    private static Product product;
    private static Order order;

    public static AddProductToCartFragment newInstance(Product p) {

        AddProductToCartFragment frag = new AddProductToCartFragment();
        Bundle args = new Bundle();
        product = p;
        args.putString("product_id", p.getId());
        args.putString("product_name", p.getNombre());
        args.putString("product_brand", p.getMarca());
        args.putString("product_stock", p.getStock());

        frag.setArguments(args);
        return frag;
    }

    public static AddProductToCartFragment newInstance(String product_id, String product_name, String product_brand,
                                                       String product_stock) {

        AddProductToCartFragment frag = new AddProductToCartFragment();
        Bundle args = new Bundle();

        args.putString("product_id", product_id);
        args.putString("product_name", product_name);
        args.putString("product_brand", product_brand);
        args.putString("product_stock", product_stock);

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
        final String product_id = getArguments().getString("product_id");
        String product_name = getArguments().getString("product_name");
        String product_brand = getArguments().getString("product_brand");
        String product_availability = getArguments().getString("product_availability");
        final String product_stock = getArguments().getString("product_stock");

        order = ((TabActivity) getActivity()).getActiveOrder();

        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.fragment_add_product_to_cart, null);

        /**
         * Order info
         */
        final Order _order = order;

        /**
         * Number picker for select stock
         */
        final NumberPicker np = (NumberPicker) v.findViewById(R.id.numberPicker);
        np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np.setMinValue(1);
        np.setMaxValue(1000);
        np.setWrapSelectorWheel(true);

        final TabActivity _activity = (TabActivity) getActivity();

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.add_product)

                // Confirm button
                .setPositiveButton(R.string.confirm,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //((ProductDetailActivity)getActivity()).doPositiveClick();

                                if (Integer.parseInt(product_stock) < np.getValue()) {
                                    getDialog().dismiss();
                                    // New dialog
                                    View view = inflater.inflate(R.layout.fragment_not_enough_stock, null);

                                    System.out.println("getActivity() = " + getActivity());
                                    // Dialog that informs that there is not enough stock
                                    new AlertDialog.Builder(getActivity())
                                            .setView(view)
                                            .setTitle("Producto sin stock suficiente")

                                            // Confirm button
                                            .setPositiveButton(R.string.confirm,
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int whichButton) {
                                                            createOrderCall(_activity, product_id, _order.getId(), new Integer(np.getValue()).toString());
                                                        }
                                                    })
                                            // Cancel button
                                            .setNegativeButton(R.string.cancel,
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int whichButton) {
                                                            dialog.dismiss();
                                                        }
                                                    }
                                            ).create().show();


                                } else {
                                    createOrderCall((TabActivity) getActivity(), product_id, _order.getId(), new Integer(np.getValue()).toString());
                                }
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


    public void createOrderCall(TabActivity tabsAct, String idProd, String idOrd, String cantidad ){
        final TabActivity _tabsAct = tabsAct;
        OrderService os = OrderService.getInstance();
        Call<Order> call = os.order.addProductToOrder(idProd, idOrd, cantidad);

        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                Order order = response.body();
                _tabsAct.productAdded();
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                //Aca tenemos que agregar el msj de error a mostrar...
            }
        });
    }

}
