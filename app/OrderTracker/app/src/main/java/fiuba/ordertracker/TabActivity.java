package fiuba.ordertracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fiuba.ordertracker.helpers.Constants;
import fiuba.ordertracker.pojo.Order;
import fiuba.ordertracker.services.OrderService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabActivity extends AppCompatActivity
        implements ProductsFragment.OnFragmentInteractionListener,
                   ProductCategoryListFragment.OnFragmentInteractionListener,
                   ProductListFragment.OnFragmentInteractionListener,
                   ProductDetailFragment.OnFragmentInteractionListener,
                   OrderContainerFragment.OnFragmentInteractionListener,
                   OrderListFragment.OnFragmentInteractionListener
{

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    //private ProgressBar progressBar;
    public String clientId;
    private Order activeOrder;
    private AddProductSubscriptor subscriptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        subscriptor = new AddProductSubscriptor();


        Intent i = this.getIntent();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle(i.getStringExtra("clientName"));

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //progressBar = (ProgressBar) findViewById(R.id.progressBar);
        //progressBar.setVisibility(View.VISIBLE);

        //setProgressBarIndeterminateVisibility(true);

        /*viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);*/


        this.clientId = i.getStringExtra("clientID");

        final OrderService os = OrderService.getInstance();
        Integer clientIntID = new Integer(this.clientId);

        Call<Order> call = os.order.getActiveProductOrderByClient(clientIntID.intValue());

        final TabActivity self_ = this;
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                Order activeOrder;
                //progressBar.setVisibility(View.GONE);
                if(response.code() == 500){
                    //no tiene pedido activo... entonces creo uno
                    self_.createOrderCall(os);

                }else {
                    self_.setActiveOrder(response.body());
                    self_.initializeTabs();
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                //Aca tenemos que agregar el msj de error a mostrar...
                TextView textNoClients = (TextView) findViewById(R.id.text_no_products);
                textNoClients.setText("Hubo un error al cargar el pedido, por favor reintente más tarde");
                textNoClients.setVisibility(View.VISIBLE);
                //progressBar.setVisibility(View.GONE);
            }
        });

    }

    private void initializeTabs() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    public Order getActiveOrder() {
        return activeOrder;
    }

    public void setActiveOrder(Order activeOrder) {
        this.activeOrder = activeOrder;
    }

    public void createOrderCall(final OrderService os){
        final TabActivity self_ = this;
        SharedPreferences pref = getApplicationContext().getSharedPreferences("OrderTrackerPref", 0);
        int idVendedor = pref.getInt("id", 0);

        Call<Order> call = os.order.createOrder(self_.clientId, Constants.ACTIVE_STATE, "se crea pedido", Integer.valueOf(idVendedor).toString());

        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                Order order = response.body();
                self_.setActiveOrder(order);
                self_.initializeTabs();
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                //Aca tenemos que agregar el msj de error a mostrar...
                TextView textNoClients = (TextView) findViewById(R.id.text_no_products);
                textNoClients.setText("Hubo un error al cargar el pedido, por favor reintente más tarde");
                textNoClients.setVisibility(View.VISIBLE);
                //progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        ProductsFragment pf = new ProductsFragment();

        Boolean orderConfirmed = Constants.CONFIRM_STATE.equals(this.activeOrder.getIdEstado()) ? true : false;

        // If the order wasn't confirmed yet, the products catalog is available.
        if(!orderConfirmed)
            adapter.addFragment(new ProductsFragment(), "PRODUCTOS");

        adapter.addFragment(new OrderContainerFragment(), "PEDIDO");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onProductsFragmentInteraction(Uri uri) {

    }

    @Override
    public void onOrderListFragmentInteraction(Uri uri) {

    }

    @Override
    public void onProductCategoryListFragmentInteraction(Uri uri) {

    }

    @Override
    public void onProductListFragmentInteraction(Uri uri) {

    }

    @Override
    public void onProductDetailFragmentInteraction(Uri uri) {

    }

    @Override
    public void onOrderContainerFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        // if there is a fragment and the back stack of this fragment is not empty,
        // then we have to emulate the 'onBackPressed' behaviour (BUG) :(
        FragmentManager fm = getSupportFragmentManager();
        for (Fragment frag : fm.getFragments()) {
            if (frag.isVisible()) {
                FragmentManager childFm = frag.getChildFragmentManager();
                if (childFm.getBackStackEntryCount() > 0) {
                    childFm.popBackStack();
                    return;
                }
            }
        }
        super.onBackPressed();
    }


    class ViewPagerAdapter extends FragmentPagerAdapter  {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }



    }

    public AddProductSubscriptor getSubscriptor() {
        return subscriptor;
    }


    public void productAdded(){

        this.subscriptor.forceChange();

        synchronized(this.subscriptor){
            this.subscriptor.notifyObservers();
        }
    }
}
