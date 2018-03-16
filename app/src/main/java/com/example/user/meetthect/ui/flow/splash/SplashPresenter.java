package com.example.user.meetthect.ui.flow.splash;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Julia on 3/15/2018.
 */

public class SplashPresenter {

    public String getCountryName(Context context, double lat, double lng) {
        String countryName = null;
        Geocoder gcd = new Geocoder(context, Locale.getDefault());

        List<Address> addresses = null;
        try {
            addresses = gcd.getFromLocation(lat, lng, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (addresses !=null && addresses.size() > 0) {
            countryName = addresses.get(0).getCountryName();
        }

        return countryName;

    }
}
