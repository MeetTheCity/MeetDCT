package com.example.user.meetthect.ui.flow.main.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.CardView;
import android.view.ViewGroup;

import com.example.user.meetthect.data.model.City;

import java.util.ArrayList;
import java.util.List;

public class CardFragmentPagerAdapter extends FragmentStatePagerAdapter implements CardAdapter {

    private final float mBaseElevation;
    private final ArrayList<City> mCityArrayList;
    private List<CardFragment> fragments;

    public CardFragmentPagerAdapter(FragmentManager fragmentManager, float baseElevation, ArrayList<City> cityArrayList) {
        super(fragmentManager);
        fragments = new ArrayList<>();
        mBaseElevation = baseElevation;
        mCityArrayList = cityArrayList;

        for (int i = 0; i < cityArrayList.size(); i++) {
            addCardFragment(new CardFragment());
        }
    }

    @Override
    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return fragments.get(position).getCardView();
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return CardFragment.newInstance(position, mCityArrayList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object fragment = super.instantiateItem(container, position);
        fragments.set(position, (CardFragment) fragment);
        return fragment;
    }

    public void addCardFragment(CardFragment fragment) {
        fragments.add(fragment);
    }

}
