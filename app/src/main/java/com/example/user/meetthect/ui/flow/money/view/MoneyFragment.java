package com.example.user.meetthect.ui.flow.money.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.meetthect.Injector;
import com.example.user.meetthect.R;
import com.example.user.meetthect.base.BaseFragment;
import com.example.user.meetthect.data.model.City;
import com.example.user.meetthect.data.model.ConvertedCurrency;
import com.example.user.meetthect.data.model.CurrencyCode;
import com.example.user.meetthect.data.network.CountriesCodeService;
import com.example.user.meetthect.data.network.CurrencyService;
import com.example.user.meetthect.ui.flow.money.contract.MoneyContract;
import com.example.user.meetthect.ui.flow.money.presenter.MoneyPresenter;

/**
 * Created by User on 3/13/2018.
 */

public class MoneyFragment extends BaseFragment implements MoneyContract.View {
    public static MoneyFragment newInstance(City mCity) {
        MoneyFragment moneyFragment = new MoneyFragment();

        return moneyFragment;
    }

    private MoneyPresenter mMoneyPresenter;

    private CountriesCodeService countriesCodeService;
    private CurrencyService currencyService;
    private TextView baseCoin;
    private TextView convertedCoin;

    public MoneyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_currency, container, false);

        baseCoin = mainView.findViewById(R.id.base_coin);
        convertedCoin = mainView.findViewById(R.id.converted_coin);

        mMoneyPresenter = new MoneyPresenter(Injector.getInstance().getCountriesCodeService(), Injector.getInstance().getCurrencyService());
        mMoneyPresenter.setView(this);
        mMoneyPresenter.getCountryCodeFromCity();
        mMoneyPresenter.getCountryCurrency();

        return mainView;
    }

    @Override
    public void showDataToScreen(CurrencyCode currencyCode, ConvertedCurrency convertedCurrency) {
        String currencyCodeString = currencyCode.getCurrencies().get(0).getCode();
        baseCoin.setText("1" + currencyCodeString + " = ");
        convertedCoin.setText(String.valueOf(convertedCurrency.getRates().getAUD()) + "AUD");
    }

    @Override
    public void showError(String errorMessage) {

    }

    @Override
    public Context getContext() {
        return getActivity();
    }
}
