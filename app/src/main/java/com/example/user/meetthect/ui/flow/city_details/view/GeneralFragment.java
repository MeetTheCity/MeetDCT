package com.example.user.meetthect.ui.flow.city_details.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.user.meetthect.Injector;
import com.example.user.meetthect.R;
import com.example.user.meetthect.data.model.City;
import com.example.user.meetthect.data.model.CurrencyCode;
import com.example.user.meetthect.data.network.CountriesCodeService;
import com.kwabenaberko.openweathermaplib.Units;
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;
import com.kwabenaberko.openweathermaplib.models.currentweather.CurrentWeather;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.text.SimpleDateFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by User on 3/13/2018.
 */

public class GeneralFragment extends Fragment {
    public static final String TAG = CityActivity.class.getSimpleName();
    public static final String CITY = "city";
    public static final int VIEW_LOADING = 0;
    public static final int VIEW_CONTACT = 1;

    private City mCity;
    private ViewFlipper mViewFlipper;
    private TextView txWeatherValue;
    private ImageView ivWeather;
    private ImageView ivLanguageIcon;
    private TextView txLanguageValue;
    private TextView tvTimeValue;

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

    private void showLoading(boolean show) {
        mViewFlipper.setDisplayedChild(show ? VIEW_LOADING : VIEW_CONTACT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_general, container, false);

        TextView cityTitle = view.findViewById(R.id.city_title);

        mViewFlipper = view.findViewById(R.id.details_view_flipper);

        showLoading(true);

        txWeatherValue = view.findViewById(R.id.weather_value);
        ivWeather = view.findViewById(R.id.weather_icon);
        txLanguageValue = view.findViewById(R.id.language_value);
        ivLanguageIcon = view.findViewById(R.id.language_icon);
        tvTimeValue = view.findViewById(R.id.time_value);

        if (mCity != null) {
            cityTitle.setText(mCity.getCityName());
            getWeatherInfo();
            getLanguageInfoAndTime();
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

    private void getLanguageInfoAndTime() {
        CountriesCodeService countriesCodeService = Injector.getInstance().getCountriesCodeService();
        countriesCodeService.getResponse(mCity.getIso2()).enqueue(new Callback<List<CurrencyCode>>() {
            @Override
            public void onResponse(Call<List<CurrencyCode>> call, final Response<List<CurrencyCode>> currencyCodeResponse) {
                String language = currencyCodeResponse.body().get(0).getDemonym();
                txLanguageValue.setText(language);

//                Glide.with(getActivity())
//                        .load(currencyCodeResponse.body().get(0).getFlag())
//                        .placeholder(R.drawable.city_inside_languge)
//                        .error(getActivity()R.drawable.ic_launcher_background)
//                        .into(ivLanguageIcon);

                String region = currencyCodeResponse.body().get(0).getRegion();
                String capital = currencyCodeResponse.body().get(0).getCapital();
                DateTime dt = new DateTime();
                DateTime dtForCity = dt.withZone(DateTimeZone.forID(region + "/" + capital));

                SimpleDateFormat fmt = new SimpleDateFormat("hh:mm");
                fmt.format(dtForCity);

                tvTimeValue.setText("");

                showLoading(false);
            }

            @Override
            public void onFailure(Call<List<CurrencyCode>> call, Throwable t) {

            }
        });
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
                double temp = currentWeather.getMain().getTemp();
                if (temp < 12) {
                    txWeatherValue.setTextColor(getResources().getColor(R.color.weatherColorCold));
                    txWeatherValue.setText(temp + getString(R.string.celzius_sign) + " " + getString(R.string.weather_cold));
                } else if (temp >= 12 && temp <= 21) {
                    txWeatherValue.setTextColor(getResources().getColor(R.color.weatherColorWarm));
                    txWeatherValue.setText(temp + getString(R.string.celzius_sign) + " " + getString(R.string.weather_warm));

                } else if (temp > 21) {
                    txWeatherValue.setTextColor(getResources().getColor(R.color.weatherColorHot));
                    txWeatherValue.setText(temp + getString(R.string.celzius_sign) + " " + getString(R.string.weather_hot));
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Timber.e(TAG, throwable.getMessage());
            }
        });
    }
}
