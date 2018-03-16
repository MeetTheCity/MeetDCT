package com.example.user.meetthect.ui.flow.city_details.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.user.meetthect.R;
import com.example.user.meetthect.base.BaseActivity;
import com.example.user.meetthect.data.model.City;
import com.example.user.meetthect.ui.flow.money.view.SpeakFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by User on 3/13/2018.
 */

public class CityActivity extends BaseActivity implements RestaurantsFragment.RestaurantsFragmentListener{

    public static final String CITY = "city";
    public static final int NAVIGATOR_FRAGMENT_POSITION = 4;

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private City mCity;

    public static <T extends Activity> Intent getIntent(T from,
                                                        City city) {
        Intent intent = new Intent(from, CityActivity.class);
        intent.putExtra(CITY, city);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);


        if (getIntent().getExtras() != null) {
            mCity = getIntent().getParcelableExtra(CITY);
        }

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        TextView title = mToolbar.findViewById(R.id.city_title);
        title.setText(mCity.getCityName());

        ActionBar supportActionBar = getSupportActionBar();

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mViewPager = findViewById(R.id.viewpager);
        final PagerAdapter adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return mTabLayout.getTabCount();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return false;
            }
        };

        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        setupViewPager(mViewPager);
//        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(GeneralFragment.newInstance(mCity));
        adapter.addFragment(SpeakFragment.newInstance(mCity));
        adapter.addFragment(RestaurantsFragment.newInstance(mCity));
        adapter.addFragment(AttractionsFragment.newInstance(mCity));
        adapter.addFragment(NavigatorFragment.newInstance(mCity));

        viewPager.setAdapter(adapter);
    }

    @Override
    public void GoToTransportationFragment(HashMap<String, String> restaurantInfoHashMap) {
        mViewPager.setCurrentItem(NAVIGATOR_FRAGMENT_POSITION);
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

        public void addFragment(Fragment fragment) {
            mFragmentList.add(fragment);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
