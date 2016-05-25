package fiuba.ordertracker;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.content.Intent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fiuba.ordertracker.helpers.FiltersHelper;
import fiuba.ordertracker.helpers.Fonts;
import fiuba.ordertracker.pojo.Client;
import fiuba.ordertracker.pojo.Product;
import fiuba.ordertracker.services.ClientService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientListActivity extends AppCompatActivity
        implements ClientListFragment.OnFragmentInteractionListener {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private RecyclerView recyclerView;
    private ClientListAdapter clientListAdapter;
    private ProgressBar progressBar;
    private Intent intent;

    private Boolean filterWasUsed = false;
    boolean resumeFromCreate = false;

    public static final int OPEN_NEW_ACTIVITY = 123456;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_list);

        this.resumeFromCreate = true;

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Toolbar
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle(getString(R.string.activity_client_list));

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        // Set current tab
        Calendar calendar = Calendar.getInstance();
        viewPager.setCurrentItem(getTabForCurrentDay(calendar.get(Calendar.DAY_OF_WEEK)));
        viewPager.getCurrentItem();

        tabLayout.getTabAt(7).setIcon(R.drawable.ic_call_split_white_24dp);

        final SearchView razonFilterView = (SearchView) findViewById(R.id.searchView);
        final EditText clientCodeFilterView = (EditText) findViewById(R.id.editText_client_code);
        Fonts.changeSearchViewTextColorBlack(clientCodeFilterView);
        Fonts.changeSearchViewTextColorBlack(razonFilterView);
        final ClientListActivity _this = this;

        razonFilterView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals(""))
                    onQueryTextSubmit("");
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                _this.filterWasUsed = true;
                filterClientsInCurrentTab();
                return false;
            }

        });

        clientCodeFilterView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_ACTION_DONE) || ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_DOWN))) {
                    _this.filterWasUsed = true;
                    filterClientsInCurrentTab();
                    return true;
                } else {
                    return false;
                }

            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        // Current date
        Calendar calendar = Calendar.getInstance();

        // Calculate current week's Sunday
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
            calendar.add(Calendar.DAY_OF_WEEK, -1);

        // Set days of the current week for each tab
        adapter.addFragment(ClientListFragment.newInstance(calendar), "D");
        calendar.add(Calendar.DATE, 1);
        adapter.addFragment(ClientListFragment.newInstance(calendar), "L");
        calendar.add(Calendar.DATE, 1);
        adapter.addFragment(ClientListFragment.newInstance(calendar), "M");
        calendar.add(Calendar.DATE, 1);
        adapter.addFragment(ClientListFragment.newInstance(calendar), "M");
        calendar.add(Calendar.DATE, 1);
        adapter.addFragment(ClientListFragment.newInstance(calendar), "J");
        calendar.add(Calendar.DATE, 1);
        adapter.addFragment(ClientListFragment.newInstance(calendar), "V");
        calendar.add(Calendar.DATE, 1);
        adapter.addFragment(ClientListFragment.newInstance(calendar), "S");
        adapter.addFragment(ClientListFragment.newInstance(null), "");


        viewPager.setAdapter(adapter);

        final ClientListActivity _this = this;

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                _this.filterClientsInCurrentTab();
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });
    }

    /**
     * Returns the index of the tab to be selected (0 - 6)
     *
     * @param dayOfWeek
     * @return the index of the tab to be selected
     */
    private int getTabForCurrentDay(int dayOfWeek) {
        //int intDayOfWeek = Integer.valueOf(dayOfWeek);
        if (dayOfWeek > 0) {
            return dayOfWeek - 1;
        } else {
            return 6;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    private void toolbar_filter() {

    }

    public void onClickShowHideFilters(View view) {

        LinearLayout button_filter = (LinearLayout) findViewById(R.id.filters_container);

        if (button_filter.getVisibility() == View.GONE)
            button_filter.setVisibility(View.VISIBLE);
        else {
            button_filter.setVisibility(View.GONE);

            EditText editText_brand = (EditText) findViewById(R.id.editText_client_code);
            editText_brand.clearFocus();

            EditText editText_client_code = (EditText) findViewById(R.id.editText_client_code);
            editText_client_code.clearFocus();
        }
    }

    // When the user clicks the "Ver carrito" button
    public void onClickViewShoppingCart(View view) {
        System.out.println("**** View shopping cart ****");
    }

    public ClientListFragment getCurrentTab() {
        ClientListFragment currentTabFragment = (ClientListFragment) this.viewPager.getAdapter().instantiateItem(this.viewPager, this.viewPager.getCurrentItem());
        return currentTabFragment;
    }

    public Map<String, String> getFiltersValues() {
        String codeFilter = ((EditText) findViewById(R.id.editText_client_code)).getText().toString();
        String nameFilter = ((SearchView) findViewById(R.id.searchView)).getQuery().toString();

        Map<String, String> filtersValues = new HashMap<String, String>();
        filtersValues.put("code", codeFilter);
        filtersValues.put("name", nameFilter);

        return filtersValues;
    }

    public void filterClientsInCurrentTab() {
        if(!this.filterWasUsed) return;
        this.getCurrentTab().executeFiltering(this.getFiltersValues());
    }

    // Call when the user clicks the go map button
    public void onClickGoMap(View view) {

        Intent intent = new Intent(view.getContext(), ClientsMapActivity.class);

        Bundle b = new Bundle();
        b.putString("clientID", null);
        b.putString("agendaDate", this.getCurrentTab().getTabDate());
        intent.putExtras(b);

        view.getContext().startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Check if is the first time the activity is created to avoid
        // multiple creation of the fragments.
        if(this.resumeFromCreate) {
            this.resumeFromCreate = false;
            return;
        }

        if(this.getCurrentTab() != null) {
            // Reload the clients list
            this.getCurrentTab().reloadClients();
            // Apply the filters again
            this.getCurrentTab().executeFiltering(this.getFiltersValues());
        }
    }
}
