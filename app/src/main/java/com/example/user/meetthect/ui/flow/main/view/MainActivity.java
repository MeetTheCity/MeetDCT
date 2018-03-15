package com.example.user.meetthect.ui.flow.main.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;

import com.airbnb.lottie.LottieComposition;
import com.example.user.meetthect.R;
import com.example.user.meetthect.base.BaseActivity;
import com.example.user.meetthect.data.model.City;
import com.example.user.meetthect.ui.flow.city_details.view.CityActivity;
import com.example.user.meetthect.utils.FileProcessingUtils;

import java.util.ArrayList;
import java.util.List;

import static com.example.user.meetthect.ui.flow.main.view.CardFragment.CITY;

public class MainActivity extends BaseActivity implements CardFragment.CardFragmentListener {
    private Toolbar mToolbar;

    private ArrayList<City> cities;
    private ArrayList<String> citiesNames;
    private AutoCompleteTextView mSearchAutoComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ViewPager citiesViewPager = findViewById(R.id.viewPager);

        ImageButton searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new OnSearchButtonClicked());

        ImageButton micButton = findViewById(R.id.mic_button);
        micButton.setOnClickListener(new OnMicButtonClicked());


        cities = getCitiesFromFile();
        citiesNames = new ArrayList<>();
        for (City city : cities) {
            citiesNames.add(city.getCityName());
        }

        mSearchAutoComplete = findViewById(R.id.search_auto_complete);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, citiesNames);
        mSearchAutoComplete.setAdapter(adapter);
        mSearchAutoComplete.setOnItemClickListener(new OnSearchAutoCompleteCitySelected());

        CardFragmentPagerAdapter pagerAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager(), dpToPixels(2, this), cities);
        ShadowTransformer fragmentCardShadowTransformer = new ShadowTransformer(citiesViewPager, pagerAdapter);
        fragmentCardShadowTransformer.enableScaling(true);

        citiesViewPager.setAdapter(pagerAdapter);
        citiesViewPager.setPageTransformer(false, fragmentCardShadowTransformer);
        citiesViewPager.setOffscreenPageLimit(3);
    }

    @NonNull
    private ArrayList<City> getCitiesFromFile() {
        FileProcessingUtils fileProcessingUtils = new FileProcessingUtils(this);
        List<City> citiesList = fileProcessingUtils.parseCitiesCSV();
        return new ArrayList<>(citiesList);
    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }


    @Override
    public void onCityClicked(City city) {
        Intent intent = CityActivity.getIntent(this, city);
        intent.putExtra(CITY, city);
        startActivity(intent);
    }

    private class OnSearchButtonClicked implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String selectedCityName = mSearchAutoComplete.getText().toString();
            findSearchedCity(selectedCityName);
        }
    }

    private void findSearchedCity(String selectedCityName) {
        for (int i = 0; i < citiesNames.size(); i++) {
            if (citiesNames.get(i).equals(selectedCityName)) {
                onCityClicked(cities.get(i));
                break;
            }
        }
    }

    private class OnMicButtonClicked implements View.OnClickListener {
        @Override
        public void onClick(View view) {

        }
    }

    private class OnSearchAutoCompleteCitySelected implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
            String selectedCityName = ((AppCompatTextView) arg1).getText().toString();
            findSearchedCity(selectedCityName);
        }
    }
}
