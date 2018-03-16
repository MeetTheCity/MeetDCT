package com.example.user.meetthect.ui.flow.main.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.meetthect.R;
import com.example.user.meetthect.data.model.City;


public class CardFragment extends Fragment {

    public interface CardFragmentListener {
        void onCityClicked(City city);
    }

    CardFragmentListener listener;

    public static final String CITY = "city";
    public static final String POSITION = "position";
    private CardView cardView;
    private City city;

    public static Fragment newInstance(int position, City city) {
        CardFragment fragment = new CardFragment();
        Bundle args = new Bundle();
        args.putParcelable(CITY, city);
        args.putInt(POSITION, position);
        fragment.setArguments(args);

        return fragment;
    }


    @SuppressLint("DefaultLocale")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final Bundle arguments = getArguments();
        if (arguments != null) {
            city = arguments.getParcelable(CITY);
        }

        View view = inflater.inflate(R.layout.viewpager_item, container, false);

        cardView = view.findViewById(R.id.cardView);
        cardView.setMaxCardElevation(cardView.getCardElevation() * CardAdapter.MAX_ELEVATION_FACTOR);

        AppCompatTextView title = view.findViewById(R.id.title);
        ImageView favoriteButton = view.findViewById(R.id.favorite_button);
        ImageView likeButton = view.findViewById(R.id.like_button);
        ImageView commentsButton = view.findViewById(R.id.comments_button);

        title.setText(city.getCityName());
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCityClicked(city);
            }
        });

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        commentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }

    public CardView getCardView() {
        return cardView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (CardFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement CardFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        listener = null;
    }
}
