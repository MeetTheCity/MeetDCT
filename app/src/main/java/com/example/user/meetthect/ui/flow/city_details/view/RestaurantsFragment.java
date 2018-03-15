package com.example.user.meetthect.ui.flow.city_details.view;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.meetthect.R;
import com.example.user.meetthect.data.model.City;

/**
 * Created by User on 3/13/2018.
 */

public class RestaurantsFragment extends Fragment {
    public static RestaurantsFragment newInstance(City mCity) {
        RestaurantsFragment restaurantsFragment = new RestaurantsFragment();

        return restaurantsFragment;
    }

    public RestaurantsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_restaurant, container, false);
    }
}
