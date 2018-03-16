package com.example.user.meetthect.ui.flow.city_details.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewFlipper;

import com.example.user.meetthect.R;
import com.example.user.meetthect.data.model.City;
import com.example.user.meetthect.data.model.GetNearbyPlacesData;
import com.example.user.meetthect.ui.flow.city_details.view.adapters.RestaurantsAdapter;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.HashMap;
import java.util.List;

import static com.example.user.meetthect.ui.flow.city_details.view.GeneralFragment.VIEW_CONTACT;
import static com.example.user.meetthect.ui.flow.city_details.view.GeneralFragment.VIEW_LOADING;

/**
 * Created by User on 3/13/2018.
 */

public class RestaurantsFragment extends Fragment implements GetNearbyPlacesData.GetNearByPlacesDataListener, RestaurantsAdapter.RestaurantsAdapterListener {
    public static final String CITY = "city";
    private static final int PROXIMITY_RADIUS = 1000;

    private GoogleApiClient client;
    private City mCity;
    private RecyclerView mRecycleView;
    private RestaurantsFragmentListener mListener;
    private ViewFlipper mViewFlipper;

    public interface RestaurantsFragmentListener {
        void GoToTransportationFragment(HashMap<String, String> restaurantInfoHashMap);
    }

    public static RestaurantsFragment newInstance(City city) {
        RestaurantsFragment restaurantsFragment = new RestaurantsFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(CITY, city);
        restaurantsFragment.setArguments(bundle);

        return restaurantsFragment;
    }

    public RestaurantsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mCity = getArguments().getParcelable(CITY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant, container, false);
        // Inflate the layout for this fragment

        sendGetRestaurantsRequest();

        mRecycleView = view.findViewById(R.id.restaurants_recycler_view);
        mViewFlipper = view.findViewById(R.id.restaurants_view_flipper);

        showLoading(true);

        return view;
    }


    private void showLoading(boolean show) {
        mViewFlipper.setDisplayedChild(show ? VIEW_LOADING : VIEW_CONTACT);
    }

    private void sendGetRestaurantsRequest() {
        Object dataTransfer[] = new Object[1];
        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
        getNearbyPlacesData.setListener(this);
        String restaurant = "restaurant";
        String url = getUrl(mCity.getLat(), mCity.getLon(), restaurant);
        dataTransfer[0] = url;

        getNearbyPlacesData.execute(dataTransfer);
    }

    private String getUrl(double latitude, double longitude, String nearByPlace) {
        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location=" + latitude + "," + longitude);
        googlePlaceUrl.append("&radius=" + PROXIMITY_RADIUS);
        googlePlaceUrl.append("&type=" + nearByPlace);
        googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&key=" + "AIzaSyDPPST0O4oXMgdxzeXhojDoaGAPdGodr38");

        return googlePlaceUrl.toString();
    }

    @Override
    public void onDataReceived(List<HashMap<String, String>> nearByPlaceList) {
        LinearLayoutManager viewManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        RestaurantsAdapter viewAdapter = new RestaurantsAdapter(nearByPlaceList, this);
        mRecycleView.setLayoutManager(viewManager);
        mRecycleView.setAdapter(viewAdapter);

        showLoading(false);
    }

    @Override
    public void GoToTransportationFragment(HashMap<String, String> restaurantInfoHashMap) {
        mListener.GoToTransportationFragment(restaurantInfoHashMap);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (RestaurantsFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement RestaurantsFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mListener = null;
    }
}
