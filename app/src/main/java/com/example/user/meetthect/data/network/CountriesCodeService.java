package com.example.user.meetthect.data.network;

import com.example.user.meetthect.data.model.CurrencyCode;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by User on 3/14/2018.
 */

public interface CountriesCodeService {
    String BASE_URL = "https://restcountries.eu/rest/v2/";

    @GET("./alpha")
    Call<List<CurrencyCode>> getResponse(@Query("codes") String tags);
}
