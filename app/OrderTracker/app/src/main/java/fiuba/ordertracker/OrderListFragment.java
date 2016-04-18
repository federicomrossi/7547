package fiuba.ordertracker;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Observable;
import java.util.Observer;


import fiuba.ordertracker.helpers.Constants;
import fiuba.ordertracker.pojo.Order;
import fiuba.ordertracker.pojo.OrderProduct;
import fiuba.ordertracker.services.OrderService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OrderListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OrderListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderListFragment extends Fragment  implements Observer {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;

    private RecyclerView recyclerView;
    private OrderProductListAdapter orderProductListAdapter;
    private ProgressBar progressBar;

    private OnFragmentInteractionListener mListener;

    public OrderListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderListFragment newInstance(String param1, String param2) {
        OrderListFragment fragment = new OrderListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TabActivity self_ = (TabActivity)getActivity();
        self_.getSubscriptor().addObserver(this);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_order_list, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        getActivity().setProgressBarIndeterminateVisibility(true);

        // Products list
        recyclerView = (RecyclerView) view.findViewById(R.id.productsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        // Load OrderProduct list

        this.getProductsFromActiveOrderCall();

        // Confirmation button
        final FragmentActivity self_ = getActivity();
        final Fragment _parentFragment = this.getParentFragment();
        Button buttonConfirm = (Button) view.findViewById(R.id.buttonConfirmOrder);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("************* getItemCount()=" + orderProductListAdapter.getItemCount());
                if(orderProductListAdapter.getItemCount() > 0){
                    new AlertDialog.Builder(self_)
                            .setTitle("Confirmar pedido")
                            .setMessage("¿Está seguro de confirmar el pedido?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    confirmOrderCall();
                                }
                            })
                            .setNegativeButton("Cancelar", null).show();

                }else{
                    System.out.println("************* ELSE no es posible confirmar");
                    new AlertDialog.Builder(self_)
                            .setTitle("No es posible confirmar")
                            .setMessage("El pedido está vacío y no puede ser enviado")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton("Ok", null).show();
                }
            }
        });

        // Discard button
        Button buttonDiscard = (Button) view.findViewById(R.id.buttonDiscardOrder);
        buttonDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(self_)
                        .setTitle("Descartar pedido")
                        .setMessage("¿Está seguro de descartar el pedido?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                Toast.makeText(self_, "Se ha descartado el pedido satisfactoriamente", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancelar", null).show();
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onOrderListFragmentInteraction(uri);
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


    @Override
    public void onStart() {
        getProductsFromActiveOrderCall();
        super.onStart();

    }


    public void confirmOrderCall(){
        final TabActivity tabsAct = (TabActivity) getActivity();
        OrderService os = OrderService.getInstance();
        Call<Order> call = os.order.editOrder(tabsAct.getActiveOrder().getId());

        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.code() == 500) {
                    //no pudo actualizarlo por falta de stock hay q mostrar el popup
                    new AlertDialog.Builder(tabsAct)
                            .setTitle("El pedido contiene productos sin stock")
                            .setMessage("¿Desea realizar el pedido de todas formas?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton("Proseguir", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    // Force the order status to Completed (Confirmed)
                                    //order.setIdEstado(Constants.COMPLETED_STATE);
                                    Toast.makeText(tabsAct, "Se ha confirmado el pedido satisfactoriamente",
                                            Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("Modificar", null).show();
                } else {
                    final Order order = response.body();

                    if (order != null) {
                        if (order.getIdEstado().equals(Constants.COMPLETED_STATE)) {
                            //aca es completo entonces hay que mostralo como confirmado y se cambia el active order a este pero esta fuera de la entrega asi que no hace nada por ahora
                            //tabsAct.setActiveOrder(order);
                            Toast.makeText(tabsAct, "Se ha confirmado el pedido satisfactoriamente", Toast.LENGTH_SHORT).show();
                        } else {

                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                //Aca tenemos que agregar el msj de error a mostrar...
                Toast.makeText(tabsAct, "Error de conexion, intente mas tarde nuevamente", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void getProductsFromActiveOrderCall(){
        // Create a call instance for looking up Retrofit contributors.
        final FragmentActivity self_ = getActivity();
        final Fragment _parentFragment = this.getParentFragment();
        final View _view = view;
        final OrderService os = OrderService.getInstance();
        Call<List<OrderProduct>> call = os.order.getProductsFromActiveOrder(((TabActivity) self_).clientId);

        call.enqueue(new Callback<List<OrderProduct>>() {

            @Override
            public void onResponse(Call<List<OrderProduct>> call, Response<List<OrderProduct>> response) {
                List<OrderProduct> listProducts = response.body();
                orderProductListAdapter = new OrderProductListAdapter(self_, listProducts, _parentFragment);
                progressBar.setVisibility(View.GONE);

                TextView textNoProducts = (TextView) _view.findViewById(R.id.text_no_products);
                TextView subtotalText = (TextView) _view.findViewById(R.id.textView4);

                if (listProducts.size() == 0) {
                    textNoProducts.setVisibility(View.VISIBLE);
                    subtotalText.setText("$" + String.valueOf(0));
                    textNoProducts.setVisibility(View.VISIBLE);
                } else {
                    float subtotal = 0;
                    for (OrderProduct orderProduct : listProducts) {
                        subtotal += orderProduct.getSubtotal();
                    }
                    subtotalText.setText("$" + String.valueOf(subtotal));
                    textNoProducts.setVisibility(View.GONE);
                }
                recyclerView.setAdapter(orderProductListAdapter);
            }

            @Override
            public void onFailure(Call<List<OrderProduct>> call, Throwable t) {
                TextView textNoProducts = (TextView) _view.findViewById(R.id.text_no_products);
                textNoProducts.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public void update(Observable observable, Object data) {
        System.out.println("notificadoooooo");
        this.getProductsFromActiveOrderCall();
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
        void onOrderListFragmentInteraction(Uri uri);
    }
}
