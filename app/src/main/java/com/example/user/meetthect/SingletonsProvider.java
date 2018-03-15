package com.example.user.meetthect;

import com.example.user.meetthect.data.network.CountriesCodeService;
import com.example.user.meetthect.data.network.CurrencyService;
import com.google.gson.Gson;

/**
 * Created by artyom on 10/03/2018.
 */

public interface SingletonsProvider {

    //    LocalDatabase getLocalDatabase();
//
    CountriesCodeService getCountriesCodeService();
    CurrencyService getCurrencyService();
    Gson getGson();

}
