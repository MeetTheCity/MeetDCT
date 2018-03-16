package com.example.user.meetthect.ui.flow.splash;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.NetworkInfo;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;
import android.location.LocationManager;

import com.example.user.meetthect.R;
import com.example.user.meetthect.ui.flow.main.view.MainActivity;

import timber.log.Timber;

public class SplashActivity extends Activity implements LocationListener {
    private static final int PERMISSION_REQUEST_LOCATION_CODE = 9999;
    private LocationManager locationManager;
    private String provider;
    private TextView latituteField;
    private TextView longitudeField;

    SplashPresenter splashPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        latituteField = (TextView) findViewById(R.id.latituteField);
        longitudeField = (TextView) findViewById(R.id.longitudeField);
        splashPresenter = new SplashPresenter();

        boolean enabled = false;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
            enabled = service
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

        }else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_LOCATION_CODE);
        }

        if (enabled) {
            getLocation();
        }

    }

    public void getLocation(){
        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_LOW);
        provider = locationManager.getBestProvider(criteria, true);

        @SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            //Start intent to Main activity
            Intent intent = MainActivity.getIntent(this, splashPresenter.getCountryName(this,(double) (location.getLatitude()),(double) (location.getLongitude())));
            startActivity(intent);
        } else {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onLocationChanged(Location location) {
        double lat = (double) (location.getLatitude());
        double lng = (double) (location.getLongitude());
        latituteField.setText(String.valueOf(lat));
        longitudeField.setText(String.valueOf(lng));


    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String s) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
    }


}
