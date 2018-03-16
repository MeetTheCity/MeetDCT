package com.example.user.meetthect.ui.flow.city_details.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.meetthect.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by User on 3/16/2018.
 */

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.ViewHolder> {

    public interface RestaurantsAdapterListener {
        void GoToTransportationFragment(HashMap<String, String> restaurantInfoHashMap);
    }

    private List<HashMap<String, String>> mDataset;
    private RestaurantsAdapterListener mListener;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mRestaurantGoThere;
        public TextView mRestaurantName;
        public TextView mRestaurantDistance;
        public TextView mRestaurantAddress;
        public TextView mRestaurantOpeningHours;

        public ViewHolder(View itemView) {
            super(itemView);
            mRestaurantName  = itemView.findViewById(R.id.restaurant_name);
            mRestaurantDistance = itemView.findViewById(R.id.restaurant_distance);
            mRestaurantAddress = itemView.findViewById(R.id.restaurant_address);
            mRestaurantOpeningHours = itemView.findViewById(R.id.restaurant_opening_hours);
            mRestaurantGoThere = itemView.findViewById(R.id.restaurant_go_there);
            mRestaurantGoThere.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.GoToTransportationFragment(mDataset.get(getAdapterPosition()));

                }
            });
        }
    }

    public RestaurantsAdapter(List<HashMap<String, String>> nearByPlaceList ,RestaurantsAdapterListener listener) {
        mListener = listener;
        mDataset = nearByPlaceList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_adapter_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mRestaurantName.setText(mDataset.get(position).get("place_name"));
        holder.mRestaurantDistance.setText("1.5KM");
        holder.mRestaurantAddress.setText(mDataset.get(position).get("vicinity"));
        holder.mRestaurantOpeningHours.setText("Closes 1:00AM");

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}