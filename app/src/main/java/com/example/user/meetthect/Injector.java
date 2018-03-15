package com.example.user.meetthect;

import android.app.Application;
import android.content.Context;

import com.example.user.meetthect.data.network.CountriesCodeService;
import com.example.user.meetthect.data.network.CurrencyService;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by artyom on 09/03/2018.
 * This is the main class that holds all Singletons
 */

public class Injector implements SingletonsProvider {

    private static final Injector ourInstance = new Injector();

    private Context mAppContext;

    private CountriesCodeService mCountriesCodeService;

    private CurrencyService currencyService;
//
//    private LocalDatabase mLocalDatabase;

    private Gson mGson;

    private Injector() {

    }

    public static Injector getInstance() {
        return ourInstance;
    }

    public void init(Application application) {
        mAppContext = application.getApplicationContext();
    }

    @Override
    public CountriesCodeService getCountriesCodeService() {
        if (mCountriesCodeService == null) {
            mCountriesCodeService = initNetworkService();
        }

        return mCountriesCodeService;
    }

    @Override
    public CurrencyService getCurrencyService() {
        if (currencyService == null) {
            currencyService = initCurrencyService();
        }

        return currencyService;
    }

    private CurrencyService initCurrencyService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .baseUrl(CurrencyService.BASE_URL)
                .client(new OkHttpClient.Builder().addInterceptor(interceptor).build())
                .build()
                .create(CurrencyService.class);
    }

    private CountriesCodeService initNetworkService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .baseUrl(CountriesCodeService.BASE_URL)
                .client(new OkHttpClient.Builder().addInterceptor(interceptor).build())
                .build()
                .create(CountriesCodeService.class);
    }

    public Gson getGson() {
        if (mGson == null) {
            mGson = new Gson();
        }

        return mGson;
    }

}
