package com.example.user.meetthect.data.network;


import com.example.user.meetthect.data.model.ConvertedCurrency;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Shahar.Wienrib on 27/02/2018.
 */

public interface CurrencyService {
    String BASE_URL = "https://api.fixer.io/";

    @GET("/latest")
    Call<ConvertedCurrency> getResponse(@Query("base") String tags);
}
