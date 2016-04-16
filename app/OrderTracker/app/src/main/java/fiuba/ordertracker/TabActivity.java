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
                   ProductCategoryListFragment.OnFragmentInteractionListener,
                   ProductListFragment.OnFragmentInteractionListener,
                   ProductDetailFragment.OnFragmentInteractionListener,
                   OrderContainerFragment.OnFragmentInteractionListener,
                   OrderListFragment.OnFragmentInteractionListener
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
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
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
        System.out.println("*********** onBackPressed() **************");
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
}
