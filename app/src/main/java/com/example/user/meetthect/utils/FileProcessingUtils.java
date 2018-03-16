package com.example.user.meetthect.utils;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.user.meetthect.data.model.City;
import com.example.user.meetthect.data.model.CountriesElectricity;
import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import timber.log.Timber;

/**
 * Created by User on 3/7/2018.
 */

public class FileProcessingUtils {
    Context mContext;

    public FileProcessingUtils(Context appContext) {
        this.mContext = appContext;
    }

    @SuppressLint("TimberArgCount")
    public List<City> parseCitiesCSV() {
        Timber.v("Parsing cities CSV");
        List<City> list = new ArrayList<>();
        try {
            CSVReader reader = new CSVReader(
                    new InputStreamReader(mContext.getAssets().open("world_cities.csv")));
            for (; ; ) {
                String[] next = reader.readNext();

                if (next != null) {
                    City city = City.builder()
                            .withCityName(next[0])
                            .withLat(Double.valueOf(next[2]))
                            .withLon(Double.valueOf(next[3]))
                            .withCountry(next[5])
                            .withIso2(next[6])
                            .build();
                    list.add(city);
                } else {
                    break;
                }
            }

            return list;
        } catch (IOException e) {
            Timber.e("Failed to parse Cities CSV file ", e);
            return Collections.emptyList();
        }
    }

//    @SuppressLint("TimberArgCount")
//    public List<CountriesElectricity> parseSocketAndVoltageByCountry() {
//        Timber.v("Parsing cities SocketAndVoltageByCountry");
//        List<CountriesElectricity> list = new ArrayList<>();
//        try {
//            CSVReader reader = new CSVReader(
//                    new InputStreamReader(mContext.getAssets().open("socket_and_voltage_by_country.csv")));
//            for (; ; ) {
//                String[] next = reader.readNext();
//
//                if (next != null) {
//                    CountriesElectricity countriesElectricity = CountriesElectricity.Builder()
//                                    .withCountryName(next[0])
//                                    .withVoltage(next[2])
//                                    .withPlug[3]
//                                    .build();
//                    list.add(countriesElectricity);
//                } else {
//                    break;
//                }
//            }
//
//            return list;
//        } catch (IOException e) {
//            Timber.e("Failed to parse Cities CSV file ", e);
//            return Collections.emptyList();
//        }
//    }
}