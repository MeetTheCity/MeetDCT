package com.example.user.meetthect.ui.flow.city_details.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.meetthect.R;
import com.example.user.meetthect.data.model.City;
import com.kwabenaberko.openweathermaplib.Units;
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;
import com.kwabenaberko.openweathermaplib.models.currentweather.CurrentWeather;

import timber.log.Timber;

/**
 * Created by User on 3/13/2018.
 */

public class GeneralFragment extends Fragment {
    public static final String TAG = CityActivity.class.getSimpleName();
    public static final String CITY = "city";

    private TextView txWeatherValue;
    private City mCity;

    public static GeneralFragment newInstance(City city) {
        GeneralFragment generalFragment = new GeneralFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(CITY, city);
        generalFragment.setArguments(bundle);

        return generalFragment;
    }

    public GeneralFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCity = getArguments().getParcelable(CITY);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_general, container, false);
        // Inflate the layout for this fragment


        txWeatherValue = view.findViewById(R.id.weather_value);

        if (mCity != null) {
            getWeatherInfo();
            getLanguageInfo();
            getElectricityInfo();
            getTimeInfo();
        }

        return view;
    }

    private void getTimeInfo() {
        mCity.getCountry();
    }

    private void getElectricityInfo() {

    }

    private void getLanguageInfo() {

    }

    private void getWeatherInfo() {
        //Instantiate class
        OpenWeatherMapHelper helper = new OpenWeatherMapHelper();

        //Set API KEY
        helper.setApiKey(getString(R.string.OPEN_WEATHER_MAP_API_KEY));
        //Set Units
        helper.setUnits(Units.METRIC);

        /*
        This Example Only Shows how to get current weather by city name
        Check the docs for more methods [https://github.com/KwabenBerko/OpenWeatherMap-Android-Library/]
        */
        helper.getCurrentWeatherByCityName(mCity.getCityName(), new OpenWeatherMapHelper.CurrentWeatherCallback() {
            @Override
            public void onSuccess(CurrentWeather currentWeather) {
                txWeatherValue.setText(currentWeather.getMain().getTemp() + getString(R.string.celzius_sign));
            }

            @Override
            public void onFailure(Throwable throwable) {
                Timber.e(TAG, throwable.getMessage());
            }
        });
    }
}
