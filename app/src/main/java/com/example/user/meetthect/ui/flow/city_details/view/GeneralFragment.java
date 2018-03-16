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
import com.example.user.meetthect.data.model.ConvertedCurrency;
import com.example.user.meetthect.data.model.CurrencyCode;
import com.example.user.meetthect.data.network.CountriesCodeService;
import com.example.user.meetthect.data.network.CurrencyService;
import com.kwabenaberko.openweathermaplib.Units;
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;
import com.kwabenaberko.openweathermaplib.models.currentweather.CurrentWeather;

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
    private TextView tvCurrencyValue;

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
        tvCurrencyValue = view.findViewById(R.id.currency_value);

        if (mCity != null) {
            cityTitle.setText(mCity.getCityName());
            getWeatherInfo();
            getLanguageInfo();
            getElectricityInfo();
            getCurrencyInfo();
            getTimeInfo();
        }

        return view;
    }

    private void getCurrencyInfo() {
        CountriesCodeService countriesCodeService = Injector.getInstance().getCountriesCodeService();
        String countryCode = mCity.getIso2();
        countriesCodeService.getResponse(countryCode).enqueue(new Callback<List<CurrencyCode>>() {
            @Override
            public void onResponse(Call<List<CurrencyCode>> call, final Response<List<CurrencyCode>> currencyCodeResponse) {
                String currencyCode = currencyCodeResponse.body().get(0).getCurrencies().get(0).getCode();
                CurrencyService currencyService = Injector.getInstance().getCurrencyService();
                currencyService.getResponse("USD").enqueue(new Callback<ConvertedCurrency>() {
                    @Override
                    public void onResponse(Call<ConvertedCurrency> call, Response<ConvertedCurrency> convertedCurrencyResponse) {
                        CurrencyCode currencyCode = currencyCodeResponse.body().get(0);
                        ConvertedCurrency convertedCurrency = convertedCurrencyResponse.body();

                        String currencyCodeString = currencyCode.getCurrencies().get(0).getCode();
                        String converetedCurrency = getConveretedCurrency(convertedCurrency.getRates(), currencyCodeString);
                        tvCurrencyValue.setText("1" + "USD" + " = " + converetedCurrency + currencyCodeString);
                    }

                    @Override
                    public void onFailure(Call<ConvertedCurrency> call, Throwable t) {
                        Timber.e(CityActivity.class.getName(), t.getMessage());

                    }
                });

            }

            @Override
            public void onFailure(Call<List<CurrencyCode>> call, Throwable t) {
                Timber.e(CityActivity.class.getName(), t.getMessage());
            }
        });
        CurrencyService currencyService = Injector.getInstance().getCurrencyService();
    }

    private String getConveretedCurrency(ConvertedCurrency.Rates rates, String currencyCodeString) {
        String rate = "";
        switch (currencyCodeString) {
            case "AUD":
                rate = String.valueOf(rates.getAUD());
                break;
            case "BRL":
                rate = String.valueOf(rates.getBRL());

                break;
            case "CAD":
                rate = String.valueOf(rates.getCAD());

                break;
            case "CNY":
                rate = String.valueOf(rates.getCNY());

                break;
            case "GBP":
                rate = String.valueOf(rates.getGBP());

                break;
            case "EUR":
                rate = String.valueOf(rates.getEUR());

                break;
            case "ILS":
                rate = String.valueOf(rates.getILS());

                break;
                default:
                    rate = String.valueOf(rates.getUSD());
                    break;

        }
        return rate;
    }

    private void getTimeInfo() {
        mCity.getCountry();
    }

    private void getElectricityInfo() {

    }

    private void getLanguageInfo() {
        CountriesCodeService countriesCodeService = Injector.getInstance().getCountriesCodeService();
        countriesCodeService.getResponse(mCity.getIso2()).enqueue(new Callback<List<CurrencyCode>>() {
            @Override
            public void onResponse(Call<List<CurrencyCode>> call, final Response<List<CurrencyCode>> currencyCodeResponse) {
                String language = currencyCodeResponse.body().get(0).getLanguages().get(0).getName();
                txLanguageValue.setText(language);

//                Glide.with(getActivity())
//                        .load(currencyCodeResponse.body().get(0).getFlag())
//                        .placeholder(R.drawable.city_inside_languge)
//                        .error(getActivity()R.drawable.ic_launcher_background)
//                        .into(ivLanguageIcon);

                String region = currencyCodeResponse.body().get(0).getRegion();
                String capital = currencyCodeResponse.body().get(0).getCapital();

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
                if (isAdded()) {
                    double excatTemp = currentWeather.getMain().getTemp();
                    int temp = (int) Math.round(excatTemp);

                    if (temp < 12) {
                        txWeatherValue.setTextColor(getResources().getColor(R.color.weatherColorCold));
                        txWeatherValue.setText(temp + getString(R.string.celzius_sign) + " " + getString(R.string.weather_cold));
                        ivWeather.setImageDrawable(getResources().getDrawable(R.drawable.city_inside_weather_cold));
                    } else if (temp >= 12 && temp <= 21) {
                        txWeatherValue.setTextColor(getResources().getColor(R.color.weatherColorWarm));
                        txWeatherValue.setText(temp + getString(R.string.celzius_sign) + " " + getString(R.string.weather_warm));
                        ivWeather.setImageDrawable(getResources().getDrawable(R.drawable.city_inside_weather_mid));

                    } else if (temp > 21) {
                        txWeatherValue.setTextColor(getResources().getColor(R.color.weatherColorHot));
                        txWeatherValue.setText(temp + getString(R.string.celzius_sign) + " " + getString(R.string.weather_hot));
                        ivWeather.setImageDrawable(getResources().getDrawable(R.drawable.city_inside_weather_hot));
                    }
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Timber.e(TAG, throwable.getMessage());
            }
        });
    }
}
