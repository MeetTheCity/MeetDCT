package com.example.user.meetthect.data.model;

import android.os.AsyncTask;
import android.util.Log;

import com.example.user.meetthect.data.network.GoogleMapsDataParser;
import com.example.user.meetthect.data.network.GoogleMapsDownloadUrl;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Shahar.Wienrib on 11/03/2018.
 */

public class GetNearbyPlacesData extends AsyncTask<Object, String, String> {
    public interface GetNearByPlacesDataListener {
        void onDataReceived(List<HashMap<String, String>> nearByPlaceList);
    }

    GetNearByPlacesDataListener listener;
    String googlePlacesData;
    GoogleMap mMap;
    String url;

    public void setListener(GetNearByPlacesDataListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Object... objects) {
        url = (String) objects[0];

        Log.d("Sending:", url);

        GoogleMapsDownloadUrl downloadUrl = new GoogleMapsDownloadUrl();
        try {
            googlePlacesData = downloadUrl.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s) {
        List<HashMap<String, String>> nearByPlceList = null;
        GoogleMapsDataParser parser = new GoogleMapsDataParser();
        nearByPlceList = parser.parse(s);

        listener.onDataReceived(nearByPlceList);

    }
}
