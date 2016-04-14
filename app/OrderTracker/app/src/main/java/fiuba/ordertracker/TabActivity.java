package fiuba.ordertracker;

import android.app.FragmentTransaction;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class TabActivity extends AppCompatActivity
        implements ProductsFragment.OnFragmentInteractionListener,
                   OrderListFragment.OnFragmentInteractionListener,
                   ProductCategoryListFragment.OnFragmentInteractionListener,
                   ProductListFragment.OnFragmentInteractionListener
{

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        /*//Notice that I am adding this 1st fragment in the Activity and not the XML
        ProductCategoryListFragment mainFragment = new ProductCategoryListFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.Maincontainer, mainFragment);
        ft.commit();*/
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ProductsFragment(), "PRODUCTOS");
        adapter.addFragment(new OrderListFragment(), "PEDIDO");
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

    class ViewPagerAdapter extends FragmentPagerAdapter {
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


    public void changeFragment(View view) {
        Fragment mFragment = new ProductListFragment();
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //Replacing using the id of the container and not the fragment itself
        ft.replace(R.id.Maincontainer, mFragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}
